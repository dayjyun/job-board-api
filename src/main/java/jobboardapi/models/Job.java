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
}
