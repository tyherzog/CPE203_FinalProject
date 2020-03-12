import processing.core.PImage;

import javax.swing.text.View;
import java.util.List;

public class Player extends MovingEntity{

    private Point nextPos;

    public Player(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, 0,0,0, actionPeriod, animationPeriod);
        nextPos = position;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        long nextPeriod = getActionPeriod();
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                nextPeriod);
    }

    public void setMovement(int x, int y, WorldModel world, ImageStore imageStore, EventScheduler scheduler, WorldView view) {
        Point potentialPosition = new Point(nextPos.getX() + x, nextPos.getY() + y);
        if(moveTo(world, potentialPosition, scheduler)) {
            acceptableFrameShift(x, y, world, view);
            executeActivity(world, imageStore, scheduler);
            nextPos = potentialPosition;
        }
    }

    public void acceptableFrameShift(int x, int y, WorldModel world, WorldView view)
    {
        int borderValueX = 5;
        int borderValueY = 3;
        if((Math.abs(getPosition().getX() - view.getViewport().getCol()) <= borderValueX &&
                !(Math.abs(getPosition().getX()) <= borderValueX)) || (Math.abs(getPosition().getX() -
                (view.getViewport().getCol() + view.getViewport().getNumCols())) <= borderValueX &&
                !(Math.abs(getPosition().getX() - world.getNumCols()) <= borderValueX)))
            view.shiftView(x, 0);
        if((Math.abs(getPosition().getY() - view.getViewport().getRow()) <= borderValueY &&
                !(Math.abs(getPosition().getY()) <= borderValueY)) || (Math.abs(getPosition().getY() -
                (view.getViewport().getRow() + view.getViewport().getNumRows())) <= borderValueY &&
            !(Math.abs(getPosition().getY() - world.getNumRows()) <= borderValueY)))
            view.shiftView(0, y);

    }

    public void attackEnemy(Point mouseLoc, WorldModel world, ImageStore imageStore, EventScheduler scheduler, WorldView view) {
        if(attack(world, new Point(mouseLoc.getX() + view.getViewport().getCol(), mouseLoc.getY() +
                view.getViewport().getRow()), scheduler))
            executeActivity(world, imageStore, scheduler);
    }

}
