package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailAlreadyUsedException extends EGSRuntimeException {

    public EmailAlreadyUsedException(String message) {
        super(message);
    }

    public EmailAlreadyUsedException() {
        this(ExceptionMessage.EMAIL_ALREADY_EXISTS);
    }
}
