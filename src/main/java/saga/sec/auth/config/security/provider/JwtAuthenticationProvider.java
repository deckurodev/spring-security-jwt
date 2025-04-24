package saga.sec.auth.config.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import saga.sec.auth.config.jwt.JwtTokenProvider;
import saga.sec.auth.config.security.service.JwtUserDetailsService;
import saga.sec.auth.config.security.token.JwtAuthenticationToken;
import saga.sec.auth.exception.InvalidJwtException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();

		if (!jwtTokenProvider.isValidToken(token))
			throw new InvalidJwtException();

		String userId = jwtTokenProvider.getSubjectFromToken(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
		return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
