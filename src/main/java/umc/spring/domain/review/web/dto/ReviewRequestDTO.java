package umc.spring.domain.review.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class AddReviewDTO{
        @NotNull(message = "storeId는 필수 항목입니다.")
        Long storeId;
        @NotNull(message = "comment은 필수 항목입니다.")
        String comment;
        @NotNull(message = "score는 필수 항목입니다.")
        Float score;
    }


}
