package saga.sec.auth.exception;

public class AuthenticationFailedException extends SecurityException {
	public AuthenticationFailedException(String reason) {
		super("인증 실패: " + reason);
	}
}
