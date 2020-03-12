public class ParseWall {
    private static final String WALL_KEY = "wall";
    private static final int WALL_NUM_PROPERTIES = 4;
    private static final int WALL_ID = 1;
    private static final int WALL_COL = 2;
    private static final int WALL_ROW = 3;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse.Parse(properties, world, imageStore, WALL_NUM_PROPERTIES, EntityFactory.makeObstacle(properties[WALL_ID],
                new Point(Integer.parseInt(properties[WALL_COL]), Integer.parseInt(properties[WALL_ROW])),
                imageStore.getImageList(WALL_KEY)));
    }
}
