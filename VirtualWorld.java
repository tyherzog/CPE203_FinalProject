import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;

import javax.swing.*;

import static java.awt.event.KeyEvent.*;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   public static final int TILE_WIDTH = 48;
   public static final int TILE_HEIGHT = 48;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;
   private static final int VIEW_COLS = 15;
           //= VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = 11;
                   //= VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = 40;
                           //VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = 30;
                                   //VIEW_ROWS * WORLD_HEIGHT_SCALE;

   public static int VIEW_WIDTH = TILE_WIDTH * VIEW_COLS;
   public static int VIEW_HEIGHT = TILE_HEIGHT * VIEW_ROWS;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x0;

   private static final String LOAD_FILE_NAME = "World1.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   private Player mainCharacter;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;

   boolean keyLeft, keyRight, keyUp, keyDown, shift;
   private static final int running = 1;
   private static final float incr_factor = 2;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
      //fullScreen();
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      /*
      Uses setup time to find main character and set it as variable in Virtual World to be further manipulated
       */
      for (Entity entity : world.getEntities()) {
         if(entity instanceof Player) {
            mainCharacter = (Player)entity;
         }
      }

      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT, mainCharacter);
      this.scheduler = new EventScheduler(timeScale);


      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;

   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
         if(!checkGameEnd()) {
            characterMove();
         }
         }

      view.drawViewport();
   }


   public void mousePressed() {

            Point pressed = mouseToPoint(mouseX, mouseY);
            System.out.println(pressed);
            mainCharacter.attackEnemy(pressed, world, imageStore, scheduler, view);
    }




   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
   }

   public void keyPressed() {
      if (keyCode == VK_W)
         keyUp = true;
      if (keyCode == VK_S)
         keyDown = true;
      if (keyCode == VK_A)
         keyLeft = true;
      if (keyCode == VK_D)
         keyRight = true;

      if (keyCode == VK_SPACE) {

         Point p = mainCharacter.getPosition();
         p = view.getViewport().worldToViewport(p.getX(), p.getY());

         if (!(world.isOccupied(p))) {
            Entity ship = p.createAngelBall(imageStore.getImageList("angleBall"));

            world.addEntity(ship);
            ((ActiveEntity) ship).scheduleActions( world, imageStore,scheduler);
         }


         for (int i = p.getX() ; i <= p.getX() ; i++) {
            for (int j = p.getY() ; j <= p.getY() ; j++) {
               if (world.withinBounds(new Point(i, j))) {
                  world.setBackgroundCell(new Point(i, j), new Background("FreezeMagic", p, imageStore.getImageList("FreezeMagic")));
               }

            }

         }

         int endLoop = 0;
         try {
            //This is to check if the crab is inside the square grid. Need to change new enemy entity
            while (endLoop == 0 && (world.findNearest(p, Goblin.class).get() instanceof Goblin)) {
               Goblin goblin = (Goblin) world.findNearest(p, Goblin.class).get();
               if (goblin.getPosition().distanceSquared(p) > 8) {
                  endLoop = 1;
               } else {

                  goblin.transformFreezed(world, scheduler, imageStore);
               }
            }
         }
         catch (Exception NoSuchElementException){
            endLoop =1;
         }


      }
   }

   public void keyReleased() {
      if (keyCode == VK_W)
         keyUp = false;
      if (keyCode == VK_S)
         keyDown = false;
      if (keyCode == VK_A)
         keyLeft = false;
      if (keyCode == VK_D)
         keyRight = false;


   }

   public boolean checkGameEnd() {
      if(!world.getEntities().contains(mainCharacter)) {
         return true;
      }
      return false;
   }

   public void characterMove(){
      int xSpeed = 0;
      int ySpeed = 0;
      if(keyLeft && shift) xSpeed = (int)(-1*incr_factor*running);
      else if (keyLeft) xSpeed = -1*running;
      if(keyRight && shift) xSpeed = (int)incr_factor*running;
      else if (keyRight) xSpeed = running;
      if(keyUp && shift) ySpeed = (int)((-1)*incr_factor*running);
      else if (keyUp) ySpeed = (-1)*running;
      if(keyDown && shift) ySpeed = (int)((incr_factor)*running);
      else if (keyDown) ySpeed = running;

      mainCharacter.setMovement(xSpeed, ySpeed, world, imageStore, scheduler, view);
   }

   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return EntityFactory.makeBackground(DEFAULT_IMAGE_NAME, new Point(0,0),
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void loadWorld(WorldModel world, String filename, ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         Functions.load(in, world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)
         if (entity instanceof AnimatedEntity) {
            AnimatedEntity animatedEntity = (AnimatedEntity) entity;
            if (animatedEntity.getActionPeriod() > 0)
               animatedEntity.scheduleActions(world, imageStore, scheduler);
         }
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
      Music.playMusic();
   }
}
