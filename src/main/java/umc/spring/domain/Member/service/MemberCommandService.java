package umc.spring.domain.Member.service;

import umc.spring.domain.Member.data.Member;
import umc.spring.domain.Member.web.dto.MemberRequestDTO;

public interface MemberCommandService {
    public Member joinMember(MemberRequestDTO.JoinDTO request);
}
