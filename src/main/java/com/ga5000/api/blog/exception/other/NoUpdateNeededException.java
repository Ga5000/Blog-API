package com.ga5000.api.blog.exception.other;

import com.ga5000.api.blog.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NoUpdateNeededException extends BaseException {
    public NoUpdateNeededException() {
        super("The value you are trying to updated is the same as the existing one", HttpStatus.BAD_REQUEST);
    }
}
