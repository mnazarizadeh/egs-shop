package com.egs.shop.exception;

import com.egs.shop.model.constant.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class CommentAccessDeniedException extends EGSRuntimeException {

    public CommentAccessDeniedException(String message) {
        super(message);
    }

    public CommentAccessDeniedException() {
        this(ExceptionMessages.COMMENT_ACCESS_DENIED);
    }
}
