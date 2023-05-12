package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   @NotNull(message = "User name may not be null")
   private String name;

   @Column(unique = true)
   @NotNull(message = "User email may not be null")
   private String email;

   @Column
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private String password;

   @Column
   private String resume;

   // one user can have many businesses
   @OneToMany(mappedBy = "user", orphanRemoval = true) // orphanRemoval removes the business from database if we deleted it from a user
   @LazyCollection(LazyCollectionOption.FALSE) // all businesses will be eagerly loaded (business data is retrieved together from the database)
   @JsonIgnore
   private List<Business> businessList;

   // one user can apply to jobs
   // Current logged-in user can see the list of jobs they applied for
   @OneToMany(mappedBy = "user", orphanRemoval = true) // orphanRemoval removes the job from database if we deleted it from a user
   @LazyCollection(LazyCollectionOption.FALSE) // all jobs will be eagerly loaded (job data is retrieved together from the database)
   private List<Job> appliedJobs;

   // many users (applicants) applied to one job
   @ManyToOne
   @JoinColumn(name = "job_id")
   @JsonIgnore
   private Job job;

   public User() {}

   public User(Long id, String name, String email, String password, String resume) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.resume = resume;
//      this.appliedJobs = new ArrayList<>();
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

   public List<Business> getBusinessList() {
      return businessList;
   }

   public void setBusinessList(List<Business> businessList) {
      this.businessList = businessList;
   }

   public List<Job> getJobList() {
      return appliedJobs;
   }

   public void setJobList(List<Job> appliedJobs) {
      this.appliedJobs = appliedJobs;
   }

   public Job getJob() {
      return job;
   }

   public void setJob(Job job) {
      this.job = job;
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
