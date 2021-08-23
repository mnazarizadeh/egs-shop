package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserNotActivatedException extends EGSRuntimeException {

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException() {
        this(ExceptionMessages.USER_NOT_ACTIVATED);
    }
}
