import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crab extends MovingEntity{
    private static final String QUAKE_KEY = "quake";

    public Crab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, 0,0,0, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> crabTarget = world.findNearest(getPosition(), new Sgrass(null, null, 0, null));
        long nextPeriod = getActionPeriod();

        if (crabTarget.isPresent()) {
            Point tgtPos = crabTarget.get().getPosition();

            if (moveTo(world, crabTarget.get(), scheduler)) {
                Quake quake =  new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(world, imageStore, scheduler);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                nextPeriod);
    }
}

