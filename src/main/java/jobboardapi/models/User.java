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

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getResume() {
      return resume;
   }

   public void setResume(String resume) {
      this.resume = resume;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              ", resume='" + resume + '\'' +
              '}';
   }
}
