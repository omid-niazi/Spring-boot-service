package org.maktab.houseservice.model;

public class ErrorApiResponse extends ApiResponse {
    protected ErrorApiResponse(ApiResponseType apiResponseType, String message, Object errors, Object data) {
        super(apiResponseType, message, errors, data);
    }

    public static ApiResponse withError(Object error) {
        return new ErrorApiResponse(ApiResponseType.error, null, error, null);
    }

    public static ApiResponse withMessage(String message) {
        return new ErrorApiResponse(ApiResponseType.error, message, null, null);
    }

    public static ApiResponse withMessageAndError(String message, Object error) {
        return new ErrorApiResponse(ApiResponseType.error, message, error, null);
    }

}
