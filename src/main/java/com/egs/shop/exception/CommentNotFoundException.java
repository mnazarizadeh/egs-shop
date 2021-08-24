package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends EGSRuntimeException {

    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException() {
        this(ExceptionMessages.COMMENT_NOT_FOUND);
    }
}
