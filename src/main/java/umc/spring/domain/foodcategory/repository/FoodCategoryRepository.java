package umc.spring.domain.foodcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.foodcategory.data.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
