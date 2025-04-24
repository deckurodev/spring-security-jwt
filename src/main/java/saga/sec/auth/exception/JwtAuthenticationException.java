package saga.sec.auth.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
	private final AuthErrorCode errorCode;

	public JwtAuthenticationException(AuthErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
