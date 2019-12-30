package com.laurynas.kacinauskas.revolut;

import com.laurynas.kacinauskas.revolut.controller.TransferController;
import com.laurynas.kacinauskas.revolut.exception.ExceptionHandler;
import org.flywaydb.core.Flyway;

import static spark.Spark.*;

public class RevolutApplication {

    private static final String API_VERSION_RESOURCE = "/api/v1";
    private static final String TRANSFER_RESOURCE = "/transfer";

    public static void main(String[] args) {
        setupDBMigration();

        before((request, response) -> response.type("application/json"));

        post(API_VERSION_RESOURCE.concat(TRANSFER_RESOURCE), TransferController::makeTransfer);

        exception(Exception.class, (exception, request, response) -> ExceptionHandler.handle(exception, response));
    }

    private static void setupDBMigration() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:revolut;DB_CLOSE_DELAY=-1", "sa", "").load();
        flyway.migrate();
    }

}
