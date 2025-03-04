package umc.spring.apiPayload.code;

public interface BaseErrorCode {

    String getMessage();

    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
