package saga.sec.auth.config.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import saga.sec.auth.config.security.AccountUserDto;
import saga.sec.auth.config.security.token.JwtTokenUserDetails;
import saga.sec.domain.member.Member;
import saga.sec.domain.member.MemberRepository;
import saga.sec.domain.role.MemberRole;
import saga.sec.domain.role.MemberRoleRepository;
import saga.sec.domain.role.Role;

@Component
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final MemberRoleRepository memberRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findById(Long.parseLong(username)).orElseThrow();

		List<GrantedAuthority> roles = memberRoleRepository.findByMemberWithRole(member)
			.stream()
			.map(MemberRole::getRole)
			.map(Role::getName) // getName() → RoleType enum
			.map(Enum::name)
			.map(roleName -> "ROLE_" + roleName)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toUnmodifiableList());

		AccountUserDto accountUserDto = new AccountUserDto(
			member.getMemberId().toString(),
			member.getEmail(),
			roles
		);

		return new JwtTokenUserDetails(accountUserDto);
	}
}
