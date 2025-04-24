package saga.sec.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UnauthenticatedAccessException extends AuthenticationException {
	private final AuthErrorCode errorCode;

	public UnauthenticatedAccessException() {
		super(AuthErrorCode.UNAUTHORIZED.getMessage());
		this.errorCode = AuthErrorCode.UNAUTHORIZED;
	}

	public AuthErrorCode getErrorCode() {
		return errorCode;
	}
}