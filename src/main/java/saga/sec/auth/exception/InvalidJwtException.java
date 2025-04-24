package saga.sec.auth.exception;

public class InvalidJwtException extends JwtAuthenticationException {
	public InvalidJwtException() {
		super(AuthErrorCode.INVALID_TOKEN);
	}
}
