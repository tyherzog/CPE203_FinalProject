import java.util.List;
import processing.core.PImage;

final class Background extends Entity
{
   public Background(String id, Point pt, List<PImage> images)
   {
      super(id, pt, images, 0,0,0);
   }
}
