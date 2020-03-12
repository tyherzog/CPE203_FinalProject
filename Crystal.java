import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crystal extends ActiveEntity{
    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    public Crystal(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, images, 0,0,0, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(getPosition());
        System.out.println(openPt.isPresent());
        if (openPt.isPresent()) {
            System.out.println(openPt.get());
            ActiveEntity goblin = EntityFactory.makeGoblin(getId() + CRAB_ID_SUFFIX,
                    openPt.get(), getActionPeriod() / CRAB_PERIOD_SCALE,
                    CRAB_ANIMATION_MIN +
                            rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                    imageStore.getImageList(CRAB_KEY));

            world.addEntity(goblin);
            goblin.scheduleActions(world, imageStore, scheduler);
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), getActionPeriod());
    }
}