public class Activity implements  Action{
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = 0;
    }

    public void executeAction(EventScheduler scheduler) {
        if(entity instanceof ActiveEntity) {
            ((ActiveEntity) entity).executeActivity(world, imageStore, scheduler);
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s", entity));
        }
    }
}
