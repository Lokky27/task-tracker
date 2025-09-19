set -e

echo "Установка локальных переменных!"

#Проверяем что в мы в корне проекта
if [[ ! -f "pom.xml" ]]; then
  echo "Ошибка! Пожалуйста, запустите скрипт из корня проекта"
  exit 1
fi

#Создаем .env файл если его еще нет
if [[ ! -f ".env" ]]; then
  echo "Создаение .env файла из шаблона..."
  cp .env.example .env
  echo "✅ Файл .env создан! Пожалуйста, внесите изменения настроек локальных переменных"
  echo ""
fi

#Запускаем docker-compose вместе с PostgreSQL
echo "Ожидание готовности базы данных..."
# shellcheck disable=SC1083
for i in {1: 30}; do
  if pg_isready -h localhost -p 5432 >dev/null 2>&1; then
    break
  fi
  echo "Ожидание... ($i/30)"
  sleep 2
done

#Загружаем переменные окружения
source .env

#Создаем базу данных если не существует
echo "Создание базы данных если ее не существует"
PASSWORD="$PG_PASSWORD" createdb -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" "$DB_NAME" 2>dev/null || echo "База данных уже существует!"

#Собираем модуль с миграциями
echo "Сборка модуля с миграциями"
mvn clean package -pl ptms-migrations -q

#Запускаем миграции
echo "Запуск миграций"
cd ptms-migrations
./scripts/migrate.sh all local

echo ""
echo "✅ Local development environment is ready!"
echo ""
echo "Теперь вы можете:"
echo "    1. Запусть отдельные сервисные миграции: ./ptms-migrations/scripts/migrate.sh user local"
echo "    2. Проверить статус миграций: ./ptms-migrations/scripts/status.sh"
echo "    3. Запусть конкретный сервис: mvn spring-boot:run -pl services/user-service"
