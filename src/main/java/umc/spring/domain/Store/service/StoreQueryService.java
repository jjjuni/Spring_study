package umc.spring.domain.Store.service;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission.data.Mission;
import umc.spring.domain.Review.data.Review;

public interface StoreQueryService {
    Page<Review> getReviewList(Long storeId, Integer page);
    Page<Mission> getMissionList(Long storeId, Integer page);
}
