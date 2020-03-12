import processing.core.PImage;

import java.util.List;

public class EntityFactory {
    public static Obstacle makeObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }

    public static Background makeBackground(String id, Point pt, List<PImage> images)
    {
        return new Background(id, pt, images);
    }

    public static Atlantis makeAtlantis(String id, Point position, List<PImage> images) {
        return new Atlantis(id, position, images);
    }

    public static Fish makeFish(String id, Point position, int actionPeriod, List<PImage> images){
        return new Fish(id, position, actionPeriod, images);
    }

    public static Quake makeQuake(Point position, List<PImage> images) {
        return new Quake(position, images);
    }

    public static Crystal makeCrystal(String id, Point position, int actionPeriod, List<PImage> images) {
        return new Crystal(id, position, actionPeriod, images);
    }

    public static OctoFull makeOctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new OctoFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public static OctoNotFull makeOctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new OctoNotFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public static Goblin makeGoblin(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new Goblin(id, position, actionPeriod, animationPeriod, images);
    }

    public static Player makePlayer(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new Player(id, position, actionPeriod, animationPeriod, images);
    }

    public static Void makeVoid(String id, Point position, List<PImage> images) {
        return new Void(id, position, images);
    }

    public static Wall makeWall(String id, Point position, List<PImage> images) {
        return new Wall(id, position, images);
    }


}
