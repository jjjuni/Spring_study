package umc.spring.domain.review.converter;

import umc.spring.domain.review.data.Review;
import umc.spring.domain.review.web.dto.ReviewRequestDTO;
import umc.spring.domain.review.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;

public class ReviewConverter {

    public static ReviewResponseDTO.AddReviewResultDTO toAddReviewResultDTO(Review review) {
        return ReviewResponseDTO.AddReviewResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.AddReviewDTO request){

        return Review.builder()
                .comment(request.getComment())
                .score(request.getScore())
                .build();
    }
}
