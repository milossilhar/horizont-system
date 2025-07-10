package sk.leziemevpezinku.spring.util.ddl;

import org.hibernate.dialect.PostgreSQLDialect;

/**
 * Some specific settings for using with DDL profile generator.
 * ONLY used in generating DDL schema.
 */
public class DdlPostgreSQLDialect extends PostgreSQLDialect {

    @Override
    public String getCheckCondition(String columnName, String[] values) {
        // We do not want enum database checks
        return null;
    }

    @Override
    public String getCheckCondition(String columnName, Class<? extends Enum<?>> enumClass) {
        // We do not want enum database checks
        return null;
    }
}
