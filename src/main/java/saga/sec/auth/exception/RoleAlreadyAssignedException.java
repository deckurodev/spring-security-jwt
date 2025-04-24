package saga.sec.auth.exception;

import saga.sec.domain.role.RoleType;

public class RoleAlreadyAssignedException extends AuthorizationDeniedException{
	public RoleAlreadyAssignedException(RoleType roleType) {
		super("이미 해당 권한이 부여되어 있습니다: " + roleType.name());
	}
}
