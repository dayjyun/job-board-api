package jobboardapi.models.login;

public class LoginResponse {

   // the login response consists of a message
   private String message;

   /**
    * the LoginResponse method returns a message to the user
    * @param message is what the user sees after logging in
    */
   public LoginResponse(String message) {
      this.message = message;
   }

   // getter and setter
   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
