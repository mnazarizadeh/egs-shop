package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RateAlreadyExistsException extends EGSRuntimeException {

    public RateAlreadyExistsException(String message) {
        super(message);
    }

    public RateAlreadyExistsException() {
        this(ExceptionMessages.RATE_ALREADY_EXISTS);
    }
}
