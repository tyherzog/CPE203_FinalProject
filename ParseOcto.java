public class ParseOcto extends Parse {
    private static final String OCTO_KEY = "octo";
    private static final int OCTO_NUM_PROPERTIES = 7;
    private static final int OCTO_ID = 1;
    private static final int OCTO_COL = 2;
    private static final int OCTO_ROW = 3;
    private static final int OCTO_LIMIT = 4;
    private static final int OCTO_ACTION_PERIOD = 5;
    private static final int OCTO_ANIMATION_PERIOD = 6;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse.Parse(properties, world, imageStore, OCTO_NUM_PROPERTIES, new OctoNotFull(properties[OCTO_ID],
                Integer.parseInt(properties[OCTO_LIMIT]), new Point(Integer.parseInt(properties[OCTO_COL]),
                Integer.parseInt(properties[OCTO_ROW])), Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]),
                imageStore.getImageList(OCTO_KEY)));
    }
}
