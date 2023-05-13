package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "jobs")
public class Job {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   @NotNull(message = "Job title may not be null")
   private String title;

   @Column
   private String description;

   @Column
   @NotNull(message = "Job location may not be null")
   private String location;

   @Column
   private double salary;

//   @Column
//   private boolean applied;

   // many job applications can belong to one user
   // Current logged-in user can see the list of jobs they applied for
   @ManyToOne
   @JoinColumn(name = "user_id")
   @JsonIgnore // excludes user details when displaying job details
   private User user;

   // many job listings can belong to one business
   @ManyToOne
   @JoinColumn(name = "business_id")
   @JsonIgnore // excludes business details when displaying job details
   private Business business;

   // Job should have a list of users who applied to the job listing
   // one job can have many applicants (users)
   @OneToMany(mappedBy = "job", orphanRemoval = true)
   @LazyCollection(LazyCollectionOption.FALSE)
   @JsonIgnore
   private List<User> applicantsList;

   public Job() {}

   public Job(Long id, String title, String description, String location, double salary) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.location = location;
      this.salary = salary;
//      this.applied = applied;
//      this.applicantsList = new ArrayList<>();
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public double getSalary() {
      return salary;
   }

   public void setSalary(double salary) {
      this.salary = salary;
   }

//   public boolean isApplied() {
//      return applied;
//   }
//
//   public void setApplied(boolean applied) {
//      this.applied = applied;
//   }


   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Business getBusiness() {
      return business;
   }

   public void setBusiness(Business business) {
      this.business = business;
   }

   public List<User> getApplicantsList() {
      return applicantsList;
   }

   public void setApplicantsList(List<User> applicantsList) {
      this.applicantsList = applicantsList;
   }

   @Override
   public String toString() {
      return "Job{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", description='" + description + '\'' +
              ", location='" + location + '\'' +
              ", salary=" + salary +
//              ", applied=" + applied +
              '}';
   }
}
