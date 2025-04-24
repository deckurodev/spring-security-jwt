package saga.sec.auth.exception;

import saga.sec.domain.role.RoleType;

public class RoleNotFoundException extends AuthorizationDeniedException {
	public RoleNotFoundException(RoleType roleType) {
		super("역할 없음: " + roleType.name());
	}
}
