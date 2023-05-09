package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.sql.Timestamp;

public class Job {
   private Long id;
   private String title;
   private String description;
   private String location;
   private double salary;
   private boolean applied;

   @Column
   @CreationTimestamp
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH-mm-ss")
   private Timestamp createdAt;

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
}
