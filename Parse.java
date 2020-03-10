public abstract class Parse {

    public static boolean Parse(String[] properties, WorldModel world, ImageStore imageStore, int NUM_PROPERTIES, Entity entity)
    {
        if (properties.length == NUM_PROPERTIES)
        {
            if(entity instanceof Background)
                world.setBackground((Background) entity);
            else
                world.tryAddEntity(entity);
        }

        return properties.length == NUM_PROPERTIES;
    }
}
