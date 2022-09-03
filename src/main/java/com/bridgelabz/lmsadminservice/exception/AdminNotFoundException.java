package com.bridgelabz.lmsadminservice.exception;

import com.bridgelabz.lmsadminservice.util.Response;
import org.springframework.web.bind.annotation.ResponseStatus;


    @ResponseStatus
    public class AdminNotFoundException extends RuntimeException{
        public Response getErrorResponse;
        private int statusCode;
        private String statusMessage;
        public AdminNotFoundException(int statusCode, String statusMessage) {
            super(statusMessage);
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }
    }
