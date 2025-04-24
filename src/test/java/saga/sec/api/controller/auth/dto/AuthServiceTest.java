package saga.sec.api.controller.auth.dto;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import saga.sec.api.controller.auth.dto.response.AuthTokenResponse;
import saga.sec.domain.member.Member;
import saga.sec.domain.member.MemberRepository;

@SpringBootTest
@Transactional
class AuthServiceTest {
	@Autowired
	private AuthService authService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String EMAIL = "test@example.com";
	private static final String PASSWORD = "1234";

	@BeforeEach
	void setUp() {
		Member member = Member.builder()
			.email(EMAIL)
			.password(passwordEncoder.encode(PASSWORD))
			.build();
		memberRepository.save(member);
	}

	@Test
	void 로그인_성공시_토큰반환() {
		AuthTokenResponse response = authService.authenticate(EMAIL, PASSWORD);
		assertThat(response.accessToken()).isNotBlank();
		assertThat(response.refreshToken()).isNotBlank();
	}

	@Test
	void 존재하지_않는_회원_로그인시_예외() {
		assertThrows(RuntimeException.class, () -> {
			authService.authenticate("notfound@example.com", PASSWORD);
		});
	}

	@Test
	void 비밀번호_불일치시_예외() {
		assertThrows(RuntimeException.class, () -> {
			authService.authenticate(EMAIL, "wrongPassword");
		});
	}
}