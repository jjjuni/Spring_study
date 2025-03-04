package umc.spring.apiPayload.exception;

import umc.spring.apiPayload.code.BaseErrorCode;

public class ErrorException extends GeneralException {

    public ErrorException(BaseErrorCode errorCode){
        super(errorCode);
    }
}
