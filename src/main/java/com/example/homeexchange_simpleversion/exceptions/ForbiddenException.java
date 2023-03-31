package com.example.homeexchange_simpleversion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This home is offered.You can`t remove it! Delete offer first")
    public class ForbiddenException extends RuntimeException {
    }

