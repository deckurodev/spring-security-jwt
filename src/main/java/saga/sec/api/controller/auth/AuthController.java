package saga.sec.api.controller.auth;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import saga.sec.api.controller.auth.dto.AuthService;
import saga.sec.api.controller.auth.dto.request.LoginRequest;
import saga.sec.api.controller.auth.dto.response.AccessTokenResponse;
import saga.sec.api.controller.auth.dto.response.AuthTokenResponse;
import saga.sec.api.controller.auth.dto.response.LoginResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/authenticate")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		AuthTokenResponse login = authService.authenticate(request.email(), request.password());
		return ResponseEntity.ok(new LoginResponse(login.accessToken(), login.refreshToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AccessTokenResponse> refresh(HttpServletRequest request) {
		String refreshToken = extractRefreshTokenFromCookie(request);
		String newAccessToken = authService.reIssueAccessToken(refreshToken);
		return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
	}

	private String extractRefreshTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null || cookies.length == 0) {
			throw new RuntimeException("요청에 쿠키가 없습니다.");
		}

		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals("refreshToken"))
			.findFirst()
			.map(Cookie::getValue)
			.orElseThrow(() -> new RuntimeException("리프레시 토큰 쿠키가 없습니다."));
	}
}
