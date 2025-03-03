package umc.spring.domain.store.service;

import org.springframework.data.domain.Page;
import umc.spring.domain.mission.data.Mission;
import umc.spring.domain.review.data.Review;

public interface StoreQueryService {
    Page<Review> getReviewList(Long storeId, Integer page);
    Page<Mission> getMissionList(Long storeId, Integer page);
}
