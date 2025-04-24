package saga.sec.domain.member.exception;

import saga.sec.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
	public MemberNotFoundException() {
		super(MemberErrorCode.MEMBER_NOT_FOUND);
	}
}
