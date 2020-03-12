import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

/*
WorldView ideally mostly controls drawing the current part of the whole world
that we can see based on the viewport
*/

final class WorldView
{
   private PApplet screen;
   private WorldModel world;
   private int tileWidth;
   private int tileHeight;
   private Viewport viewport;
   private Player main;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight, Player main)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.main = main;
      this.viewport = new Viewport(numRows, numCols, main);
   }

   private static int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }


   public void shiftView(int colDelta, int rowDelta)
   {
      int newCol = clamp(viewport.getCol() + colDelta, 0,
              world.getNumCols() - viewport.getNumCols());
      int newRow = clamp(viewport.getRow() + rowDelta, 0,
              world.getNumRows() - viewport.getNumRows());

      viewport.shift(newCol, newRow);
   }

   private void drawBackground()
   {
      for (int row = 0; row < viewport.getNumRows(); row++)
      {
         for (int col = 0; col < viewport.getNumCols(); col++)
         {
            Point worldPoint = viewport.viewportToWorld(col, row);
            Optional<PImage> image = world.getBackgroundImage(worldPoint);
            if (image.isPresent())
            {
               screen.image(image.get(), col * tileWidth,
                       row * tileHeight, (float)(image.get().width*1.5), (float)(image.get().height*1.5));
            }
         }
      }
   }

   private void drawEntities()
   {
      for (Entity entity : world.getEntities())
      {
         Point pos = entity.getPosition();

         if (viewport.contains(pos))
         {

            Point viewPoint = viewport.worldToViewport(pos.getX(), pos.getY());
            screen.image(getCurrentImage(entity),
                    viewPoint.getX() * tileWidth, viewPoint.getY() * tileHeight,
                    (float) (getCurrentImage(entity).get().width * 1.5), (float) (getCurrentImage(entity).get().height * 1.5));

         }
      }
   }

   public void drawViewport()
   {
      drawBackground();
      drawEntities();
      drawHUD();
      if(!world.getEntities().contains(main))
         drawGameEnd();
   }

   public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).getImages()
                 .get(((Background)entity).getImageIndex());
      }
      else if (entity instanceof Entity)
      {
         return ((Entity)entity).getImages().get(((Entity)entity).getImageIndex());
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         entity));
      }
   }

   private void drawHUD()
   {
      screen.image(checkHealth(), (float)(tileWidth*(viewport.getNumCols() - 2.5)), tileHeight/3,
              (float)(checkHealth().width*1.5), (float)(checkHealth().height*1.5));
   }

   private PImage checkHealth()
   {
      if(main.getHealth() > 75)
         return animateHealth();
      else if(main.getHealth() <= 75 && main.getHealth() > 50)
         return screen.loadImage("images/threeFourthHealth.bmp");
      else if(main.getHealth() <= 50 && main.getHealth() > 25)
         return screen.loadImage("images/halfHealth.bmp");
      else if(main.getHealth() <= 25 && main.getHealth() > 0)
         return screen.loadImage("images/oneFourthHealth.bmp");
      else {
         return screen.loadImage("images/noHealth.bmp");
      }
   }

   private PImage animateHealth()
   {
      int select = screen.frameCount % 4;
      PImage[] animation = {screen.loadImage("images/fullHealth1.bmp"),
              screen.loadImage("images/fullHealth2.bmp"),
              screen.loadImage("images/fullHealth3.bmp"),
              screen.loadImage("images/fullHealth4.bmp")};
      return animation[select];
   }

   public Viewport getViewport() {
      return viewport;
   }

   public void drawGameEnd() {
      screen.image(screen.loadImage("images/wyvern1.bmp"),
              (float)(viewport.getNumCols() / 2 * tileWidth),
              (float)(viewport.getNumRows() / 2 * tileHeight));

   }
}
