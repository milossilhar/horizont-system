# expressions and variables
MAVEN := './mvnw'
SQL_SCHEMA_DIR := './db/reference'

DOCKER_PROD_FILE := './docker/compose.prod.yaml'
DOCKER_DEV_FILE := './docker/compose.dev.yaml'
DOCKER_OPENAPI_FILE := './docker/compose.openapi.yaml'

FRONTEND_PROJECT_DIR := '~/projects/leziemevpezinku/horizont-web'

# generate sql schema script from hibernate
generate-model:
    {{MAVEN}} test-compile -Pddl
    cp ./target/generated-resources/sql/ddl/auto/postgresql.sql {{SQL_SCHEMA_DIR}}/schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -ri 's/^ {4}//g' schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -rzi 's/alter table if exists ([[:print:]]+).*\n.*add constraint ([[:alnum:]]+).*\n.*foreign key \(([[:print:]]+)\)/create index \2 on \1(\3);\n\0/gm' schema.sql

# generates openapi yaml from spring controllers
generate-openapi:
    docker compose -f {{DOCKER_OPENAPI_FILE}} up -d
    sleep 5
    {{MAVEN}} verify -DskipTests=true -Popenapi
    cp ./target/openapi.json {{FRONTEND_PROJECT_DIR}}/openapi/openapi.json
    docker compose -f {{DOCKER_OPENAPI_FILE}} down -v

# runs SpringBoot app in production mode
prod:
    {{MAVEN}} spring-boot:run

# runs SpringBoot app in developoment mode
dev:
    {{MAVEN}} spring-boot:run -Dspring-boot.run.profiles=dev

# runs SpringBoot app in development mode with DEBUG enabled
debug:
    {{MAVEN}} spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

# cleans whole project
clean:
    {{MAVEN}} clean

# packages whole project
package:
    {{MAVEN}} package

# compiles whole project
compile:
    {{MAVEN}} compile

# runs unit tests on application
test:
    {{MAVEN}} test

# runs integration tests on application, test database must be created
inttest:
    {{MAVEN}} verify

# starts whole spring backend stack as production configuration in local Docker
start:
    docker compose -f {{DOCKER_PROD_FILE}} up -d --build

# stops whole spring backend stack
stop:
    docker compose -f {{DOCKER_PROD_FILE}} down -v

# starts LOCAL postgres in Docker
dbstart:
    docker compose -f {{DOCKER_DEV_FILE}} up -d

# stops and removes LOCAL postgres in Docker
dbstop:
    docker compose -f {{DOCKER_DEV_FILE}} down -v

# connects to LOCAL postgres in Docker
dbconnect:
    psql "postgresql://horizon_user:horizon_pass@localhost:5431/horizon"
