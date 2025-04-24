package saga.sec.api.controller.auth.dto.response;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {}
