package saga.sec.auth.exception;

import saga.sec.domain.role.RoleType;

public class AuthorizationDeniedException extends SecurityException {
	public AuthorizationDeniedException(RoleType roleType) {
		super("해당 권한 없음 : " + roleType.name());
	}

	public AuthorizationDeniedException(String message) {
		super(message);
	}
}