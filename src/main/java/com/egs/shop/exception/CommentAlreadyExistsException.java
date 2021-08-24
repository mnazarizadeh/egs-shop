package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CommentAlreadyExistsException extends EGSRuntimeException {

    public CommentAlreadyExistsException(String message) {
        super(message);
    }

    public CommentAlreadyExistsException() {
        this(ExceptionMessages.COMMENT_ALREADY_EXISTS);
    }
}
