package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class RateAccessDeniedException extends EGSRuntimeException {

    public RateAccessDeniedException(String message) {
        super(message);
    }

    public RateAccessDeniedException() {
        this(ExceptionMessages.RATE_ACCESS_DENIED);
    }
}
