set -e

# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='033[0;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Функция помощи
show_help() {
    echo "Usage: $0 [service] [environment] [options]"
    echo ""
    echo "Services:"
    echo "  all         - All services (default)"
    echo "  user        - User service only"
    echo "  project     - Project service only"
    echo "  task        - Task service only"
    echo ""
    echo "Environments:"
    echo "  local       - Local development (default)"
    echo "  dev         - Development server"
    echo "  test        - Test environment"
    echo ""
    echo "Options:"
    echo "  --dry-run   - Generate SQL without executing"
    echo "  --help      - Show this help"
    echo ""
    echo "Examples:"
    echo "  $0                    # All services, local"
    echo "  $0 user local         # User service, local"
    echo "  $0 all local --dry-run # Dry run all services"
}

# Параметры
SERVICE=${1:-all}
ENVIRONMENT=${2:-local}
DRY_RUN=false

# Обработка опций
shift 2 2>dev/null || true
while [[ $# -gt 0 ]]; do
  case $1 in
    --dry-run)
      DRY_RUN=true
      shift
      ;;
    --help)
      show_help
      exit 0
      ;;
    *)
      echo -e "${RED}Unknown option $1${NC}"
      show_help
      exit 1
      ;;
  esac
done

echo -e "${BLUE}PTMS Database Migration${NC}"
echo -e "${BLUE}SERVICE: ${SERVICE}, Environment: ${ENVIRONMENT}${NC}"

#Загружаем переменные окружения
if [[ -f "../.env" ]]; then
    echo -e "${GREEN}Loading environment from .env file${NC}"
    set -a
    source ../.env
    set +a
else
  echo -e "${YELLOW}Warning. .env file not found, using system environment variables${NC}"

fi

#Определяем changelog
case $SERVICE in
  "all")
      CHANGELOG="classpath:changelogs:/master-all-services.yaml"
      echo -e "${YELLOW}Migrating All Services...${NC}"
      ;;
  "user_profile")
      CHANGELOG="classpath:changelogs:/db/changelog/user_details_master_changelog.yaml"
      echo -e "${YELLOW}Migrating User Service...${NC}"
      ;;
  "project")
      CHANGELOG="classpath:changelogs:/db/changelog/project_service-master_changelog.yaml"
      echo -e "${YELLOW}Migrating Project Service...${NC}"
    ;;
  "task")
      CHANGELOG="classpath:changelogs:/db/changelog/task_master_changelog.yaml"
      echo -e "${YELLOW}Migrating Task Service...${NC}"
      ;;
  *)
    echo -e "${RED}Unknown service: ${SERVICE}${NC}"
    show_help
    exit 1
    ;;
esac

# Проверяем доступность БД
echo -e "${BLUE}Проверка доступности БД...${NC}"
if ! pg_ready -h "${DB_HOST:-localhost}" -p "${DB_PORT:-5432}" -U "${DB_USER:-postgres}" >/dev/null 2>&1; then
    echo -e "${RED}База данных недоступна по адресу: ${DB_HOST:-localhost}:${DB_PORT:-5432}${NC}"
    echo -e "${YELLOW}Попробуйте перезапустить docker-compose up -d${NC}"
    exit 1
fi

echo -e "${GREEN}База данных доступна${NC}"

# Переход в папку migrations
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")") && pwd)"
cd "$SCRIPT_DIR/.."

#Проверяем что jar-файл собран
if [[ ! -f "target/migrations-1.0.0.jar" ]]; then
  echo -e "${YELLOW}Сборка модуля миграций...${NC}"
  mvn clean package -q
fi

#Формируем команду
CMD_ARGS=(
  "--spring.liquibase.change-log=${CHANGELOG}"
  "--spring.liquibase.contexts=${LIQUIBASE_CONTEXTS:-local}"
  "--spring.datasource.url=${DB_URL}"
  "--spring.datasource.username=${DB_USER}"
  "--spring.datasource.password=${DB_PASSWORD}"
  "--spring.profiles.active=${ENVIRONMENT}"
)

if [[ "$DRY_RUN"=="true" ]]; then
  echo -e "${YELLOW}DRY RUN: Generating SQL without executing...${NC}"
  java -jar target/migrations-1.0.0.jar \
    "${CMD_ARGS[@]}" \
    --spring.liquibase.should-run=false \
    --logging.level.liquibase=DEBUG \
    > "migration-${SERVICE}-${ENVIRONMENT}.sql"
  echo -e "${GREEN}SQL Сгенерирован: миграция-${SERVICE}-${ENVIRONMENT}.sql${NC}"
  exit 0
fi

#Выполняем миграцию
echo -e "${BLUE}Запускаем миграции...${NC}"
java -jar target/migrations-1.0.0.jar "${CMD_ARGS[@]}"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Миграция завершена успешно!${NC}"

    #Показываем последние выполенные миграции
    echo -e "${BLUE}Последние миграции:${NC}"
    PGPASSWORD="$DB_PASSWORD" psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -d "${DB_PASSWORD}" -c "
      SELECT id, author, dateexecuted, description
      FROM databasechangelog
      ORDER BY dateexecuted DESC
      LIMIT 5;
    " 2>dev/null || echo "Не удалось определить статус миграции"
else
  echo -e "${RED}Миграция завершилась ошибкой${NC}"
  exit 1
fi