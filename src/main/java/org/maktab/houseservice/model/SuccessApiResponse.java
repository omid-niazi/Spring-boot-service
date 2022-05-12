package org.maktab.houseservice.model;

import java.util.List;

public class SuccessApiResponse extends ApiResponse {
    protected SuccessApiResponse(ApiResponseType apiResponseType, String message, Object errors, Object data) {
        super(apiResponseType, message, errors, data);
    }

    public static ApiResponse withData(Object data) {
        return new SuccessApiResponse(ApiResponseType.success, null, null, data);
    }

    public static ApiResponse withMessage(String message) {
        return new SuccessApiResponse(ApiResponseType.success, message, null, null);
    }

    public static ApiResponse withMessageAndData(String message, Object data) {
        return new SuccessApiResponse(ApiResponseType.success, message, null, data);
    }

}
