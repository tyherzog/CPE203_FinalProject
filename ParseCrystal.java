public class ParseCrystal extends Parse{
    private static final String CRYSTAL_KEY = "crystal";
    private static final int CRYSTAL_NUM_PROPERTIES = 5;
    private static final int CRYSTAL_ID = 1;
    private static final int CRYSTAL_COL = 2;
    private static final int CRYSTAL_ROW = 3;
    private static final int CRYSTAL_ACTION_PERIOD = 4;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse(properties, world, imageStore, CRYSTAL_NUM_PROPERTIES, EntityFactory.makeCrystal(properties[CRYSTAL_ID],
                new Point(Integer.parseInt(properties[CRYSTAL_COL]), Integer.parseInt(properties[CRYSTAL_ROW])),
                Integer.parseInt(properties[CRYSTAL_ACTION_PERIOD]), imageStore.getImageList(CRYSTAL_KEY)));
    }
}
