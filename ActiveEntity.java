import processing.core.PImage;

import java.util.List;
import java.util.Random;

public abstract class ActiveEntity extends Entity{
    public static final Random rand = new Random();
    private double health;
    private int actionPeriod;

    public ActiveEntity(String id, Point position, List<PImage> images, int imageIndex,
                        int resourceLimit, int resourceCount, int actionPeriod) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount);
        this.actionPeriod = actionPeriod;
        this.health = 100;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), getActionPeriod());
        if(this instanceof AnimatedEntity)
            scheduler.scheduleEvent(this, new Animation((AnimatedEntity) this, 0),
                    ((AnimatedEntity)this).getAnimationPeriod());
    }


    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
