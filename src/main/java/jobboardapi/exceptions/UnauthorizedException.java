package jobboardapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The UnauthorizedException class is used to throw an exception
 * when the user is unauthorized and performs a request or tries to access data
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

   /**
    *The UnauthorizedException method instantiates the UnauthorizedException class with a custom exception error message
    * @param message is the custom exception error message the user sees
    */
   public UnauthorizedException(String message) {
      super(message);
   }
}
