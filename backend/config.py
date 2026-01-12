# config.py
import os
from dotenv import load_dotenv

# Явно загружаем .env из текущей папки
env_path = os.path.join(os.path.dirname(__file__), '.env')
load_dotenv(env_path)

class Config:
    # Принудительное чтение из .env
    MYSQL_HOST = os.getenv('MYSQL_HOST', '79.174.89.233')
    MYSQL_PORT = os.getenv('MYSQL_PORT', '19426')
    MYSQL_DATABASE = os.getenv('MYSQL_DATABASE', 'biathlon_db')
    MYSQL_USER = os.getenv('MYSQL_USER', 'adminZarina')
    MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD', '')
    @classmethod
    def get_db_config(cls):
        return {
            'host': cls.MYSQL_HOST,
            'port': int(cls.MYSQL_PORT) if cls.MYSQL_PORT else 19426,
            'user': cls.MYSQL_USER,
            'password': cls.MYSQL_PASSWORD,
            'database': cls.MYSQL_DATABASE,
        }
    @classmethod
    def print_config(cls):
        """Отладочный вывод"""
        print("=== CONFIG ===")
        print(f"HOST: {cls.MYSQL_HOST}")
        print(f"PORT: {cls.MYSQL_PORT}")
        print(f"DB: {cls.MYSQL_DATABASE}")
        print(f"USER: {cls.MYSQL_USER}")
        print(f"PWD SET: {'Да' if cls.MYSQL_PASSWORD else 'Нет'}")

# Выводим конфиг при импорте
Config.print_config()