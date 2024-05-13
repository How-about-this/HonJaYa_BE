package goorm.honjaya.domain.member.repository;

import goorm.honjaya.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
