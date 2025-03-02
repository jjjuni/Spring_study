package umc.spring.domain.Region.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Region.data.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
