import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MovingEntity extends AnimatedEntity {

    private PathingStrategy strategy = new AStarPathingStrategy();

    public MovingEntity(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit,
                        int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Entity target) {
        List<Point> potentialPos = strategy.computePath(getPosition(), target.getPosition(),
                p ->  world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

        if(potentialPos.size() != 0)
            return potentialPos.get(0);
        else
            return getPosition();
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (EventScheduler.adjacent(getPosition(), target.getPosition()))
        {
            if(this instanceof OctoNotFull) {
                setResourceCount(getResourceCount() + 1);
                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
            }
            else if(this instanceof Crab) {
                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
            }

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target);

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean moveTo(WorldModel world, Point next, EventScheduler scheduler)
    {
        if(world.withinBounds(next)) {
            Optional<Entity> occupant = world.getOccupant(next);
            if (!getPosition().equals(next) && !occupant.isPresent()) {
                world.moveEntity(this, next);
                return true;
            }
        }
        return false;
    }

    public boolean attack(WorldModel world, Point attackPoint, EventScheduler scheduler)
    {
        Point newAttackPoint = new Point((getPosition().getX() + (int)Math.signum(attackPoint.getX() - getPosition().getX())),
                (getPosition().getY() + (int)Math.signum(attackPoint.getY() - getPosition().getY())));
        if(world.withinBounds(newAttackPoint)) {
            Optional<Entity> occupant = world.getOccupant(newAttackPoint);
            if (occupant.isPresent() && occupant.get().canInteract()) {
                System.out.println("Enemy health before hit: " + occupant.get().getHealth());
                occupant.get().setHealth(occupant.get().getHealth() - getDamage());
                System.out.println("Enemy health after hit: " + occupant.get().getHealth());
                if(occupant.get().getHealth() <= 0) {
                    world.removeEntity(occupant.get());
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                return true;
            }
            System.out.println("SWOOOOOOSSSSSHHHH!!!");
        }
        return false;
    }

//    public boolean withinRange(int range, Entity target)
//    {
//        if(getPosition().)
//    }
}

