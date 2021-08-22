package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyUsedException extends EGSRuntimeException {

    public UsernameAlreadyUsedException(String message) {
        super(message);
    }

    public UsernameAlreadyUsedException() {
        this(ExceptionMessage.USERNAME_ALREADY_EXISTS);
    }
}
