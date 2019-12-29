package com.laurynas.kacinauskas.revolut.controller;

import com.laurynas.kacinauskas.revolut.configuration.RequestValidator;
import com.laurynas.kacinauskas.revolut.dto.TransferDTO;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralError;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import static com.laurynas.kacinauskas.revolut.configuration.JsonTransformer.getTransformer;

public class TransferController {

    private TransferController() {
    }

    public static String makeTransfer(Request request, Response response) {
        response.type("application/json");

        try {
            TransferDTO transferDTO = getTransformer().fromJson(request.body(), TransferDTO.class);
            if (!RequestValidator.getValidator().validate(transferDTO).isEmpty()) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return getTransformer().toJson(new GeneralError(ErrorCode.BAD_REQUEST.toString()));
            }

        } catch (Exception e) {
            if (e.getCause() instanceof GeneralValidationException) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return getTransformer().toJson(new GeneralError(e.getCause().getMessage()));
            }
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return "";
        }

        response.status(HttpStatus.CREATED_201);
        return "";
    }

}
