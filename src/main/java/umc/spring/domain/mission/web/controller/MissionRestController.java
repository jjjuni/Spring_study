package umc.spring.domain.mission.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.config.security.OAuth2.UserPrincipal;
import umc.spring.domain.mission.converter.UserMissionConverter;
import umc.spring.domain.mission.converter.MissionConverter;
import umc.spring.domain.mission.data.Mission;
import umc.spring.domain.mission.data.mapping.UserMission;
import umc.spring.domain.mission.service.MissionCommandService;
import umc.spring.domain.mission.web.dto.MissionRequestDTO;
import umc.spring.domain.mission.web.dto.MissionResponseDTO;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.service.UserCommandServiceImpl;

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
    public ApiResponse<MissionResponseDTO.ChallengeMissionResultDTO> join(@RequestBody @Valid MissionRequestDTO.ChallengeMissionDTO challengeMissionRequest, HttpServletRequest request, @AuthenticationPrincipal UserPrincipal userDetails){
        String email = userDetails.getUsername();

        UserMission userMission = missionCommandService.challengeMission(challengeMissionRequest, email);
        return ApiResponse.onSuccess(UserMissionConverter.toChallengeMissionResultDTO(userMission));
    }
}
