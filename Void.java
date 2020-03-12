import processing.core.PImage;

import java.util.List;

public class Void extends ImpassableEntity {
    public Void(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0, 0);
    }
}
