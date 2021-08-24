package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticatedUserNotFoundException extends EGSRuntimeException {

    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }

    public AuthenticatedUserNotFoundException() {
        this(ExceptionMessages.AUTHENTICATED_USER_NOT_FOUND);
    }
}
