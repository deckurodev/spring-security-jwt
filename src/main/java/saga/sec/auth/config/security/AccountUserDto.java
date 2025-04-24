package saga.sec.auth.config.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public record AccountUserDto(
	String userId,
	String email,
	List<GrantedAuthority> authorities
) {}