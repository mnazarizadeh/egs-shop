package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends EGSRuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException() {
        this(ExceptionMessages.PRODUCT_NOT_FOUND);
    }
}
