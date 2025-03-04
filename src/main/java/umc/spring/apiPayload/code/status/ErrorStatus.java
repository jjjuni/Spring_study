package umc.spring.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.spring.apiPayload.code.BaseErrorCode;
import umc.spring.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // Test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트"),

    // Page
    NOT_PAGE(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 요청이 잘못되었습니다. (page = 1 이상의 int)"),

    // User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "존재하지 않는 사용자입니다,"),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "닉네임은 필수입니다."),
    NOT_AUTHENTICATED(HttpStatus.BAD_REQUEST, "USER4003", "인증되지 않은 사용자입니다."),
    // Token
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
    JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4002", "토큰을 찾을 수 없습니다."),

    // FoodCategory
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "FOOD_CATEGORY4001", "음식 카테고리를 찾을 수 없습니다."),

    // Region
    REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "REGION4001", "국가를 찾을 수 없습니다."),

    // Store
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE4001", "가게를 찾을 수 없습니다."),

    // Mission
    MISSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MISSION4001", "미션을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
