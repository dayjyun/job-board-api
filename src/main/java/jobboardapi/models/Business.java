package jobboardapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "businesses")
public class Business {
   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private String name;

   @Column
   private String headquarters;

   // many businesses can belong to one user
   @ManyToOne
   @JoinColumn(name = "user_id")
   @JsonIgnore // excludes user details when displaying business details
   private User user;

   // one business can have many job listings
   @OneToMany(mappedBy = "job", orphanRemoval = true)
   @LazyCollection(LazyCollectionOption.FALSE)
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

   @Override
   public String toString() {
      return "Business{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", headquarters='" + headquarters + '\'' +
              '}';
   }
}
