public class ParseAtlantis extends Parse{
    private static final String ATLANTIS_KEY = "atlantis";
    private static final int ATLANTIS_NUM_PROPERTIES = 4;
    private static final int ATLANTIS_ID = 1;
    private static final int ATLANTIS_COL = 2;
    private static final int ATLANTIS_ROW = 3;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse(properties, world, imageStore, ATLANTIS_NUM_PROPERTIES, EntityFactory.makeAtlantis(properties[ATLANTIS_ID],
                new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                        Integer.parseInt(properties[ATLANTIS_ROW])),
                imageStore.getImageList(ATLANTIS_KEY)));
    }
}
