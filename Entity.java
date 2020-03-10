import java.util.List;
import processing.core.PImage;


public abstract class Entity {
   private String id;
   private Point position;
   private List<PImage> images;
   private int resourceLimit;
   private int resourceCount;
   private int imageIndex;
   private boolean canInteract;
   private double health;
   private double damage;

   public Entity(String id, Point position, List<PImage> images,int imageIndex, int resourceLimit, int resourceCount) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.canInteract = true;
      this.health = 100;
      this.damage = 21;
   }

   public Entity(String id, Point position, List<PImage> images,int imageIndex, int resourceLimit, int resourceCount,
                 boolean canInteract, double health, double damage) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.canInteract = canInteract;
      this.health = health;
      this.damage = damage;
   }

   public Point getPosition() {
      return position;
   }

   public List<PImage> getImages() {
      return images;
   }

   public int getImageIndex() {
      return imageIndex;
   }

   public String getId() {
      return id;
   }

   public int getResourceCount() {
      return resourceCount;
   }

   public int getResourceLimit() {
      return resourceLimit;
   }

   public void setResourceCount(int count) {
      resourceCount = count;
   }

   public void setPosition(Point p) {
      position = p;
   }

   public void setImageIndex(int i) { imageIndex = i; }

   public double getDamage() {
      return damage;
   }

   public double getHealth() {
      return health;
   }

   public void setHealth(double health) {
      this.health = health;
   }

   public void setDamage(double damage) {
      this.damage = damage;
   }

   public void setCanInteract(boolean canInteract) {
      this.canInteract = canInteract;
   }

   public boolean canInteract() {
      return canInteract;
   }
}