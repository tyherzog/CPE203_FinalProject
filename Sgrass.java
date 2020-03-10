import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Sgrass extends AnimatedEntity{
    private static final String FISH_ID_PREFIX = "fish -- ";
    private static final int FISH_CORRUPT_MIN = 20000;
    private static final int FISH_CORRUPT_MAX = 30000;
    private static final String FISH_KEY = "fish";

    public Sgrass(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, images, 0,0,0, actionPeriod, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent()) {
            ActiveEntity fish = new Fish(FISH_ID_PREFIX + getId(),
                    openPt.get(), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                    imageStore.getImageList(FISH_KEY));
            world.addEntity(fish);
            fish.scheduleActions(world, imageStore, scheduler);
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), getActionPeriod());
    }
}
