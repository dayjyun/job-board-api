package jobboardapi.models.login;

public class LoginRequest {

   // these are the two fields required for a user to log in
   private String email;
   private String password;

   // getters
   public String getEmail() {
      return email;
   }

   public String getPassword() {
      return password;
   }
}
