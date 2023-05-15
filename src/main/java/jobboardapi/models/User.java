package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users") // name of table in H2 database
public class User {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) // this is the primary key
   private Long id;

   @Column
   private String name;

   @Column(unique = true) // each user's email is unique
   private String email;

   @Column
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // users can send password data to server, but it is not sent back for the user to view in the JSON object
   private String password;

   @Column
   private String resume;

   // one user can have many businesses
   @OneToMany(mappedBy = "user", orphanRemoval = true) // orphanRemoval removes the business from database if we deleted it from a user
   @LazyCollection(LazyCollectionOption.FALSE) // all businesses will be eagerly loaded (business data is retrieved together with user from the database)
   @JsonIgnore // excludes data from JSON object viewed by client
   private List<Business> businessList;

   // many users can a list of many jobs they applied to
   @ManyToMany(mappedBy = "applicantsList")
   @JsonIgnore // excludes data from JSON object viewed by client
   private List<Job> listOfJobsAppliedTo;

   // no-args constructor
   public User() {}

   // parameterized constructor
   public User(Long id, String name, String email, String password, String resume) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.resume = resume;
   }

   // getters and setters
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

   public List<Business> getBusinessList() {
      return businessList;
   }

   public void setBusinessList(List<Business> businessList) {
      this.businessList = businessList;
   }

   public List<Job> getListOfJobsAppliedTo() {
      return listOfJobsAppliedTo;
   }

   public void setListOfJobsAppliedTo(List<Job> listOfJobsAppliedTo) {
      this.listOfJobsAppliedTo = listOfJobsAppliedTo;
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
