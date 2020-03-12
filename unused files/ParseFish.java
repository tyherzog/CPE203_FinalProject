public class ParseFish {
    private static final String FISH_KEY = "fish";
    private static final int FISH_NUM_PROPERTIES = 5;
    private static final int FISH_ID = 1;
    private static final int FISH_COL = 2;
    private static final int FISH_ROW = 3;
    private static final int FISH_ACTION_PERIOD = 4;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse.Parse(properties, world, imageStore, FISH_NUM_PROPERTIES, EntityFactory.makeFish(properties[FISH_ID],
                new Point(Integer.parseInt(properties[FISH_COL]), Integer.parseInt(properties[FISH_ROW])),
                Integer.parseInt(properties[FISH_ACTION_PERIOD]), imageStore.getImageList(FISH_KEY)));
    }
}
