package com.laurynas.kacinauskas.revolut.exception;

import org.eclipse.jetty.http.HttpStatus;
import spark.Response;

import static com.laurynas.kacinauskas.revolut.util.JsonTransformer.getTransformer;

public class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static void handle(Exception exception, Response response) {
        if (exception.getCause() instanceof GeneralValidationException) {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(getTransformer().toJson(new GeneralError(exception.getCause().getMessage())));
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body("");
        }
    }

}
