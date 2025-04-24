package saga.sec.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import saga.sec.auth.exception.JwtAuthenticationException;
import saga.sec.exception.BusinessException;
import saga.sec.exception.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorCode code = e.getErrorCode();

		ErrorResponse errorResponse = new ErrorResponse(
			code.getCode(),
			code.getMessage()
		);

		return ResponseEntity
			.status(code.getStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(JwtAuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e) {
		return ResponseEntity
			.status(e.getErrorCode().getStatus())
			.body(new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
		return ResponseEntity
			.internalServerError()
			.body(
				new ErrorResponse("INTERNAL_ERROR", "서버 에러")
			);
	}
}
