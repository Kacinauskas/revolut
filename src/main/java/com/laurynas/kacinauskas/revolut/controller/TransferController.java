package com.laurynas.kacinauskas.revolut.controller;

import com.laurynas.kacinauskas.revolut.domain.Transfer;
import com.laurynas.kacinauskas.revolut.dto.ResultDto;
import com.laurynas.kacinauskas.revolut.dto.TransferDto;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
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
        TransferDto transferDTO = getTransformer().fromJson(request.body(), TransferDto.class);

        if (!RequestValidator.getValidator().validate(transferDTO).isEmpty()) {
            throw new GeneralValidationException(ErrorCode.BAD_REQUEST);
        }

        Transfer transfer = getInstance(TransferService.class).doTransfer(transferDTO);

        response.status(HttpStatus.CREATED_201);
        return getTransformer().toJson(new ResultDto<>(transfer.getId().toString()));
    }

}
