package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "jobs")
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
   private double salary;

   @Column
   private boolean applied;

   @Column
   @CreationTimestamp
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH-mm-ss")
   private Timestamp createdAt;

   // many job applications can belong to one user
   @ManyToOne
   @JoinColumn(name = "user_id")
   @JsonIgnore // excludes user details when displaying job details
   private User user;

   public Job() {}

   public Job(Long id, String title, String description, String location, double salary, boolean applied, Timestamp createdAt) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.location = location;
      this.salary = salary;
      this.applied = applied;
      this.createdAt = createdAt;
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

   public boolean isApplied() {
      return applied;
   }

   public void setApplied(boolean applied) {
      this.applied = applied;
   }

   public Timestamp getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Timestamp createdAt) {
      this.createdAt = createdAt;
   }

   @Override
   public String toString() {
      return "Job{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", description='" + description + '\'' +
              ", location='" + location + '\'' +
              ", salary=" + salary +
              ", applied=" + applied +
              ", createdAt=" + createdAt +
              '}';
   }
}
