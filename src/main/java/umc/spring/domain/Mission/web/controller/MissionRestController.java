package umc.spring.domain.Mission.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.Mission.converter.MemberMissionConverter;
import umc.spring.domain.Mission.converter.MissionConverter;
import umc.spring.domain.Mission.data.Mission;
import umc.spring.domain.Mission.data.mapping.MemberMission;
import umc.spring.domain.Mission.service.MissionCommandService;
import umc.spring.domain.Mission.web.dto.MissionRequestDTO;
import umc.spring.domain.Mission.web.dto.MissionResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionRestController {

    private final MissionCommandService missionCommandService;

    @PostMapping("")
    public ApiResponse<MissionResponseDTO.AddMissionResultDTO> join(@RequestBody @Valid MissionRequestDTO.AddMissionDTO request){
        Mission mission = missionCommandService.addMission(request);
        return ApiResponse.onSuccess(MissionConverter.toAddMissionResultDTO(mission));
    }

    @PostMapping("/challenge")
    public ApiResponse<MissionResponseDTO.ChallengeMissionResultDTO> join(@RequestBody @Valid MissionRequestDTO.ChallengeMissionDTO request){
        MemberMission memberMission = missionCommandService.challengeMission(request);
        return ApiResponse.onSuccess(MemberMissionConverter.toChallengeMissionResultDTO(memberMission));
    }
}
