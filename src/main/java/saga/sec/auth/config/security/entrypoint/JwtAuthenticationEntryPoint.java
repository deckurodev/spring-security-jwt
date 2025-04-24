package saga.sec.auth.config.security.entrypoint;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import saga.sec.api.ErrorResponse;
import saga.sec.auth.exception.AuthErrorCode;
import saga.sec.auth.exception.JwtAuthenticationException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException {
		AuthErrorCode errorCode = AuthErrorCode.UNAUTHORIZED;

		if (authException instanceof JwtAuthenticationException jwtEx) {
			errorCode = jwtEx.getErrorCode();
		}

		ErrorResponse errorResponse = new ErrorResponse(
			errorCode.getCode(),
			errorCode.getMessage()
		);

		response.setStatus(errorCode.getStatus());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}