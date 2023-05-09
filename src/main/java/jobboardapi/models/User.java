package jobboardapi.models;

public class User {
   private Long id;


   private String name;


   private String email;
   private String password;
   private String resume;

   public User() {}

   public User(Long id, String name, String email, String password, String resume) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.resume = resume;
   }
}
