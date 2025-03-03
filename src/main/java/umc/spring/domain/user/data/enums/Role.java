package umc.spring.domain.user.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    NOT_REGISTERED("ROLE_NOT_REGISTERED"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String key;
}
