# expressions and variables
MAVEN := './mvnw'
SQL_SCHEMA_DIR := './db/reference'

DOCKER_LOCAL_FILE := './docker/compose.dev.yaml'
DOCKER_TEST_FILE := './docker/compose.test.yaml'

# generate sql schema script from hibernate
generate-model:
    {{MAVEN}} test-compile -Pddl
    cp ./target/generated-resources/sql/ddl/auto/postgresql.sql {{SQL_SCHEMA_DIR}}/schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -ri 's/^ {4}//g' schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -rzi 's/alter table if exists ([[:print:]]+).*\n.*add constraint ([[:alnum:]]+).*\n.*foreign key \(([[:print:]]+)\)/create index \2 on \1(\3);\n\0/gm' schema.sql

# generates openapi yaml from spring controllers
generate-openapi:
    {{MAVEN}} verify -Popenapi

# runs SpringBoot app in developoment mode
dev:
    {{MAVEN}} spring-boot:run

# runs SpringBoot app in development mode with DEBUG enabled
debug:
    {{MAVEN}} spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

# compiles whole project
compile:
    {{MAVEN}} compile

# tests application
test:
    docker compose -f {{DOCKER_TEST_FILE}} up -d
    echo "Waiting for postgres to start" && sleep 10
    {{MAVEN}} test
    docker compose -f {{DOCKER_TEST_FILE}} down -v

# starts TEST postgres in Docker
testdbstart:
    docker compose -f {{DOCKER_TEST_FILE}} up -d

# stops and removes TEST postgres in Docker
testdbstop:
    docker compose -f {{DOCKER_LOCAL_FILE}} down -v

# starts LOCAL postgres in Docker
dbstart:
    docker compose -f {{DOCKER_LOCAL_FILE}} up -d

# stops and removes LOCAL postgres in Docker
dbstop:
    docker compose -f {{DOCKER_LOCAL_FILE}} down -v
