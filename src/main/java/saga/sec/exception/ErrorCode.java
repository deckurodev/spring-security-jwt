package saga.sec.exception;

public interface ErrorCode {
	String getCode();
	String getMessage();
	int getStatus();
}