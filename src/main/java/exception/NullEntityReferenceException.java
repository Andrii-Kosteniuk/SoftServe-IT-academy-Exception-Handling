package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NullEntityReferenceException extends RuntimeException{
    public NullEntityReferenceException(String message) {
        super(message);
    }

}
