package umc.spring.domain.auth.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AuthRequestDTO {

    @Getter
    @Setter
    public static class AuthDTO{
        @NotNull
        String email;
    }
}
