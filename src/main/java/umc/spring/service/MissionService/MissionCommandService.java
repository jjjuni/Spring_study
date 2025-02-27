package umc.spring.service.MissionService;

import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MissionDTO.MissionRequestDTO;

public interface MissionCommandService {
    public Mission addMission(MissionRequestDTO.AddMissionDTO request);

    public MemberMission challengeMission(MissionRequestDTO.ChallengeMissionDTO request);
}
