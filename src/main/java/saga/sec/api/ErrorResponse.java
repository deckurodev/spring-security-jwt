package saga.sec.api;

public record ErrorResponse(
	String code,
	String message
) {}