public class ParsePlayer extends Parse {
    private static final String PLAYER_KEY = "player";
    private static final int PLAYER_NUM_PROPERTIES = 6;
    private static final int PLAYER_ID = 1;
    private static final int PLAYER_COL = 2;
    private static final int PLAYER_ROW = 3;
    private static final int PLAYER_ACTION_PERIOD = 4;
    private static final int PLAYER_ANIMATION_PERIOD = 5;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse(properties, world, imageStore, PLAYER_NUM_PROPERTIES, EntityFactory.makePlayer(properties[PLAYER_ID],
                new Point(Integer.parseInt(properties[PLAYER_COL]), Integer.parseInt(properties[PLAYER_ROW])),
                Integer.parseInt(properties[PLAYER_ACTION_PERIOD]), Integer.parseInt(properties[PLAYER_ANIMATION_PERIOD]),
                imageStore.getImageList(PLAYER_KEY)));
    }
}
