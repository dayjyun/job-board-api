package jobboardapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The NotFoundException class is used to throw an exception
 * when the object does not exist in the referenced data
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    /**
     *The NotFoundException method instantiates the NotFoundException class with a custom exception error message
     * @param message is the custom exception error message the user sees
     */
    public NotFoundException(String message){
        super(message);
    }
}
