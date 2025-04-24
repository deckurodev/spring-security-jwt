package saga.sec.auth.config.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken  extends AbstractAuthenticationToken {
	private final UserDetails principal;
	private String token;

	// 인증 전
	public JwtAuthenticationToken(String token) {
		super(null);
		this.token = token;
		this.principal = null;
		setAuthenticated(false);
	}

	// 인증 후
	public JwtAuthenticationToken(UserDetails principal, String token,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.token = token;
		setAuthenticated(true); // 인증 완료 상태로 설정
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
