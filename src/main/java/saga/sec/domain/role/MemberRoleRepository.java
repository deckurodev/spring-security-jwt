package saga.sec.domain.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saga.sec.domain.member.Member;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
	boolean existsByMemberAndRole(Member member, Role role);

	Optional<MemberRole> findByMemberAndRole(Member member, Role role);

	@Query("SELECT mr FROM MemberRole mr JOIN FETCH mr.role WHERE mr.member = :member")
	List<MemberRole> findByMemberWithRole(@Param("member") Member member);
}
