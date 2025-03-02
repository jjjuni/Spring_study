package umc.spring.domain.Review.service;

import umc.spring.domain.Review.data.Review;
import umc.spring.domain.Review.web.dto.ReviewRequestDTO;

public interface ReviewCommandService {
    public Review addReview(ReviewRequestDTO.AddReviewDTO request);
}
