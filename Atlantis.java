import processing.core.PImage;

import java.util.List;

public class Atlantis extends AnimatedEntity {
    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Atlantis(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0, 0, 0, 0);
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this, new Animation(this, ATLANTIS_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

}
