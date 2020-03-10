public class ParseSgrass extends Parse{
    private static final String SGRASS_KEY = "seaGrass";
    private static final int SGRASS_NUM_PROPERTIES = 5;
    private static final int SGRASS_ID = 1;
    private static final int SGRASS_COL = 2;
    private static final int SGRASS_ROW = 3;
    private static final int SGRASS_ACTION_PERIOD = 4;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse(properties, world, imageStore, SGRASS_NUM_PROPERTIES, new Sgrass(properties[SGRASS_ID],
                new Point(Integer.parseInt(properties[SGRASS_COL]), Integer.parseInt(properties[SGRASS_ROW])),
                Integer.parseInt(properties[SGRASS_ACTION_PERIOD]), imageStore.getImageList(SGRASS_KEY)));
    }
}
