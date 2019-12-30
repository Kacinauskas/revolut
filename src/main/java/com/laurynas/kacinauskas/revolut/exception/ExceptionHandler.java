package com.laurynas.kacinauskas.revolut.exception;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;

import static com.laurynas.kacinauskas.revolut.util.JsonTransformer.getTransformer;

public class ExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    private ExceptionHandler() {
    }

    public static void handle(Exception exception, Response response) {
        if (exception.getCause() instanceof GeneralValidationException) {
            LOG.error("General validation exception: ", exception);
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(getTransformer().toJson(new GeneralError(exception.getCause().getMessage())));
        } else {
            LOG.error("Unexpected application exception: ", exception);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body("");
        }
    }

}
