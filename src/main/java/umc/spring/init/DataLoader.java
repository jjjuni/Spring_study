// hibernate create로 실행 시 초기 데이터 삽입

package umc.spring.init;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.domain.mapping.MemberPrefer;
import umc.spring.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.RegionRepository.RegionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    public final RegionRepository regionRepository;

    public final StoreRepository storeRepository;

    public final FoodCategoryRepository foodCategoryRepository;

    public final MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            Region region1 = Region.builder()
                    .name("한국")
                    .build();

            Region region2 = Region.builder()
                    .name("미국")
                    .build();

            Region region3 = Region.builder()
                    .name("일본")
                    .build();

            Region region4 = Region.builder()
                    .name("중국")
                    .build();

            regionRepository.saveAll(Arrays.asList(region1, region2, region3, region4));

            Store store1 = Store.builder()
                    .name("가게1")
                    .address("주소1")
                    .region(region1)
                    .build();

            Store store2 = Store.builder()
                    .name("가게2")
                    .address("주소2")
                    .region(region2)
                    .build();

            storeRepository.saveAll(Arrays.asList(store1, store2));
        }

        if (foodCategoryRepository.count() == 0) {
            FoodCategory food1 = FoodCategory.builder()
                    .name("한식")
                    .build();

            FoodCategory food2 = FoodCategory.builder()
                    .name("양식")
                    .build();

            FoodCategory food3 = FoodCategory.builder()
                    .name("일식")
                    .build();

            FoodCategory food4 = FoodCategory.builder()
                    .name("중식")
                    .build();

            foodCategoryRepository.saveAll(Arrays.asList(food1, food2, food3, food4));

            Member member1 = Member.builder()
                    .name("이름1")
                    .address("주소1")
                    .specAddress("상세주소1")
                    .build();

            Member member2 = Member.builder()
                    .name("이름2")
                    .address("주소2")
                    .specAddress("상세주소2")
                    .build();

            MemberPrefer prefer1 = MemberPrefer.builder()
                    .foodCategory(food1)
                    .member(member1)
                    .build();

            MemberPrefer prefer2 = MemberPrefer.builder()
                    .foodCategory(food1)
                    .member(member2)
                    .build();

            member1.getMemberPreferList().add(prefer1);
            member1.getMemberPreferList().add(prefer2);

            memberRepository.saveAll(Arrays.asList(member1, member2));
        }
    }
}
