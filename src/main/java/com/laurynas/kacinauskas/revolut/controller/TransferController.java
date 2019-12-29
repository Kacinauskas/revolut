package com.laurynas.kacinauskas.revolut.controller;

import com.laurynas.kacinauskas.revolut.domain.Transfer;
import com.laurynas.kacinauskas.revolut.dto.ResultDTO;
import com.laurynas.kacinauskas.revolut.dto.TransferDTO;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralError;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;
import com.laurynas.kacinauskas.revolut.service.TransferService;
import com.laurynas.kacinauskas.revolut.util.RequestValidator;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import static com.laurynas.kacinauskas.revolut.util.ClassInjector.getInstance;
import static com.laurynas.kacinauskas.revolut.util.JsonTransformer.getTransformer;

public class TransferController {

    private TransferController() {
    }

    public static String makeTransfer(Request request, Response response) {
        response.type("application/json");
        Transfer transfer;

        try {
            TransferDTO transferDTO = getTransformer().fromJson(request.body(), TransferDTO.class);
            if (!RequestValidator.getValidator().validate(transferDTO).isEmpty()) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return getTransformer().toJson(new GeneralError(ErrorCode.BAD_REQUEST.toString()));
            }

            transfer = getInstance(TransferService.class).doTransfer(transferDTO);
        } catch (Exception e) {
            if (e.getCause() instanceof GeneralValidationException) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return getTransformer().toJson(new GeneralError(e.getCause().getMessage()));
            }
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return "";
        }

        response.status(HttpStatus.CREATED_201);
        return getTransformer().toJson(new ResultDTO<>(transfer.getId().toString()));
    }

}
