package com.example.ecommerce.exceptions.global;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter

public class InditexConxServiceError {
    private final Map<String, String[]> parametersError;
    private final String error;
}
