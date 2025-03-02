package umc.spring.domain.FoodCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.FoodCategory.data.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
