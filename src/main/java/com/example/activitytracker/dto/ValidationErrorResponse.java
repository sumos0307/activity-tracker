package com.example.activitytracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;
@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private int status;
    private String error;
    private Map <String,String>errors;
}
