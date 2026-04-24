from flask import Flask, jsonify, request
from flask_cors import CORS
import mysql.connector
import json
import os
import pymysql
from database import get_db_connection  
import secrets
import bcrypt
import jwt
from datetime import datetime, timedelta
from functools import wraps
import re
import smtplib
import requests
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import google.auth
from google.auth.transport.requests import Request
from google.oauth2 import service_account
from datetime import datetime, timedelta
import pytz
from apscheduler.schedulers.background import BackgroundScheduler
import atexit
import ssl



SCOPES = ['https://www.googleapis.com/auth/firebase.messaging']
PROJECT_ID = os.environ.get('FCM_PROJECT_ID', 'biathlonapp-84d7a')

# Email настройки для Yandex (замените старые настройки)
EMAIL_HOST = os.environ.get('EMAIL_HOST', 'smtp.yandex.ru')
EMAIL_PORT = int(os.environ.get('EMAIL_PORT', 587))
EMAIL_USER = os.environ.get('EMAIL_USER', '')
EMAIL_PASSWORD = os.environ.get('EMAIL_PASSWORD', '')
APP_URL = os.environ.get('APP_URL', 'https://biathlon-app2.onrender.com')



#проверка



def get_firebase_access_token():
    """Получение OAuth 2.0 токена для Firebase"""
    try:
        # Пытаемся получить credentials из переменной окружения (Render)
        creds_json = os.environ.get('GOOGLE_APPLICATION_CREDENTIALS_JSON')
        if creds_json:
            creds_dict = json.loads(creds_json)
            credentials = service_account.Credentials.from_service_account_info(
                creds_dict, scopes=SCOPES
            )
        else:
            # Если нет, пробуем через файл
            credentials = service_account.Credentials.from_service_account_file(
                'service-account.json', scopes=SCOPES
            )
        
        credentials.refresh(Request())
        return credentials.token
    except Exception as e:
        print(f"❌ Ошибка получения токена: {e}")
        return None
    
def send_fcm_notification_v1(fcm_token, title, body, race_id=None):
    """Отправка уведомления через FCM HTTP v1 API"""
    try:
        access_token = get_firebase_access_token()
        if not access_token:
            return False
        
        # URL для v1 API
        url = f"https://fcm.googleapis.com/v1/projects/{PROJECT_ID}/messages:send"
        
        headers = {
            'Authorization': f'Bearer {access_token}',
            'Content-Type': 'application/json'
        }
        
        # Формируем payload для v1 API [citation:3]
        payload = {
            "message": {
                "token": fcm_token,
                "notification": {
                    "title": title,
                    "body": body
                },
                "data": {
                    "race_id": race_id or "",
                    "click_action": "OPEN_RACE_ACTIVITY"
                },
                "android": {
                    "priority": "high",
                    "notification": {
                        "click_action": "OPEN_RACE_ACTIVITY"
                    }
                }
            }
        }
        
        response = requests.post(url, headers=headers, json=payload)
        
        if response.status_code == 200:
            print(f"✅ Уведомление отправлено на {fcm_token[:10]}...")
            return True
        else:
            print(f"❌ Ошибка: {response.status_code} - {response.text}")
            return False
            
    except Exception as e:
        print(f"❌ Исключение при отправке: {e}")
        return False

def send_reset_email(to_email, reset_token):
    """Отправка email для сброса пароля через Yandex"""
    try:
        # Используем HTTPS ссылку вместо biathlonapp://
        reset_link = f"https://biathlon-app2.onrender.com/reset-password?token={reset_token}"
        
        subject = "Сброс пароля - Биатлон Приложение"
        html_content = f"""
        <html>
        <head>
            <style>
                body {{ font-family: Arial, sans-serif; }}
                .container {{ max-width: 600px; margin: 0 auto; padding: 20px; }}
                .button {{
                    background-color: #4CAF50;
                    border: none;
                    color: white;
                    padding: 12px 24px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    font-size: 16px;
                    margin: 20px 0;
                    border-radius: 4px;
                }}
                .footer {{ margin-top: 30px; font-size: 12px; color: #666; }}
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Сброс пароля</h2>
                <p>Вы запросили сброс пароля для аккаунта <strong>{to_email}</strong>.</p>
                <p>Нажмите на кнопку ниже, чтобы установить новый пароль:</p>
                <a href="{reset_link}" class="button" style="background-color:#4CAF50;border:none;color:white;padding:12px 24px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;margin:20px 0;border-radius:4px;">Сбросить пароль</a>
                <p>Или скопируйте ссылку в браузер:</p>
                <p><a href="{reset_link}">{reset_link}</a></p>
                <p>Ссылка действительна в течение 1 часа.</p>
                <p>Если вы не запрашивали сброс пароля, просто проигнорируйте это письмо.</p>
                <div class="footer">
                    <p>© 2026 Биатлон Приложение</p>
                </div>
            </div>
        </body>
        </html>
        """
        
        msg = MIMEMultipart()
        msg['From'] = EMAIL_USER
        msg['To'] = to_email
        msg['Subject'] = subject
        msg.attach(MIMEText(html_content, 'html'))
        
        # Отправка через Yandex с SSL
        context = ssl.create_default_context()
        with smtplib.SMTP_SSL("smtp.yandex.ru", 465, context=context) as server:
            server.login(EMAIL_USER, EMAIL_PASSWORD)
            server.send_message(msg)
        
        print(f"✅ Reset email sent to {to_email}")
        return True
        
    except Exception as e:
        print(f"❌ Failed to send email: {e}")
        import traceback
        traceback.print_exc()
        return False
# JWT настройки
JWT_SECRET = os.environ.get('JWT_SECRET', 'your-secret-key-change-in-production')
JWT_EXPIRATION_HOURS = 24 * 30  # 30 дней

# Вспомогательные функции
def hash_password(password):
    """Хеширование пароля"""
    salt = bcrypt.gensalt()
    return bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

def verify_password(password, password_hash):
    """Проверка пароля"""
    return bcrypt.checkpw(password.encode('utf-8'), password_hash.encode('utf-8'))

def generate_token(user_id, email):
    """Генерация JWT токена"""
    payload = {
        'user_id': user_id,
        'email': email,
        'exp': datetime.utcnow() + timedelta(hours=JWT_EXPIRATION_HOURS),
        'iat': datetime.utcnow()
    }
    return jwt.encode(payload, JWT_SECRET, algorithm='HS256')

def decode_token(token):
    """Декодирование JWT токена"""
    try:
        payload = jwt.decode(token, JWT_SECRET, algorithms=['HS256'])
        return payload
    except jwt.ExpiredSignatureError:
        return None
    except jwt.InvalidTokenError:
        return None

def token_required(f):
    """Декоратор для проверки токена"""
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get('Authorization')
        
        if not token:
            return jsonify({'error': 'Токен отсутствует'}), 401
        
        # Убираем 'Bearer ' если есть
        if token.startswith('Bearer '):
            token = token[7:]
        
        payload = decode_token(token)
        if not payload:
            return jsonify({'error': 'Недействительный или просроченный токен'}), 401
        
        request.user_id = payload['user_id']
        request.user_email = payload['email']
        
        return f(*args, **kwargs)
    return decorated

def validate_email(email):
    """Валидация email"""
    pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
    return re.match(pattern, email) is not None

def validate_password(password):
    """Валидация пароля (минимум 6 символов)"""
    return len(password) >= 6
pymysql.install_as_MySQLdb()
app = Flask(__name__)
CORS(app)
def check_and_send_race_notifications():
    """Проверяет гонки и отправляет уведомления за день до и в день гонки"""
    try:
        # Получаем текущую дату (без времени)
        today = datetime.now().date()
        tomorrow = today + timedelta(days=1)
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Получаем все гонки на сегодня и завтра
        cursor.execute("""
            SELECT DISTINCT 
                r.race_id,
                r.name_race,
                r.discipline,
                r.date as race_date,
                r.place_race
            FROM races r
            JOIN race_pdf_urls p ON r.race_id = p.race_id
            WHERE r.date IN (%s, %s)
            ORDER BY r.date
        """, (today, tomorrow))
        
        races = cursor.fetchall()
        
        if not races:
            print("📭 Нет гонок на сегодня или завтра")
            conn.close()
            return
        
        # Получаем всех пользователей с включенными уведомлениями
        cursor.execute("""
            SELECT fcm_token FROM users 
            WHERE fcm_token IS NOT NULL 
            AND (notifications_enabled = 1 OR notifications_enabled IS NULL)
        """)
        
        users = cursor.fetchall()
        
        if not users:
            print("👥 Нет пользователей для отправки уведомлений")
            conn.close()
            return
        
        notifications_sent = 0
        
        for race in races:
            race_id = race['race_id']
            race_date = race['race_date']
            name_race = race['name_race'] or f"{race['discipline']}"
            
            # Проверяем, нужно ли отправить уведомление за день до
            if race_date == tomorrow:
                # Проверяем, не отправляли ли уже
                cursor.execute("""
                    SELECT id FROM race_notifications_sent 
                    WHERE race_id = %s AND notification_type = 'day_before'
                """, (race_id,))
                
                if not cursor.fetchone():
                    title = "🏆 Напоминание о гонке"
                    body = f"Завтра: {name_race}"
                    
                    for user in users:
                        if user['fcm_token']:
                            send_fcm_notification_v1(user['fcm_token'], title, body, race_id)
                    
                    # Сохраняем, что уведомление отправлено
                    cursor.execute("""
                        INSERT INTO race_notifications_sent (race_id, notification_type)
                        VALUES (%s, 'day_before')
                    """, (race_id,))
                    conn.commit()
                    notifications_sent += 1
                    print(f"✅ Отправлено уведомление 'Завтра' для гонки: {name_race}")
            
            # Проверяем, нужно ли отправить уведомление в день гонки
            elif race_date == today:
                # Проверяем текущее время (9 утра по серверу)
                current_hour = datetime.now().hour
                
                if current_hour >= 9:  # После 9 утра
                    cursor.execute("""
                        SELECT id FROM race_notifications_sent 
                        WHERE race_id = %s AND notification_type = 'day_of'
                    """, (race_id,))
                    
                    if not cursor.fetchone():
                        title = "🏃‍♂️ Гонка сегодня!"
                        body = f"Сегодня: {name_race}"
                        
                        for user in users:
                            if user['fcm_token']:
                                send_fcm_notification_v1(user['fcm_token'], title, body, race_id)
                        
                        cursor.execute("""
                            INSERT INTO race_notifications_sent (race_id, notification_type)
                            VALUES (%s, 'day_of')
                        """, (race_id,))
                        conn.commit()
                        notifications_sent += 1
                        print(f"✅ Отправлено уведомление 'Сегодня' для гонки: {name_race}")
        
        conn.close()
        print(f"📬 Всего отправлено уведомлений: {notifications_sent}")
        
    except Exception as e:
        print(f"❌ Ошибка при проверке гонок: {e}")

# Добавляем эндпоинт для ручного вызова (для тестирования)
@app.route('/api/notifications/check-races', methods=['GET'])
@token_required
def manual_check_races():
    """Ручной запуск проверки гонок (для тестирования)"""
    check_and_send_race_notifications()
    return jsonify({'success': True, 'message': 'Проверка гонок выполнена'})

# Для сброса пароля
@app.route('/reset-password', methods=['GET'])
def reset_password_page():
    """Страница-заглушка, которая перенаправляет на приложение"""
    token = request.args.get('token', '')
    if token:
        # Перенаправляем на deep link приложения
        return f'''
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Сброс пароля - Биатлон</title>
            <meta http-equiv="refresh" content="2; URL=biathlonapp://reset-password?token={token}">
            <style>
                body {{
                    font-family: Arial, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    margin: 0;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                }}
                .container {{
                    text-align: center;
                    background: white;
                    padding: 40px;
                    border-radius: 20px;
                    box-shadow: 0 10px 40px rgba(0,0,0,0.2);
                }}
                .logo {{
                    font-size: 48px;
                    margin-bottom: 20px;
                }}
                h1 {{
                    color: #333;
                    margin-bottom: 10px;
                }}
                p {{
                    color: #666;
                    margin-bottom: 20px;
                }}
                .button {{
                    background-color: #4CAF50;
                    border: none;
                    color: white;
                    padding: 12px 24px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    font-size: 16px;
                    margin: 10px 0;
                    border-radius: 4px;
                    cursor: pointer;
                }}
                .loading {{
                    display: inline-block;
                    width: 20px;
                    height: 20px;
                    border: 3px solid #f3f3f3;
                    border-top: 3px solid #4CAF50;
                    border-radius: 50%;
                    animation: spin 1s linear infinite;
                }}
                @keyframes spin {{
                    0% {{ transform: rotate(0deg); }}
                    100% {{ transform: rotate(360deg); }}
                }}
            </style>
        </head>
        <body>
            <div class="container">
                <div class="logo">🏆</div>
                <h1>Биатлон Приложение</h1>
                <p>Перенаправление в приложение для сброса пароля...</p>
                <div class="loading"></div>
                <p style="margin-top: 20px; font-size: 14px;">
                    Если перенаправление не произошло, 
                    <a href="biathlonapp://reset-password?token={token}" style="color:#4CAF50;">нажмите здесь</a>
                </p>
                <p style="margin-top: 10px; font-size: 12px; color: #999;">
                    Или установите приложение Биатлон из Google Play
                </p>
            </div>
            <script>
                // Автоматический редирект на приложение
                setTimeout(function() {{
                    window.location.href = "biathlonapp://reset-password?token={token}";
                }}, 2000);
                
                // Проверяем, открылось ли приложение
                var timeout = setTimeout(function() {{
                    document.querySelector('.loading').style.display = 'none';
                    document.querySelector('p').innerHTML = 'Приложение не установлено. <a href="#" onclick="redirectToPlay()">Скачать из Google Play</a>';
                }}, 3000);
                
                window.addEventListener("blur", function() {{
                    clearTimeout(timeout);
                }});
                
                function redirectToPlay() {{
                    window.location.href = "https://play.google.com/store/apps/details?id=com.biathlonapp";
                }}
            </script>
        </body>
        </html>
        '''
    else:
        return '''
        <!DOCTYPE html>
        <html>
        <head>
            <title>Сброс пароля - Биатлон</title>
            <style>
                body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }
                .error { color: red; }
            </style>
        </head>
        <body>
            <h1>🏆 Биатлон Приложение</h1>
            <p class="error">Недействительная ссылка для сброса пароля</p>
            <p>Пожалуйста, запросите сброс пароля заново в приложении.</p>
        </body>
        </html>
        '''


@app.route('/api/notifications/add-test-race', methods=['POST'])
@token_required
def add_test_race():
    """Добавление тестовой гонки для проверки уведомлений"""
    try:
        data = request.get_json()
        race_id = data.get('race_id', 'TEST_RACE_001')
        name_race = data.get('name_race', 'Тестовая гонка')
        discipline = data.get('discipline', 'BT_Sprint')
        date_str = data.get('date', (datetime.now() + timedelta(days=1)).strftime('%Y-%m-%d'))
        place_race = data.get('place_race', 'Тестовое место')
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Добавляем гонку
        cursor.execute("""
            INSERT INTO races (race_id, discipline, date, name_race, place_race)
            VALUES (%s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE
            discipline = VALUES(discipline),
            date = VALUES(date),
            name_race = VALUES(name_race),
            place_race = VALUES(place_race)
        """, (race_id, discipline, date_str, name_race, place_race))
        
        # Добавляем PDF URL (заглушку)
        cursor.execute("""
            INSERT INTO race_pdf_urls (race_id, gender, pdf_url, date)
            VALUES (%s, 'М', 'https://example.com/test.pdf', %s)
            ON DUPLICATE KEY UPDATE date = VALUES(date)
        """, (race_id, date_str))
        
        conn.commit()
        conn.close()
        
        return jsonify({
            'success': True, 
            'message': f'Тестовая гонка добавлена на {date_str}',
            'race': {
                'id': race_id,
                'name': name_race,
                'date': date_str
            }
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# ========== ОСНОВНЫЕ ENDPOINTS ==========
     
@app.route('/api/auth/update-fcm-token', methods=['POST'])
@token_required
def update_fcm_token():
    """Обновление FCM токена пользователя"""
    try:
        data = request.get_json()
        fcm_token = data.get('fcm_token', '')
        
        if not fcm_token:
            return jsonify({'error': 'FCM token обязателен'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем существование колонки и добавляем если нет
        cursor.execute("SHOW COLUMNS FROM users LIKE 'fcm_token'")
        column_exists = cursor.fetchone()
        
        if not column_exists:
            cursor.execute("ALTER TABLE users ADD COLUMN fcm_token VARCHAR(500) NULL")
        
        # Обновляем токен
        cursor.execute("""
            UPDATE users 
            SET fcm_token = %s
            WHERE id = %s
        """, (fcm_token, request.user_id))
        
        conn.commit()
        conn.close()
        
        return jsonify({'success': True, 'message': 'FCM token обновлен'})
        
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'error': str(e)}), 500

@app.route('/api/notifications/settings', methods=['POST'])
@token_required
def update_notification_settings():
    """Обновление настроек уведомлений пользователя"""
    try:
        data = request.get_json()
        enabled = data.get('enabled', True)
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем существование колонки и добавляем если нет
        cursor.execute("SHOW COLUMNS FROM users LIKE 'notifications_enabled'")
        column_exists = cursor.fetchone()
        
        if not column_exists:
            cursor.execute("ALTER TABLE users ADD COLUMN notifications_enabled BOOLEAN DEFAULT TRUE")
        
        cursor.execute("""
            UPDATE users 
            SET notifications_enabled = %s
            WHERE id = %s
        """, (enabled, request.user_id))
        
        conn.commit()
        conn.close()
        
        return jsonify({'success': True, 'message': 'Настройки уведомлений обновлены'})
        
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'error': str(e)}), 500
@app.route('/api/notifications/send-race-reminder', methods=['POST'])
@token_required
def send_race_reminder():
    """Отправка напоминания о гонке"""
    try:
        data = request.get_json()
        title = data.get('title', 'Новая гонка')
        body = data.get('body', '')
        race_id = data.get('race_id')
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Получаем токены пользователей, у которых включены уведомления
        cursor.execute("""
            SELECT fcm_token FROM users 
            WHERE fcm_token IS NOT NULL AND notifications_enabled = 1
        """)
        
        tokens = cursor.fetchall()
        conn.close()
        
        success_count = 0
        for token_data in tokens:
            token = token_data['fcm_token']
            if send_fcm_notification_v1(token, title, body, race_id):
                success_count += 1
        
        return jsonify({
            'success': True,
            'sent': success_count,
            'total': len(tokens)
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500
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
@app.route('/api/race/<race_id>/relay-results', methods=['GET'])
def get_relay_results(race_id):
    """Получить результаты эстафеты с группировкой по командам"""
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
                r.place_race
            FROM races r
            WHERE r.race_id = %s
        """, (race_id,))
        
        race_info = cursor.fetchone()
        
        if not race_info:
            return jsonify({"error": "Гонка не найдена"}), 404
        
        if race_info['date']:
            race_info['date'] = race_info['date'].strftime('%Y-%m-%d')
        
        # Группируем результаты по командам
        cursor.execute("""
            SELECT 
                r.team_name,
                MIN(r.finish_place) as finish_place,
                SUM(r.miss_count) as total_miss_count,
                MIN(r.finish_time) as finish_time,
                GROUP_CONCAT(
                    CONCAT(
                        a.last_name, ' ', a.first_name, 
                        '|', r.miss_count, 
                        '|', r.start_number,
                        '|', r.athlete_id
                    ) 
                    ORDER BY r.start_number SEPARATOR ';'
                ) as members_data
            FROM results r
            JOIN athlete a ON r.athlete_id = a.athlete_id
            WHERE r.race_id = %s AND r.team_name IS NOT NULL
            GROUP BY r.team_name
            ORDER BY MIN(r.finish_place) ASC
        """, (race_id,))
        
        teams = cursor.fetchall()
        
        # Форматируем участников
        for team in teams:
            members = []
            if team['members_data']:
                for member_str in team['members_data'].split(';'):
                    parts = member_str.split('|')
                    if len(parts) >= 4:
                        members.append({
                            'full_name': parts[0],
                            'miss_count': int(parts[1]) if parts[1].isdigit() else 0,
                            'leg_number': int(parts[2]) if parts[2].isdigit() else 0,
                            'athlete_id': int(parts[3]) if parts[3].isdigit() else 0
                        })
            team['members'] = members
            del team['members_data']
        
        # PDF URL
        cursor.execute("""
            SELECT pdf_url, gender
            FROM race_pdf_urls
            WHERE race_id = %s
        """, (race_id,))
        
        pdf_urls = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            "race_info": {
                "race_id": race_info['race_id'],
                "name_race": race_info['name_race'],
                "discipline": race_info['discipline'],
                "date": race_info['date'],
                "place_race": race_info['place_race'],
                "is_relay": True,
                "pdf_urls": pdf_urls
            },
            "results": teams,
            "results_count": len(teams)
        })
        
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({"error": str(e)}), 500
@app.route('/api/race/<path:race_id>/results', methods=['GET'])
def get_race_results(race_id):
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Извлекаем пол из race_id (если есть)
        clean_race_id = race_id
        gender = None
        
        # Проверяем окончание race_id
        if race_id.endswith('_М'):
            clean_race_id = race_id[:-2]
            gender = 'М'
        elif race_id.endswith('_Ж'):
            clean_race_id = race_id[:-2]
            gender = 'Ж'
        
        # Получаем информацию о гонке
        cursor.execute("""
            SELECT 
                r.race_id,
                r.name_race,
                r.discipline,
                r.date,
                r.place_race
            FROM races r
            WHERE r.race_id = %s
        """, (clean_race_id,))
        
        race_info = cursor.fetchone()
        
        if not race_info:
            return jsonify({"error": "Гонка не найдена"}), 404
        
        # Преобразуем дату
        if race_info['date']:
            race_info['date'] = race_info['date'].strftime('%Y-%m-%d')
        
        # Получаем результаты спортсменов с учетом пола
        if gender:
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
                    a.sports_rank,
                    a.gender as athlete_gender
                FROM results r
                JOIN athlete a ON r.athlete_id = a.athlete_id
                WHERE r.race_id = %s AND a.gender = %s
                ORDER BY r.finish_place ASC
            """, (clean_race_id, gender))
        else:
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
                    a.sports_rank,
                    a.gender as athlete_gender
                FROM results r
                JOIN athlete a ON r.athlete_id = a.athlete_id
                WHERE r.race_id = %s
                ORDER BY r.finish_place ASC
            """, (clean_race_id,))
        
        results = cursor.fetchall()
        
        # Получаем PDF URL
        cursor.execute("""
            SELECT pdf_url, gender
            FROM race_pdf_urls
            WHERE race_id = %s
        """, (clean_race_id,))
        
        pdf_urls = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            "race_info": {
                "race_id": race_info['race_id'],
                "name_race": race_info['name_race'],
                "discipline": race_info['discipline'],
                "date": race_info['date'],
                "place_race": race_info['place_race'],
                "gender": gender,
                "pdf_urls": pdf_urls
            },
            "results": results,
            "results_count": len(results)
        })
        
    except Exception as e:
        print(f"Error: {e}")
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

# ========== АВТОРИЗАЦИЯ ==========

@app.route('/api/auth/register', methods=['POST'])
def register():
    """Регистрация нового пользователя"""
    try:
        data = request.get_json()
        email = data.get('email', '').strip().lower()
        password = data.get('password', '')
        
        # Валидация
        if not email or not password:
            return jsonify({'error': 'Email и пароль обязательны'}), 400
        
        if not validate_email(email):
            return jsonify({'error': 'Некорректный email'}), 400
        
        if not validate_password(password):
            return jsonify({'error': 'Пароль должен быть не менее 6 символов'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем, существует ли пользователь
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        if cursor.fetchone():
            conn.close()
            return jsonify({'error': 'Пользователь с таким email уже существует'}), 400
        
        # Создаем пользователя
        password_hash = hash_password(password)
        cursor.execute("""
            INSERT INTO users (email, password_hash, created_at)
            VALUES (%s, %s, NOW())
        """, (email, password_hash))
        conn.commit()
        
        user_id = cursor.lastrowid
        token = generate_token(user_id, email)
        
        conn.close()
        
        return jsonify({
            'success': True,
            'token': token,
            'user': {
                'id': user_id,
                'email': email
            }
        }), 201
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/auth/login', methods=['POST'])
def login():
    """Вход пользователя"""
    try:
        data = request.get_json()
        email = data.get('email', '').strip().lower()
        password = data.get('password', '')
        
        if not email or not password:
            return jsonify({'error': 'Email и пароль обязательны'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("SELECT id, email, password_hash FROM users WHERE email = %s", (email,))
        user = cursor.fetchone()
        
        if not user or not verify_password(password, user['password_hash']):
            conn.close()
            return jsonify({'error': 'Неверный email или пароль'}), 401
        
        # Обновляем время последнего входа
        cursor.execute("UPDATE users SET last_login = NOW() WHERE id = %s", (user['id'],))
        conn.commit()
        
        token = generate_token(user['id'], user['email'])
        
        conn.close()
        
        return jsonify({
            'success': True,
            'token': token,
            'user': {
                'id': user['id'],
                'email': user['email']
            }
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/auth/logout', methods=['POST'])
@token_required
def logout():
    """Выход пользователя (просто удаляем токен на клиенте)"""
    return jsonify({'success': True})

@app.route('/api/auth/me', methods=['GET'])
@token_required
def get_current_user():
    """Получить информацию о текущем пользователе"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT id, email, created_at, last_login
            FROM users WHERE id = %s
        """, (request.user_id,))
        user = cursor.fetchone()
        
        conn.close()
        
        if user:
            # Преобразуем даты в строки
            if user['created_at']:
                user['created_at'] = user['created_at'].strftime('%Y-%m-%d %H:%M:%S')
            if user['last_login']:
                user['last_login'] = user['last_login'].strftime('%Y-%m-%d %H:%M:%S')
            
            return jsonify({'user': user})
        else:
            return jsonify({'error': 'Пользователь не найден'}), 404
            
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# ========== СБРОС ПАРОЛЯ ==========

@app.route('/api/auth/forgot-password', methods=['POST'])
def forgot_password():
    """Запрос на сброс пароля"""
    try:
        data = request.get_json()
        email = data.get('email', '').strip().lower()
        
        if not email:
            return jsonify({'error': 'Email обязателен'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем, существует ли пользователь
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        user = cursor.fetchone()
        
        if not user:
            conn.close()
            # Не показываем, что email не найден (безопасность)
            return jsonify({
                'success': True,
                'message': 'Если пользователь существует, инструкция отправлена на email'
            })
        
        # Генерируем уникальный токен
        reset_token = secrets.token_urlsafe(32)
        expires_at = datetime.now() + timedelta(hours=1)
        
        # Сохраняем токен в БД
        cursor.execute("""
            INSERT INTO password_resets (email, token, expires_at)
            VALUES (%s, %s, %s)
        """, (email, reset_token, expires_at))
        conn.commit()
        
        # Отправляем email
        email_sent = send_reset_email(email, reset_token)
        
        conn.close()
        
        return jsonify({
            'success': True,
            'message': 'Инструкция по сбросу пароля отправлена на email'
        })
        
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'error': str(e)}), 500

@app.route('/api/auth/reset-password', methods=['POST'])
def reset_password():
    """Установка нового пароля по токену"""
    try:
        data = request.get_json()
        token = data.get('token', '')
        new_password = data.get('new_password', '')
        
        if not token or not new_password:
            return jsonify({'error': 'Токен и новый пароль обязательны'}), 400
        
        if len(new_password) < 6:
            return jsonify({'error': 'Пароль должен быть не менее 6 символов'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем токен
        cursor.execute("""
            SELECT email, expires_at, used
            FROM password_resets
            WHERE token = %s
        """, (token,))
        
        reset_record = cursor.fetchone()
        
        if not reset_record:
            conn.close()
            return jsonify({'error': 'Недействительный токен'}), 400
        
        if reset_record['used']:
            conn.close()
            return jsonify({'error': 'Токен уже использован'}), 400
        
        if datetime.now() > reset_record['expires_at']:
            conn.close()
            return jsonify({'error': 'Токен истек'}), 400
        
        # Обновляем пароль пользователя
        new_password_hash = hash_password(new_password)
        cursor.execute("""
            UPDATE users
            SET password_hash = %s
            WHERE email = %s
        """, (new_password_hash, reset_record['email']))
        
        # Отмечаем токен как использованный
        cursor.execute("""
            UPDATE password_resets
            SET used = TRUE
            WHERE token = %s
        """, (token,))
        
        conn.commit()
        conn.close()
        
        return jsonify({
            'success': True,
            'message': 'Пароль успешно изменен'
        })
        
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'error': str(e)}), 500

@app.route('/api/auth/verify-reset-token', methods=['GET'])
def verify_reset_token():
    """Проверка валидности токена сброса"""
    try:
        token = request.args.get('token', '')
        
        if not token:
            return jsonify({'valid': False, 'error': 'Токен отсутствует'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT expires_at, used
            FROM password_resets
            WHERE token = %s
        """, (token,))
        
        record = cursor.fetchone()
        conn.close()
        
        if not record:
            return jsonify({'valid': False, 'error': 'Токен не найден'}), 404
        
        if record['used']:
            return jsonify({'valid': False, 'error': 'Токен уже использован'}), 400
        
        if datetime.now() > record['expires_at']:
            return jsonify({'valid': False, 'error': 'Токен истек'}), 400
        
        return jsonify({'valid': True})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# ========== ИЗБРАННОЕ (с привязкой к пользователю) ==========

@app.route('/api/favorites', methods=['GET'])
@token_required
def get_favorites():
    """Получить всех избранных спортсменов текущего пользователя"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT a.*
            FROM user_favorites uf
            JOIN athlete a ON uf.athlete_id = a.athlete_id
            WHERE uf.user_id = %s
            ORDER BY a.last_name, a.first_name
        """, (request.user_id,))
        
        favorites = cursor.fetchall()
        conn.close()
        
        # Преобразуем даты
        for fav in favorites:
            if fav['birth_date']:
                fav['birth_date'] = fav['birth_date'].strftime('%Y-%m-%d')
        
        return jsonify(favorites)
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/favorites', methods=['POST'])
@token_required
def add_favorite():
    """Добавить спортсмена в избранное"""
    try:
        data = request.get_json()
        athlete_id = data.get('athlete_id')
        
        if not athlete_id:
            return jsonify({'error': 'athlete_id обязателен'}), 400
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        # Проверяем, существует ли спортсмен
        cursor.execute("SELECT athlete_id FROM athlete WHERE athlete_id = %s", (athlete_id,))
        if not cursor.fetchone():
            conn.close()
            return jsonify({'error': 'Спортсмен не найден'}), 404
        
        # Добавляем в избранное
        cursor.execute("""
            INSERT IGNORE INTO user_favorites (user_id, athlete_id)
            VALUES (%s, %s)
        """, (request.user_id, athlete_id))
        conn.commit()
        
        conn.close()
        
        return jsonify({'success': True, 'message': 'Спортсмен добавлен в избранное'})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/favorites/<athlete_id>', methods=['DELETE'])
@token_required
def remove_favorite(athlete_id):
    """Удалить спортсмена из избранного"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            DELETE FROM user_favorites
            WHERE user_id = %s AND athlete_id = %s
        """, (request.user_id, athlete_id))
        conn.commit()
        
        conn.close()
        
        return jsonify({'success': True, 'message': 'Спортсмен удален из избранного'})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/favorites/check/<athlete_id>', methods=['GET'])
@token_required
def check_favorite(athlete_id):
    """Проверить, находится ли спортсмен в избранном"""
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT 1 FROM user_favorites
            WHERE user_id = %s AND athlete_id = %s
        """, (request.user_id, athlete_id))
        
        is_favorite = cursor.fetchone() is not None
        conn.close()
        
        return jsonify({'is_favorite': is_favorite})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500
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

# Для отправки пуш уведомлений
scheduler = BackgroundScheduler()

scheduler.add_job(
    func=check_and_send_race_notifications,
    trigger="cron",
    hour=8,
    minute=30,
    id="race_notifications",
    replace_existing=True
)

scheduler.start()

atexit.register(lambda: scheduler.shutdown())

if __name__ == '__main__':
    # Для продакшена используем порт из переменной окружения
    port = int(os.environ.get('PORT', 5000))
    
    print("🚀 Biathlon API запущен!")
    #print("📊 База данных:", Config.MYSQL_DATABASE)
    print("🌐 API доступен на: http://0.0.0.0:" + str(port))
    print("⏹️  Остановка: Ctrl+C")
    
    app.run(host='0.0.0.0', port=port, debug=False)  # debug=False для продакшена!