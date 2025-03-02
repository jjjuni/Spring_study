package umc.spring.domain.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Member.data.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
