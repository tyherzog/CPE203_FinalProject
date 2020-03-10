import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends ActiveEntity{
    private int animationPeriod;


    public AnimatedEntity(String id, Point position, List<PImage> images, int imageIndex,
                          int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, imageIndex, resourceLimit, resourceCount, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public void nextImage() {
        setImageIndex((getImageIndex() + 1) % getImages().size());
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }
}
