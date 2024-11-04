package exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiException extends Exception{

    private final int statusCode;
    private final LocalDateTime timestamp;

    public ApiException(int statusCode, String message, LocalDateTime timestamp) {
        super(message);
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
