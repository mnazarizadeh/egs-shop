package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordMismatchedException extends EGSRuntimeException {

    public PasswordMismatchedException(String message) {
        super(message);
    }

    public PasswordMismatchedException() {
        this(ExceptionMessages.PASSWORD_NOT_MATCHED);
    }
}
