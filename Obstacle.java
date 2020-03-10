import processing.core.PImage;

import java.util.List;

public class Obstacle extends ImpassableEntity{

    public Obstacle(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0, 0);
    }
}
