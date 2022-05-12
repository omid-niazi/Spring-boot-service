package org.maktab.houseservice.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class ApiResponse {
    private ApiResponseType apiResponseType;
    private String message;
    private Object errors;
    private Object data;

    protected ApiResponse(ApiResponseType apiResponseType, String message, Object errors, Object data) {
        this.apiResponseType = apiResponseType;
        this.message = message;
        this.errors = errors;
        this.data = data;
    }

    @JsonProperty("type")
    public ApiResponseType getApiResponseType() {
        return apiResponseType;
    }

    public void setApiResponseType(ApiResponseType apiResponseType) {
        this.apiResponseType = apiResponseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    enum ApiResponseType {
        error, success
    }
}
