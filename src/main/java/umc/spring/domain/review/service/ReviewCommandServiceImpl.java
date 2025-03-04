package umc.spring.domain.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.domain.review.converter.ReviewConverter;
import umc.spring.domain.user.data.User;
import umc.spring.domain.review.data.Review;
import umc.spring.domain.store.data.Store;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.review.repository.ReviewRepository;
import umc.spring.domain.store.repository.StoreRepository;
import umc.spring.domain.review.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Review addReview(ReviewRequestDTO.AddReviewDTO request) {

        Review newReview = ReviewConverter.toReview(request);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ErrorException(ErrorStatus.STORE_NOT_FOUND));

        newReview.setUser(user);
        newReview.setStore(store);

        return reviewRepository.save(newReview);
    }
}
