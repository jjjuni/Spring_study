// create로 실행 시 초기 데이터 삽입

package umc.spring.init;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Region;
import umc.spring.repository.FoodCategoryRepository;
import umc.spring.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    public final RegionRepository regionRepository;

    public final FoodCategoryRepository foodCategoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            Region region1 = Region.builder()
                    .name("한국")
                    .createdAt(LocalDateTime.now())
                    .build();

            Region region2 = Region.builder()
                    .name("미국")
                    .createdAt(LocalDateTime.now())
                    .build();

            Region region3 = Region.builder()
                    .name("일본")
                    .createdAt(LocalDateTime.now())
                    .build();

            Region region4 = Region.builder()
                    .name("중국")
                    .createdAt(LocalDateTime.now())
                    .build();

            regionRepository.saveAll(Arrays.asList(region1, region2, region3, region4));
        }

        if (foodCategoryRepository.count() == 0) {
            FoodCategory food1 = FoodCategory.builder()
                    .name("한식")
                    .createdAt(LocalDateTime.now())
                    .build();

            FoodCategory food2 = FoodCategory.builder()
                    .name("양식")
                    .createdAt(LocalDateTime.now())
                    .build();

            FoodCategory food3 = FoodCategory.builder()
                    .name("일식")
                    .createdAt(LocalDateTime.now())
                    .build();

            FoodCategory food4 = FoodCategory.builder()
                    .name("중식")
                    .createdAt(LocalDateTime.now())
                    .build();

            foodCategoryRepository.saveAll(Arrays.asList(food1, food2, food3, food4));
        }
    }
}
