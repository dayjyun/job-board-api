package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobs") // name of table in H2 database
public class Job {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private String title;

   @Column
   private String description;

   @Column
   private String location;

   @Column
   private Double salary;

   // many job listings can belong to one business
   @ManyToOne
   @JoinColumn(name = "business_id")
   @JsonIgnore // excludes business details when displaying job details
   private Business business;

   @ManyToMany
   @JoinTable(name = "user_jobs",
           joinColumns = @JoinColumn(name = "job_id"),
           inverseJoinColumns = @JoinColumn(name = "user_id"))
   @JsonIgnore
   private List<User> applicantsList;

   public Job() {
   }

   public Job(Long id, String title, String description, String location, Double salary) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.location = location;
      this.salary = salary;
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

   public Double getSalary() {
      return salary;
   }

   public void setSalary(Double salary) {
      this.salary = salary;
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
              '}';
   }
}
