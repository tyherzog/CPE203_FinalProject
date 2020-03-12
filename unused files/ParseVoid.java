public class ParseVoid extends Parse {
    private static final String VOID_KEY = "void";
    private static final int VOID_NUM_PROPERTIES = 4;
    private static final int VOID_ID = 1;
    private static final int VOID_COL = 2;
    private static final int OCTO_ROW = 3;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse.Parse(properties, world, imageStore, VOID_NUM_PROPERTIES, EntityFactory.makeVoid(properties[VOID_ID],
                new Point(Integer.parseInt(properties[VOID_COL]), Integer.parseInt(properties[OCTO_ROW])),
                imageStore.getImageList(VOID_KEY)));
    }
}
