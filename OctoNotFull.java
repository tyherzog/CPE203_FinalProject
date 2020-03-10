import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OctoNotFull extends MovingEntity {

    public OctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, 0, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(getPosition(),
                new Fish(null, null, 0, null));

        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, imageStore, scheduler)) {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public boolean transformNotFull(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        if (getResourceCount() >= getResourceLimit())
        {
            AnimatedEntity octo = new OctoFull(getId(), getResourceLimit(),
                    getPosition(), getActionPeriod(), getAnimationPeriod(),
                    getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleActions(world, imageStore, scheduler);

            return true;
        }

        return false;
    }
}
