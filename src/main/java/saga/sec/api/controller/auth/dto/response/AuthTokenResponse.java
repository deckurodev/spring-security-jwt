package saga.sec.api.controller.auth.dto.response;

public record AuthTokenResponse(
	String accessToken,
	String refreshToken
) {}