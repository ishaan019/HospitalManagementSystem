package com.ss.doctorandscheduling.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int code;
    private String message;
    private String correlationId;
}

