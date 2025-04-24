package saga.sec.auth.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import saga.sec.auth.exception.InvalidJwtException;
import saga.sec.domain.role.RoleType;

@Component(value = "jwtTokenProvider")
public class JwtTokenProvider {
	private static final long ACCESS_TOKEN_VALIDITY_IN_MILLIS = 1000L * 60 * 30;
	private static final long REFRESH_TOKEN_VALIDITY_IN_MILLIS = 1000L * 60 * 60 * 24 * 7;
	private static final String ISSUER = "secutoken.io";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String HEADER_AUTHORIZATION = "Authorization";

	private final Key key;

	public JwtTokenProvider(
		@Value("${jwt.secret}") String secret
	) {
		if (secret.length() < 32) {
			throw new IllegalArgumentException("JWT 시크릿 키는 32글자 이상이어야 합니다.");
		}

		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createAccessToken(Long userId, List<RoleType> roles) {
		return createToken(
			userId.toString(),
			roles,
			ACCESS_TOKEN_VALIDITY_IN_MILLIS
		);
	}

	public String createRefreshToken(Long userId) {
		return createToken(userId.toString(), null, REFRESH_TOKEN_VALIDITY_IN_MILLIS);
	}

	public String createToken(String userId, List<RoleType> roles, long validity) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + validity);

		return Jwts.builder()
			.setSubject(userId)
			.setIssuer(ISSUER)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.claim("roles", roles != null ? roles.stream().map(RoleType::name).toList() : Collections.EMPTY_LIST)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (Exception e) {
			throw new InvalidJwtException();
		}
	}

	public String getSubjectFromToken(String token) {
		return parseClaims(token).getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader(HEADER_AUTHORIZATION);
		if (bearer != null && bearer.startsWith(TOKEN_PREFIX)) {
			return bearer.substring(TOKEN_PREFIX.length());
		}
		return null;
	}
}
