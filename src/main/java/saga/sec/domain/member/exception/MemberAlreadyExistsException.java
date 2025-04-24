package saga.sec.domain.member.exception;

import saga.sec.exception.BusinessException;

public class MemberAlreadyExistsException extends BusinessException {
	public MemberAlreadyExistsException() {
		super(MemberErrorCode.MEMBER_ALREADY_EXISTS);
	}
}