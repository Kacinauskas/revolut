package com.laurynas.kacinauskas.revolut;

import org.flywaydb.core.Flyway;

public class RevolutApplication {

    public static void main(String[] args) {
        setupDBMigration();
    }

    private static void setupDBMigration() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:revolut;DB_CLOSE_DELAY=-1", "sa", "").load();
        flyway.migrate();
    }

}
