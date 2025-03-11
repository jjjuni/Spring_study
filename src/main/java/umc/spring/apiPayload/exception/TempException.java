package umc.spring.apiPayload.exception;

import umc.spring.apiPayload.code.BaseErrorCode;

public class TempException extends GeneralException {

    public TempException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
