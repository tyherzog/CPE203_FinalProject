import java.util.List;
import processing.core.PImage;


public abstract class Entity {
   private String id;
   private Point position;
   private List<PImage> images;
   private int resourceLimit;
   private int resourceCount;
   private int imageIndex;

   public Entity(String id, Point position, List<PImage> images,int imageIndex, int resourceLimit, int resourceCount) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
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
}