package saga.sec.auth.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import saga.sec.auth.config.security.token.JwtAuthenticationToken;
import saga.sec.auth.exception.AuthErrorCode;
import saga.sec.auth.exception.JwtAuthenticationException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(
		JwtTokenProvider jwtTokenProvider,
		AuthenticationManager authenticationManager
	) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(
		@Nonnull HttpServletRequest request,
		@Nonnull HttpServletResponse response,
		@Nonnull FilterChain filterChain
	) throws ServletException, IOException, ExpiredJwtException
	{
		String token = jwtTokenProvider.resolveToken(request);
		if (token != null) {
			try {
				Authentication unauthenticated = new JwtAuthenticationToken(token);
				Authentication authenticated = authenticationManager.authenticate(unauthenticated);
				SecurityContextHolder.getContext().setAuthentication(authenticated);
			} catch (JwtAuthenticationException e) {
				throw e;
			} catch (Exception e) {
				//  JWT 에러로 처리
				throw new JwtAuthenticationException(AuthErrorCode.UNAUTHORIZED);
			}
		}
		filterChain.doFilter(request, response);
	}
}