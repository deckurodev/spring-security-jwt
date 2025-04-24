package saga.sec.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import saga.sec.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
	INVALID_CREDENTIALS(401, "INVALID", "이메일 또는 비밀번호가 일치하지 않습니다."),
	REFRESH_TOKEN_NOT_FOUND(401, "NOT_FOUND", "리프레시 토큰이 존재하지 않습니다."),
	TOKEN_EXPIRED(401, "EXPIRED", "토큰이 만료되었습니다."),
	INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
	PROVIDER_MISMATCH(400, "PROVIDER_MISMATCH", "로그인 제공자 정보가 일치하지 않습니다."),
	UNAUTHORIZED(401, "UNAUTHORIZED", "인증되지 않은 요청입니다."),
	FORBIDDEN(403, "FORBIDDEN", "접근 권한이 없습니다.");

	;

	private final int status;
	private final String code;
	private final String message;
}