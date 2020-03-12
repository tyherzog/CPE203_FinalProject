public class ParseBackground extends Parse{
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    public static boolean parse(String[] properties, WorldModel world, ImageStore imageStore) {
        return Parse.Parse(properties, world, imageStore, BGND_NUM_PROPERTIES,
                EntityFactory.makeBackground(properties[BGND_ID], new Point(Integer.parseInt(properties[BGND_COL]),
                        Integer.parseInt(properties[BGND_ROW])), imageStore.getImageList(properties[BGND_ID])));
    }
}
