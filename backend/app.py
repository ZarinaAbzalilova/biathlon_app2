from flask import Flask, jsonify, request
from flask_cors import CORS
import mysql.connector
import json
import os
import pymysql
from database import get_db_connection  # ← Это импорт из отдельного файла!
# УДАЛИТЕ: from config import Config  # Больше не нужно здесь

pymysql.install_as_MySQLdb()
app = Flask(__name__)
CORS(app)

# УДАЛИТЕ все строки с load_dotenv() и app.config

# ========== ОСНОВНЫЕ ENDPOINTS ==========
@app.route('/test_db')
def test_db():
    try:
        conn = get_db_connection()  # ← Используем новую функцию
        cursor = conn.cursor()
        cursor.execute('SELECT 1')
        result = cursor.fetchone()
        conn.close()
        return '''
        <h1>✅ Database connection successful!</h1>
        <p>Подключение к Reg.ru Cloud MySQL работает.</p>
        <p><a href="/">Вернуться на главную</a></p>
        '''
    except Exception as e:
        return f'''
        <h1>❌ Database connection failed!</h1>
        <p>Error: {str(e)}</p>
        <p><a href="/">Вернуться на главную</a></p>
        '''
@app.route('/')
def home():
    return """
    <h1>Biathlon API 🎯</h1>
    <p>Доступные endpoints:</p>
    <ul>
        <li><a href="/api/athletes">/api/athletes</a> - все спортсмены</li>
        <li><a href="/api/athletes/110208199100/results">/api/athletes/{id}/results</a> - результаты спортсмена</li>
        <li><a href="/api/races">/api/races</a> - все гонки</li>
        <li><a href="/api/debug/results">/api/debug/results</a> - диагностика results</li>
        <li><a href="/api/debug/connections">/api/debug/connections</a> - диагностика связей</li>
        <li><a href="/api/debug/race-mismatch">/api/debug/race-mismatch</a> - диагностика race_id</li>
    </ul>
    """

@app.route('/api/athletes', methods=['GET'])
def get_athletes():
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT * FROM athlete ORDER BY last_name, first_name")
        athletes = cursor.fetchall()
        conn.close()
        
        # Преобразуем даты в читаемый формат
        for athlete in athletes:
            if athlete['birth_date']:
                athlete['birth_date'] = athlete['birth_date'].strftime('%d.%m.%Y')
        
        return jsonify(athletes)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/athletes/<athlete_id>/results', methods=['GET'])
def get_athlete_results(athlete_id):
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Сначала проверим существует ли спортсмен
        cursor.execute("SELECT * FROM athlete WHERE athlete_id = %s", (athlete_id,))
        athlete = cursor.fetchone()
        
        if not athlete:
            return jsonify({"error": "Спортсмен не найден"}), 404
        
        # Преобразуем birth_date в строку, если оно есть
        if athlete['birth_date']:
            athlete['birth_date'] = athlete['birth_date'].strftime('%Y-%m-%d')
        
        # ОБНОВЛЕННЫЙ ЗАПРОС с name_race и place_race
        query = """
        SELECT 
            r.race_id, 
            rc.discipline, 
            rc.date,
            rc.name_race,
            rc.place_race,
            r.start_number, 
            r.finish_place, 
            r.miss_count,
            rpu.pdf_url,
            CASE WHEN rc.race_id IS NULL THEN 'race_not_found' ELSE 'ok' END as race_status
        FROM results r
        LEFT JOIN races rc ON r.race_id = rc.race_id
        LEFT JOIN athlete a ON r.athlete_id = a.athlete_id
        LEFT JOIN race_pdf_urls rpu ON r.race_id = rpu.race_id AND a.gender = rpu.gender
        WHERE r.athlete_id = %s
        ORDER BY COALESCE(rc.date, '1900-01-01') DESC
        """
        
        cursor.execute(query, (athlete_id,))
        results = cursor.fetchall()
        
        # Преобразуем даты в читаемый формат
        for result in results:
            if result['date']:
                result['date'] = result['date'].strftime('%Y-%m-%d')
        
        conn.close()
        
        # Формируем ответ с группировкой по гонкам
        formatted_results = []
        for r in results:
            result_item = {
                "race_id": r['race_id'],
                "race_info": {
                    "discipline": r['discipline'],
                    "date": r['date'],
                    "name_race": r['name_race'],
                    "place_race": r['place_race'],
                    "status": r['race_status']
                },
                "athlete_performance": {
                    "start_number": r['start_number'],
                    "finish_place": r['finish_place'],
                    "miss_count": r['miss_count']
                },
                "pdf_url": r['pdf_url']
            }
            formatted_results.append(result_item)
        
        return jsonify({
            "athlete": {
                "athlete_id": athlete['athlete_id'],
                "last_name": athlete['last_name'],
                "first_name": athlete['first_name'],
                "gender": athlete['gender'],
                "region": athlete['region'],
                "region_code": athlete['region_code'],
                "sports_rank": athlete['sports_rank'],
                "birth_date": athlete['birth_date']
            },
            "results": formatted_results,
            "results_count": len(results),
            "message": f"Найдено {len(results)} результатов для спортсмена"
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/api/calendar/races', methods=['GET'])
def get_races_by_month():
    """Получить гонки за определенный месяц"""
    try:
        year = request.args.get('year', type=int)
        month = request.args.get('month', type=int)
        
        if not year or not month:
            return jsonify({"error": "Укажите year и month"}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        start_date = f"{year:04d}-{month:02d}-01"
        if month == 12:
            end_date = f"{year+1:04d}-01-01"
        else:
            end_date = f"{year:04d}-{month+1:02d}-01"
        
        # Получаем все гонки за месяц
        query = """
        SELECT 
            r.race_id as id,
            r.name_race as title,
            r.place_race as location,
            r.discipline,
            p.gender,
            p.date,
            p.pdf_url
        FROM races r
        JOIN race_pdf_urls p ON r.race_id = p.race_id
        WHERE p.date >= %s AND p.date < %s
        ORDER BY p.date, 
                 CASE 
                     WHEN r.discipline IN ('BT_MixedRelay', 'BT_SingleMixedRelay') THEN 0 
                     ELSE 1 
                 END,
                 p.gender
        """
        
        cursor.execute(query, (start_date, end_date))
        races = cursor.fetchall()
        
        # Группируем по датам
        races_by_date = {}
        for race in races:
            date_str = race['date'].strftime('%Y-%m-%d')
            if date_str not in races_by_date:
                races_by_date[date_str] = {
                    'date': date_str,
                    'has_male': 0,
                    'has_female': 0,
                    'has_mixed': 0,  # ← НОВОЕ
                    'races': []
                }
            
            # Определяем тип гонки
            if race['discipline'] in ['BT_MixedRelay', 'BT_SingleMixedRelay']:
                races_by_date[date_str]['has_mixed'] = 1
                # Для смешанных гонок добавляем только одну запись (если еще не добавили)
                existing_mixed = any(
                    r['id'] == race['id'] and r['is_mixed'] == True 
                    for r in races_by_date[date_str]['races']
                )
                if not existing_mixed:
                    race['is_mixed'] = True
                    race['gender'] = 'Смешанная'
                    races_by_date[date_str]['races'].append(race)
            else:
                # Обычные гонки по полу
                if race['gender'] == 'М':
                    races_by_date[date_str]['has_male'] = 1
                elif race['gender'] == 'Ж':
                    races_by_date[date_str]['has_female'] = 1
                race['is_mixed'] = False
                races_by_date[date_str]['races'].append(race)
        
        conn.close()
        return jsonify(list(races_by_date.values()))
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/race/<race_id>/<gender>', methods=['GET'])
def get_race_details(race_id, gender):
    """Получить детали конкретной гонки (для карточки)"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        query = """
        SELECT 
            r.race_id,
            r.name_race as title,
            r.place_race as location,
            r.discipline,
            p.gender,
            p.date,
            p.pdf_url
        FROM races r
        JOIN race_pdf_urls p ON r.race_id = p.race_id
        WHERE r.race_id = %s AND p.gender = %s
        """
        
        cursor.execute(query, (race_id, gender))
        race = cursor.fetchone()
        conn.close()
        
        if race:
            if race['date']:
                race['date'] = race['date'].strftime('%Y-%m-%d')
            return jsonify(race)
        else:
            return jsonify({"error": "Гонка не найдена"}), 404
            
    except Exception as e:
        return jsonify({"error": str(e)}), 500
@app.route('/api/race/<race_id>/results', methods=['GET'])
def get_race_results(race_id):
    """Получить результаты гонки для финишного протокола"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Получаем информацию о гонке
        cursor.execute("""
            SELECT 
                r.race_id,
                r.name_race,
                r.discipline,
                r.date,
                r.place_race,
                rpu.pdf_url,
                rpu.gender
            FROM races r
            LEFT JOIN race_pdf_urls rpu ON r.race_id = rpu.race_id
            WHERE r.race_id = %s
        """, (race_id,))
        
        race_info = cursor.fetchone()
        
        if not race_info:
            return jsonify({"error": "Гонка не найдена"}), 404
        
        # Получаем результаты спортсменов
        cursor.execute("""
            SELECT 
                r.start_number,
                r.finish_place,
                r.miss_count,
                r.finish_time,
                a.athlete_id,
                a.last_name,
                a.first_name,
                a.region,
                a.sports_rank
            FROM results r
            JOIN athlete a ON r.athlete_id = a.athlete_id
            WHERE r.race_id = %s
            ORDER BY r.finish_place ASC
        """, (race_id,))
        
        results = cursor.fetchall()
        
        # Преобразуем дату в строку
        if race_info['date']:
            race_info['date'] = race_info['date'].strftime('%Y-%m-%d')
        
        conn.close()
        
        return jsonify({
            "race_info": {
                "race_id": race_info['race_id'],
                "name_race": race_info['name_race'],
                "discipline": race_info['discipline'],
                "date": race_info['date'],
                "place_race": race_info['place_race'],
                "gender": race_info['gender'],
                "pdf_url": race_info['pdf_url']
            },
            "results": results,
            "results_count": len(results)
        })
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500
@app.route('/api/athletes/by-team', methods=['GET'])
def get_athletes_by_team():
    """Получить спортсменов по команде"""
    try:
        team = request.args.get('team')
        
        if not team:
            return jsonify({"error": "Укажите team"}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        query = "SELECT * FROM athlete WHERE team = %s ORDER BY last_name, first_name"
        cursor.execute(query, (team,))
        athletes = cursor.fetchall()
        
        # Преобразуем даты
        for athlete in athletes:
            if athlete['birth_date']:
                athlete['birth_date'] = athlete['birth_date'].strftime('%Y-%m-%d')
        
        conn.close()
        
        return jsonify(athletes)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/calendar/races/by-date', methods=['GET'])
def get_races_by_date():
    """Получить гонки за конкретную дату"""
    try:
        date_str = request.args.get('date')
        
        if not date_str:
            return jsonify({"error": "Укажите date"}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        query = """
        SELECT 
            race_id as id,
            name_race as title,
            date,
            place_race as location,
            discipline
        FROM races
        WHERE date = %s
        ORDER BY date
        """
        
        cursor.execute(query, (date_str,))
        races = cursor.fetchall()
        
        # Преобразуем даты в строки
        for race in races:
            if race['date']:
                race['date'] = race['date'].strftime('%Y-%m-%d')
        
        conn.close()
        
        return jsonify(races)
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/api/races', methods=['GET'])
def get_races():
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT * FROM races ORDER BY date DESC")
        races = cursor.fetchall()
        conn.close()
        return jsonify(races)
    except Exception as e:
        return jsonify({"error": str(e)}), 500



@app.route('/api/debug/check-pdf-coverage', methods=['GET'])
def check_pdf_coverage():
    """Проверить покрытие PDF URLs для гонок спортсмена"""
    athlete_id = request.args.get('athlete_id', '110208199100')
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Получаем все гонки спортсмена
        cursor.execute("""
            SELECT DISTINCT r.race_id, rc.discipline, rc.date
            FROM results r
            LEFT JOIN races rc ON r.race_id = rc.race_id
            WHERE r.athlete_id = %s
            ORDER BY rc.date DESC
            LIMIT 10
        """, (athlete_id,))
        athlete_races = cursor.fetchall()
        
        # Проверяем какие из них есть в race_pdf_urls
        coverage_data = []
        for race in athlete_races:
            cursor.execute("""
                SELECT COUNT(*) as pdf_count 
                FROM race_pdf_urls 
                WHERE race_id = %s
            """, (race['race_id'],))
            pdf_count = cursor.fetchone()['pdf_count']
            
            coverage_data.append({
                'race_id': race['race_id'],
                'discipline': race['discipline'],
                'date': race['date'],
                'has_pdf_urls': pdf_count > 0,
                'pdf_urls_count': pdf_count
            })
        
        conn.close()
        
        return jsonify({
            "athlete_id": athlete_id,
            "pdf_coverage": coverage_data,
            "message": "Покрытие PDF URLs для гонок спортсмена"
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
# ========== ДИАГНОСТИЧЕСКИЕ ENDPOINTS ==========

@app.route('/api/debug/detailed-join-analysis', methods=['GET'])
def detailed_join_analysis():
    """Детальный анализ JOIN запроса"""
    athlete_id = request.args.get('athlete_id', '110208199100')
    race_id = request.args.get('race_id', '20250327_A_RCHM_Pursuit')
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # 1. Проверим данные спортсмена
        cursor.execute("SELECT athlete_id, last_name, first_name, gender FROM athlete WHERE athlete_id = %s", (athlete_id,))
        athlete = cursor.fetchone()
        
        # 2. Проверим данные PDF URLs для этой гонки
        cursor.execute("SELECT race_id, gender, pdf_url FROM race_pdf_urls WHERE race_id = %s", (race_id,))
        pdf_urls = cursor.fetchall()
        
        # 3. Проверим JOIN по частям
        join_analysis_query = """
        SELECT 
            r.race_id,
            a.athlete_id,
            a.gender as athlete_gender,
            rpu.gender as pdf_gender, 
            rpu.pdf_url,
            CASE 
                WHEN a.gender IS NULL THEN 'NO_ATHLETE_GENDER'
                WHEN rpu.gender IS NULL THEN 'NO_PDF_GENDER_MATCH'
                WHEN rpu.pdf_url IS NULL THEN 'PDF_URL_IS_NULL'
                WHEN a.gender = rpu.gender THEN 'GENDER_MATCH'
                ELSE 'GENDER_MISMATCH'
            END as join_status
        FROM results r
        LEFT JOIN athlete a ON r.athlete_id = a.athlete_id
        LEFT JOIN race_pdf_urls rpu ON r.race_id = rpu.race_id AND a.gender = rpu.gender
        WHERE r.athlete_id = %s AND r.race_id = %s
        """
        
        cursor.execute(join_analysis_query, (athlete_id, race_id))
        join_result = cursor.fetchone()
        
        # 4. Проверим простой JOIN без условий
        simple_join_query = """
        SELECT 
            r.race_id,
            a.gender as athlete_gender,
            rpu.*
        FROM results r
        JOIN athlete a ON r.athlete_id = a.athlete_id  
        JOIN race_pdf_urls rpu ON r.race_id = rpu.race_id
        WHERE r.athlete_id = %s AND r.race_id = %s
        """
        
        cursor.execute(simple_join_query, (athlete_id, race_id))
        simple_join_results = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            "athlete": athlete,
            "pdf_urls_for_race": pdf_urls,
            "join_analysis": join_result,
            "simple_join_results": simple_join_results,
            "message": f"Детальный анализ для гонки {race_id}"
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/races/<race_id>/pdf-url', methods=['GET'])
def get_race_pdf_url(race_id):
    """Получить PDF URL для гонки"""
    athlete_id = request.args.get('athlete_id')
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        if athlete_id:
            # Получаем gender спортсмена и ищем соответствующий PDF
            cursor.execute("""
                SELECT rpu.pdf_url
                FROM race_pdf_urls rpu
                JOIN athlete a ON a.athlete_id = %s AND rpu.gender = a.gender
                WHERE rpu.race_id = %s
            """, (athlete_id, race_id))
        else:
            # Без athlete_id - берем первый попавшийся PDF
            cursor.execute("""
                SELECT pdf_url FROM race_pdf_urls 
                WHERE race_id = %s 
                LIMIT 1
            """, (race_id,))
        
        pdf_data = cursor.fetchone()
        conn.close()
        
        if pdf_data and pdf_data['pdf_url']:
            return jsonify({
                "race_id": race_id,
                "pdf_url": pdf_data['pdf_url'],
                "found": True
            })
        else:
            return jsonify({
                "race_id": race_id, 
                "pdf_url": None,
                "found": False,
                "message": "PDF не найден для этой гонки"
            }), 404
            
    except Exception as e:
        return jsonify({"error": str(e)}), 500
@app.route('/api/debug/check-pdf-match', methods=['GET'])
def check_pdf_match():
    """Проверить соответствие PDF URL для конкретного спортсмена и гонки"""
    athlete_id = request.args.get('athlete_id', '110208199100')
    race_id = request.args.get('race_id', '20250329_R_RCHM_Relay')
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Получаем данные спортсмена
        cursor.execute("SELECT * FROM athlete WHERE athlete_id = %s", (athlete_id,))
        athlete = cursor.fetchone()
        
        # Получаем PDF URL для этой гонки и gender спортсмена
        cursor.execute("""
            SELECT 
                rpu.race_id,
                rpu.gender as pdf_gender,
                rpu.pdf_url,
                a.gender as athlete_gender,
                a.last_name,
                a.first_name
            FROM race_pdf_urls rpu
            CROSS JOIN athlete a 
            WHERE rpu.race_id = %s 
            AND rpu.gender = a.gender
            AND a.athlete_id = %s
        """, (race_id, athlete_id))
        
        match_result = cursor.fetchone()
        
        # Также проверим все PDF URLs для этой гонки
        cursor.execute("SELECT * FROM race_pdf_urls WHERE race_id = %s", (race_id,))
        all_pdf_urls = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            "athlete": athlete,
            "pdf_match_result": match_result,
            "all_pdf_urls_for_race": all_pdf_urls,
            "message": f"Проверка для гонки {race_id} и спортсмена {athlete_id}"
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/api/debug/results', methods=['GET'])
def debug_results():
    """Проверить какие данные есть в results"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT * FROM results LIMIT 10")
        results = cursor.fetchall()
        conn.close()
        return jsonify({
            "message": "Первые 10 записей из таблицы results",
            "data": results,
            "count": len(results)
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/debug/connections', methods=['GET'])
def debug_connections():
    """Проверить связи между таблицами"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("SELECT COUNT(*) as count FROM athlete")
        athlete_count = cursor.fetchone()
        
        cursor.execute("SELECT COUNT(*) as count FROM races")
        races_count = cursor.fetchone()
        
        cursor.execute("SELECT COUNT(*) as count FROM results")
        results_count = cursor.fetchone()
        
        cursor.execute("""
            SELECT COUNT(*) as linked_results 
            FROM results r 
            JOIN athlete a ON r.athlete_id = a.athlete_id
        """)
        linked_results = cursor.fetchone()
        
        conn.close()
        
        return jsonify({
            "table_counts": {
                "athlete": athlete_count['count'],
                "races": races_count['count'], 
                "results": results_count['count']
            },
            "linked_results_count": linked_results['linked_results']
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/debug/race-mismatch', methods=['GET'])
def debug_race_mismatch():
    """Диагностика проблемы с race_id"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT 
                (SELECT COUNT(DISTINCT race_id) FROM results) as unique_race_ids_in_results,
                (SELECT COUNT(DISTINCT race_id) FROM races) as unique_race_ids_in_races,
                (SELECT COUNT(*) FROM results r LEFT JOIN races rc ON r.race_id = rc.race_id WHERE rc.race_id IS NULL) as unmatched_results
        """)
        stats = cursor.fetchone()
        
        cursor.execute("""
            SELECT DISTINCT r.race_id
            FROM results r 
            LEFT JOIN races rc ON r.race_id = rc.race_id 
            WHERE rc.race_id IS NULL
            LIMIT 10
        """)
        missing_races = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            "stats": stats,
            "missing_races_in_races_table": missing_races
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
@app.route('/api/athletes/search', methods=['GET'])
def search_athletes():
    """Поиск спортсменов по имени"""
    query = request.args.get('q', '')
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        cursor.execute(
            "SELECT * FROM athlete WHERE last_name LIKE %s OR first_name LIKE %s ORDER BY last_name",
            (f'%{query}%', f'%{query}%')
        )
        athletes = cursor.fetchall()
        conn.close()
        return jsonify(athletes)
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/api/athletes/paginated', methods=['GET'])
def get_athletes_paginated():
    """Список спортсменов с пагинацией"""
    page = request.args.get('page', 1, type=int)
    per_page = request.args.get('per_page', 50, type=int)
    offset = (page - 1) * per_page
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Общее количество
        cursor.execute("SELECT COUNT(*) as total FROM athlete")
        total = cursor.fetchone()['total']
        
        # Данные страницы
        cursor.execute("SELECT * FROM athlete ORDER BY last_name, first_name LIMIT %s OFFSET %s", 
                      (per_page, offset))
        athletes = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            'page': page,
            'per_page': per_page,
            'total': total,
            'total_pages': (total + per_page - 1) // per_page,
            'data': athletes
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/api/stats', methods=['GET'])
def get_stats():
    """Общая статистика"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("SELECT COUNT(*) as total_athletes FROM athlete")
        athletes = cursor.fetchone()
        
        cursor.execute("SELECT COUNT(*) as total_races FROM races")
        races = cursor.fetchone()
        
        cursor.execute("SELECT COUNT(*) as total_results FROM results")
        results = cursor.fetchone()
        
        cursor.execute("SELECT sports_rank, COUNT(*) as count FROM athlete WHERE sports_rank != '' GROUP BY sports_rank")
        ranks = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            'total_athletes': athletes['total_athletes'],
            'total_races': races['total_races'],
            'total_results': results['total_results'],
            'sports_ranks': ranks
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500
if __name__ == '__main__':
    # Для продакшена используем порт из переменной окружения
    port = int(os.environ.get('PORT', 5000))
    
    print("🚀 Biathlon API запущен!")
    #print("📊 База данных:", Config.MYSQL_DATABASE)
    print("🌐 API доступен на: http://0.0.0.0:" + str(port))
    print("⏹️  Остановка: Ctrl+C")
    
    app.run(host='0.0.0.0', port=port, debug=False)  # debug=False для продакшена!