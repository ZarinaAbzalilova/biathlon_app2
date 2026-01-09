# config.py
import os
from dotenv import load_dotenv

load_dotenv()

class Config:
    # Для локальной разработки (значения по умолчанию)
    # Для продакшена на Render.com значения будут браться из переменных окружения
    
    MYSQL_HOST = os.getenv('MYSQL_HOST', 'localhost')
    MYSQL_USER = os.getenv('MYSQL_USER', 'root')
    MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD', 'admin')
    MYSQL_DATABASE = os.getenv('MYSQL_DATABASE', 'biathlon')
    MYSQL_PORT = os.getenv('MYSQL_PORT', '3306')
    
    # Получение конфигурации для подключения к БД
    @classmethod
    def get_db_config(cls):
        return {
            'host': cls.MYSQL_HOST,
            'user': cls.MYSQL_USER,
            'password': cls.MYSQL_PASSWORD,
            'database': cls.MYSQL_DATABASE,
            'port': int(cls.MYSQL_PORT)
        }