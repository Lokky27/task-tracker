#Загружаем переменные окружения
if [[ -f "../.env" ]]; then
  set -a
  source ../.env
  set +a
fi

SERVICE=${1:-all}
ENVIRONMENT=${2:-local}

echo "Статус миграций БД"
echo "========================"
echo "Сервис: $SERVICE"
echo "Окружение: $ENVIRONMENT"
echo "База данных: ${DB_NAME} на хосте: ${DB_HOST}:${DB_PORT}"
echo ""

#Проверяем подключение
if ! pg_isready -h "${DB_HOST}" -p "${DB_PORT}" -u "${DB_USER}" >/dev/null 2>&1; then
  echo "❌ База данных недоступна!"
  exit 1
fi

echo "📊 Статистика миграций:"
PASSWORD="${DB_PASSWORD}" psql -h "${DB_HOST}" -p "${DB_PORT}" -u "${DB_USER}" -d "${DB_NAME}" -c "
  SELECT
    COUNT(*) as total_changesets,
    COUNT(DISTINCT author) as authors,
    MAX(dateexecuted) as last_migration
  FROM databasechangelog;
"

echo ""
echo "🗄️ Схемы базы данных:"
PASSWORD="$DB_PASSWORD" psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -d "${DB_NAME}" -c "
  SELECT
    id,
    author,
    filename,
    dateexecuted,
    description
  FROM databasechangelog
  ORDER BY dateexecuted DESC
  LIMIT 10;
"
