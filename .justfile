# expressions and variables
MAVEN := './mvnw'
SQL_SCHEMA_DIR := './db/reference'

# generate sql schema script from hibernate
generate-model:
    {{MAVEN}} test-compile -Pddl
    cp ./target/generated-resources/sql/ddl/auto/postgresql.sql {{SQL_SCHEMA_DIR}}/schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -ri 's/^ {4}//g' schema.sql
    cd {{SQL_SCHEMA_DIR}} && sed -rzi 's/alter table if exists ([[:print:]]+).*\n.*add constraint ([[:alnum:]]+).*\n.*foreign key \(([[:print:]]+)\)/create index \2 on \1(\3);\n\0/gm' schema.sql

# runs SpringBoot app in developoment mode
dev:
    {{MAVEN}} spring-boot:run

# compiles whole project
compile:
    {{MAVEN}} compile

# starts local postgres in Docker
startdb:
    docker compose -f ./docker/compose.local.yaml up -d