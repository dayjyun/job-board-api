package jobboardapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The BadRequestException class is used to throw an exception
 * when the user is submitting a request that does not adhere to the method constraints
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

   /**
    *The BadRequestException method instantiates the BadRequestException class with a custom exception error message
    * @param message is the custom exception error message the user sees
    */
   public BadRequestException(String message) {
      super(message);
   }
}
