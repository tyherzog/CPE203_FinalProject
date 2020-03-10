import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OctoFull extends MovingEntity{

    public OctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, 0, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                new Atlantis(null, null, null));

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler)) {
            //at atlantis trigger animation
            ((Atlantis)(fullTarget.get())).scheduleActions(world, imageStore, scheduler);

            //transform to unfull
            transformFull(world, imageStore, scheduler);
        } else {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        AnimatedEntity octo = new OctoNotFull(getId(), getResourceLimit(),
                getPosition(), getActionPeriod(), getAnimationPeriod(),
                getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActions(world, imageStore, scheduler);
    }
}
