package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown in case of a blocked user trying to authenticate.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserBlockedException extends EGSRuntimeException {

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException() {
        this(ExceptionMessage.USER_BLOCKED);
    }
}
