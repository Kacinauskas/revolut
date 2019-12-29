package com.laurynas.kacinauskas.revolut.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class RequestValidator {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    private RequestValidator() {
    }

    public static Validator getValidator() {
        return validator;
    }

}
