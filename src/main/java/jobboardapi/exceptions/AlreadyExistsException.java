package jobboardapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The AlreadyExistsException class is used to throw an exception
 * when an object already exists in the referenced data
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException{

    /**
     *The AlreadyExistsException method instantiates the AlreadyExistsException class with a custom exception error message
     * @param message is the custom exception error message the user sees
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}
