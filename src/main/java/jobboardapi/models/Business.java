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
}
