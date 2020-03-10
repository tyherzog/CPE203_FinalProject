import processing.core.PImage;

import java.util.List;

public abstract class ImpassableEntity extends Entity {
    public ImpassableEntity(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit, int resourceCount) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount);
        setCanInteract(false);
    }
}
