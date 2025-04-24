package saga.sec.api.service.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saga.sec.auth.exception.AuthorizationDeniedException;
import saga.sec.auth.exception.RoleAlreadyAssignedException;
import saga.sec.auth.exception.RoleNotFoundException;
import saga.sec.domain.member.Member;
import saga.sec.domain.role.MemberRole;
import saga.sec.domain.role.MemberRoleRepository;
import saga.sec.domain.role.Role;
import saga.sec.domain.role.RoleRepository;
import saga.sec.domain.role.RoleType;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberRoleService {
	private final RoleRepository roleRepository;
	private final MemberRoleRepository memberRoleRepository;

	public void assignRole(Member member, RoleType roleType) {
		Role role = roleRepository.findByName(roleType)
			.orElseThrow(() -> new RoleNotFoundException(roleType));

		boolean exists = memberRoleRepository.existsByMemberAndRole(member, role);
		if (exists) {
			throw new RoleAlreadyAssignedException(roleType);
		}

		MemberRole memberRole = new MemberRole(member, role);
		memberRoleRepository.save(memberRole);
	}

	public void removeRole(Member member, RoleType roleType) {
		Role role = roleRepository.findByName(roleType)
			.orElseThrow(() -> new RoleNotFoundException(roleType));

		MemberRole memberRole = memberRoleRepository.findByMemberAndRole(member, role)
			.orElseThrow(() -> new AuthorizationDeniedException(roleType));

		memberRoleRepository.delete(memberRole);
	}

	public List<Role> findRoleByMember(Member member) {
		return memberRoleRepository.findByMemberWithRole(member)
			.stream()
			.map(MemberRole::getRole)
			.toList();
	}
}
