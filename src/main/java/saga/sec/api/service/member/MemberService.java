package saga.sec.api.service.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saga.sec.domain.member.Member;
import saga.sec.domain.member.MemberRepository;
import saga.sec.domain.member.exception.MemberAlreadyExistsException;
import saga.sec.domain.member.exception.MemberNotFoundException;
import saga.sec.domain.role.RoleType;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberRoleService memberRoleService;
	private final PasswordEncoder passwordEncoder;

	public void register(
		final String email,
		final String password,
		final RoleType role
	) {
		if (memberRepository.findByEmail(email).isPresent())
			throw new MemberAlreadyExistsException();

		Member member = Member.builder()
			.email(email)
			.password(passwordEncoder.encode(password))
			.build();

		memberRepository.save(member);
		memberRoleService.assignRole(member, role);
	}

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
	}

	public Member findById(Long userId) {
		return memberRepository.findById(userId).orElseThrow(MemberNotFoundException::new);
	}
}
