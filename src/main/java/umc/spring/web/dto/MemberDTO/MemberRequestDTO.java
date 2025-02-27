package umc.spring.web.dto.MemberDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDTO{
        @NotNull(message = "이름은 필수 항목입니다.")
        String name;
        Integer gender;
        Integer birthYear;
        Integer birthMonth;
        Integer birthDay;
        @NotNull(message = "주소는 필수 항목입니다.")
        String address;
        @NotNull(message = "주소는 필수 항목입니다.")
        String specAddress;
        @ExistCategories
        List<Long> preferCategory;
    }
}
