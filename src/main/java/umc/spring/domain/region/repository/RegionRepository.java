package umc.spring.domain.region.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.region.data.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
