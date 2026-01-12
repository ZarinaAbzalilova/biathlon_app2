# database.py
import mysql.connector
from config import Config

def get_db_connection():
    """Подключение к БД Reg.ru с SSL"""
    try:
        config = Config.get_db_config()  # ← Используем метод из Config
        
        # Добавляем SSL параметры
        config.update({
            'use_pure': True,
            'ssl_disabled': False,
            'ssl_verify_cert': False
        })
        
        print(f"🔄 Подключение к {config['host']}:{config['port']}")
        conn = mysql.connector.connect(**config)
        print("✅ Подключение успешно")
        return conn
        
    except Exception as e:
        print(f"❌ Ошибка подключения: {e}")
        print(f"Конфиг: {config}")
        raise