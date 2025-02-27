package umc.spring.web.dto.ReviewDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

public class ReviewRequestDTO {

    @Getter
    public static class AddReviewDTO{
        @NotNull(message = "memberId는 필수 항목입니다.")
        Long memberId;
        @NotNull(message = "scoreId는 필수 항목입니다.")
        Long storeId;
        @NotNull(message = "title은 필수 항목입니다.")
        String title;
        @NotNull(message = "score는 필수 항목입니다.")
        Float score;
    }
}
