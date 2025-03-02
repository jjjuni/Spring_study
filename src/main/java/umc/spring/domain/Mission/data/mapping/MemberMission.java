package umc.spring.domain.Mission.data.mapping;

import jakarta.persistence.*;
import lombok.*;
import umc.spring.domain.Member.data.Member;
import umc.spring.domain.Mission.data.Mission;
import umc.spring.global.data.BaseEntity;
import umc.spring.domain.Mission.data.Mission.data.enums.MissionStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
}
