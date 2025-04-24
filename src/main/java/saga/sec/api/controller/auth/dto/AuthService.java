package saga.sec.api.controller.auth.dto;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saga.sec.api.controller.auth.dto.response.AuthTokenResponse;
import saga.sec.api.service.member.MemberRoleService;
import saga.sec.api.service.member.MemberService;
import saga.sec.auth.config.jwt.JwtTokenProvider;
import saga.sec.auth.exception.AuthenticationFailedException;
import saga.sec.auth.exception.InvalidJwtException;
import saga.sec.domain.member.Member;
import saga.sec.domain.role.Role;
import saga.sec.domain.role.RoleType;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final MemberService memberService;
	private final MemberRoleService memberRoleService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public AuthTokenResponse authenticate(String email, String rawPassword) {
		Member member = memberService.findByEmail(email);

		if (!passwordIsMatched(rawPassword, member.getPassword())) {
			throw new AuthenticationFailedException(email);
		}

		List<RoleType> roles = memberRoleService.findRoleByMember(member).stream()
			.map(Role::getName)
			.toList();

		String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId(), roles);
		String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());

		return new AuthTokenResponse(accessToken, refreshToken);
	}

	public boolean passwordIsMatched(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public String reIssueAccessToken(String refreshToken) {
		if (!jwtTokenProvider.isValidToken(refreshToken)) {
			throw new InvalidJwtException();
		}

		String userId = jwtTokenProvider.getSubjectFromToken(refreshToken);
		Member member = memberService.findById(Long.parseLong(userId));
		List<Role> roleList = memberRoleService.findRoleByMember(member);

		return jwtTokenProvider.createAccessToken(
			member.getMemberId(),
			roleList.stream().map(Role::getName).toList()
		);
	}
}
