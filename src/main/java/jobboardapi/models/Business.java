package jobboardapi.models;

public class Business {
   private Long id;
   private String name;
   private String headquarters;

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
