package umc.spring.domain.review.service;

import umc.spring.domain.review.data.Review;
import umc.spring.domain.review.web.dto.ReviewRequestDTO;
import umc.spring.domain.user.data.User;

public interface ReviewCommandService {
    public Review addReview(ReviewRequestDTO.AddReviewDTO request, User user);
}
