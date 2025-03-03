package umc.spring.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.mission.data.mapping.UserMission;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
}
