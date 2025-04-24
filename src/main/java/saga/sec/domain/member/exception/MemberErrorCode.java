package saga.sec.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import saga.sec.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
	MEMBER_ALREADY_EXISTS(400, "ALREADY_EXISTS", "이미 존재하는 회원입니다."),
	MEMBER_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 회원입니다."),
	;

	private final int status;
	private final String code;
	private final String message;
}