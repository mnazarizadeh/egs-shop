package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RateNotFoundException extends EGSRuntimeException {

    public RateNotFoundException(String message) {
        super(message);
    }

    public RateNotFoundException() {
        this(ExceptionMessages.RATE_NOT_FOUND);
    }
}
