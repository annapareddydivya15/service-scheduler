package com.intuit.schedule.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;
}