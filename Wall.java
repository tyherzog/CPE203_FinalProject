import processing.core.PImage;

import java.util.List;

public class Wall extends ImpassableEntity {
    public Wall(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0, 0);
    }
}
