print("=" * 50)
print("ЗАПУСК СЕРВЕРА FLASK")
print("=" * 50)

# Проверяем установленные библиотеки
try:
    import flask
    print("✅ Flask установлен")
except ImportError:
    print("❌ Flask НЕ установлен")
    exit()

# Создаем простое приложение
from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/')
def home():
    return """
    <h1>Сервер работает! 🎉</h1>
    <p>Тестовые ссылки:</p>
    <ul>
        <li><a href="/api/test">/api/test</a></li>
        <li><a href="/api/athletes">/api/athletes</a></li>
    </ul>
    """

@app.route('/api/test')
def test():
    return jsonify({"status": "success", "message": "API работает!"})

@app.route('/api/athletes')
def athletes():
    return jsonify([
        {"id": 1, "name": "Тестовый спортсмен 1", "rank": "МСМК"},
        {"id": 2, "name": "Тестовый спортсмен 2", "rank": "МС"}
    ])

if __name__ == '__main__':
    print("🌐 Сервер запускается...")
    print("📱 Откройте в браузере: http://localhost:5000")
    print("⏹️  Для остановки нажмите Ctrl+C")
    print("=" * 50)
    app.run(host='0.0.0.0', port=5000, debug=True)