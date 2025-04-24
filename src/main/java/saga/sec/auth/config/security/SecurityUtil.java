package saga.sec.auth.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import saga.sec.auth.exception.UnauthenticatedAccessException;

public class SecurityUtil {
	public static Long currentUserId() {
		SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();
		Authentication authentication = context.getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UnauthenticatedAccessException();
		}

		return Long.parseLong(authentication.getName());
	}
}
