package umc.spring.repository.MemberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
}
