package umc.spring.domain.Mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Mission.data.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
}
