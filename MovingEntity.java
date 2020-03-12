import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends AnimatedEntity {

    private PathingStrategy strategy = new AStarPathingStrategy();
    private Point nextPosition;
    private double damage;


    public MovingEntity(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit,
                        int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount, actionPeriod, animationPeriod);
        this.nextPosition = position;
        this.damage = 21;
    }

    public Point nextPosition(WorldModel world, Entity target) {
        List<Point> potentialPos = strategy.computePath(getPosition(), target.getPosition(),
                p ->  world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);

        if(potentialPos.size() != 0)
            nextPosition = potentialPos.get(0);
        else
            nextPosition = getPosition();
        return nextPosition;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition()))
        {
            if(this instanceof OctoNotFull) {
                attack(world, target.getPosition(), scheduler);
            }
            else if(this instanceof Goblin) {
                attack(world, target.getPosition(), scheduler);
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
        nextPosition = next;
        if(world.withinBounds(nextPosition)) {
            Optional<Entity> occupant = world.getOccupant(nextPosition);
            if (!getPosition().equals(nextPosition) && !occupant.isPresent()) {
                world.moveEntity(this, nextPosition);
                return true;
            }
        }
        return false;
    }

    public boolean attack(WorldModel world, Point attackPoint, EventScheduler scheduler)
    {
        System.out.println(attackPoint);
        Point newAttackPoint = new Point((getPosition().getX() + (int)Math.signum(attackPoint.getX() - getPosition().getX())),
                (getPosition().getY() + (int)Math.signum(attackPoint.getY() - getPosition().getY())));
        if(world.withinBounds(newAttackPoint)) {
            Optional<Entity> occupant = world.getOccupant(newAttackPoint);
            if (occupant.isPresent() && occupant.get() instanceof ActiveEntity) {
                ActiveEntity activeEntity = (ActiveEntity) occupant.get();
                activeEntity.setHealth(activeEntity.getHealth() - getDamage());
                if(activeEntity.getHealth() <= 0) {
                    world.removeEntity(occupant.get());
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                return true;
            }
            System.out.println("SWOOOOOOSSSSSHHHH!!!");
        }
        return false;
    }

    public Point getNextPosition() {
        return nextPosition;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}

