package umc.spring.domain.mission.data.mapping;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.spring.domain.user.data.User;
import umc.spring.domain.mission.data.Mission;
import umc.spring.global.data.BaseEntity;
import umc.spring.domain.mission.data.enums.MissionStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MissionStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    @NotNull
    private Mission mission;
}
