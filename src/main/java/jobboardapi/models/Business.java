package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import net.bytebuddy.utility.nullability.NeverNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Entity
@Table(name = "businesses")
public class Business {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   @NotNull(message = "Business name may not be null")
   private String name;

   @Column
   private String headquarters;

   // many businesses can belong to one user
   @ManyToOne
   @JoinColumn(name = "user_id")
   @JsonIgnore // excludes user details when displaying business details
   private User user;

   // one business can have many job listings
   @OneToMany(mappedBy = "business", orphanRemoval = true) // orphanRemoval removes the job from the database if we deleted it from a business
   @LazyCollection(LazyCollectionOption.FALSE) // all jobs will be eagerly loaded (job data is retrieved together from the database)
   private List<Job> jobList;

   public Business() {}

   public Business(Long id, String name, String headquarters) {
      this.id = id;
      this.name = name;
      this.headquarters = headquarters;
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

   public String getHeadquarters() {
      return headquarters;
   }

   public void setHeadquarters(String headquarters) {
      this.headquarters = headquarters;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public List<Job> getJobList() {
      return jobList;
   }

   public void setJobList(List<Job> jobList) {
      this.jobList = jobList;
   }

   @Override
   public String toString() {
      return "Business{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", headquarters='" + headquarters + '\'' +
              '}';
   }
}
