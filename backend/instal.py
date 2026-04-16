import subprocess
import sys

packages = ['flask', 'flask-cors', 'mysql-connector-python', 'python-dotenv','bcrypt']

print("Устанавливаем необходимые пакеты...")

for package in packages:
    print(f"Устанавливаем {package}...")
    try:
        subprocess.check_call([sys.executable, '-m', 'pip', 'install', package])
        print(f"✅ {package} установлен")
    except subprocess.CalledProcessError:
        print(f"❌ Ошибка установки {package}")

print("Установка завершена!")