package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   @NotNull
   private String name;

   @Column(unique = true)
   @NotNull
   private String email;

   @Column
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private String password;

   @Column
   private String resume;

   // one user can have many businesses
   @OneToMany(mappedBy = "user", orphanRemoval = true) // orphanRemoval removes the business from database if we deleted it from a user
   @LazyCollection(LazyCollectionOption.FALSE) // all businesses will be eagerly loaded (business data is retrieved together from the database)
   private List<Business> businessList;

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
