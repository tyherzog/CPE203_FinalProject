import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import processing.core.*;

import static java.awt.event.KeyEvent.*;

public class MapBuilder extends PApplet
{
   private PImage character;
   private int current_image;
   private long next_time;
   private PImage[] background = new PImage[11];
   private PImage[] obstacle = new PImage[3];
   private PImage voidSpace;
   private PImage[] wall  = new PImage[17];
   private PImage[] deco  = new PImage[3];
   private PImage[] hole = new PImage[9];
   private PImage stairs;

   private List<Point> path;

   private static final int TILE_SIZE = 32;

   private GridValues[][] grid;
   private int[][] gridFloorSel;
   private int[][] gridWallSel;
   private int[][] gridObstacleSel;
   private int[][] gridDecoSel;
   private int[][] gridHoleSel;


   private static final int ROWS = 30;
   private static final int COLS = 40;
   private static final int width = 1280;
   private static final int height = 960;

   private static enum GridValues { BACKGROUND, OBSTACLE, PLAYER, VOID, WALL, DECO, HOLE};

   private Point screenCalibration = new Point(width/2 - (COLS / 2 * TILE_SIZE), height/2 - (ROWS / 2 * TILE_SIZE));

   private char tileSelect;
   private int floorSelect = 0;
   private int wallSelect = 0;
   private int obstacleSelect = 0;
   private int decoSelect = 0;
   private int holeSelect = 0;

   public void settings()
   {
      size(width, height);
   }

   public void setup()
   {

      character = loadImage("images/wyvern1.bmp");
      background[0] = loadImage("images/background1.bmp");
      background[1] = loadImage("images/background2.bmp");
      background[2] = loadImage("images/background3.bmp");
      background[3] = loadImage("images/background4.bmp");
      background[4] = loadImage("images/background5.bmp");
      background[5] = loadImage("images/background6.bmp");
      background[6] = loadImage("images/background7.bmp");
      background[7] = loadImage("images/background8.bmp");
      background[8] = loadImage("images/background9.bmp");
      background[9] = loadImage("images/background10.bmp");
      background[10]= loadImage("images/stairs.bmp");
      obstacle[0]   = loadImage("images/bigBoulder.bmp");
      obstacle[1]   = loadImage("images/bigCrystal.bmp");
      obstacle[2]   = loadImage("images/hole.bmp");
      wall[0] = loadImage("images/wallTop.bmp");
      wall[1] = loadImage("images/wallTopRightCorner.bmp");
      wall[2] = loadImage("images/wallTopLeftCorner.bmp");
      wall[3] = loadImage("images/wallSideLeft.bmp");
      wall[4] = loadImage("images/wallSideRight.bmp");
      wall[5] = loadImage("images/wallBottomGeneral.bmp");
      wall[6] = loadImage("images/wallFrontRightCorner.bmp");
      wall[7] = loadImage("images/wallFrontLeftCorner.bmp");
      wall[8] = loadImage("images/wallBottomAwkwardLeft.bmp");
      wall[9] = loadImage("images/wallBottomAwkwardRight.bmp");
      wall[10] = loadImage("images/wallTopRight.bmp");
      wall[11] = loadImage("images/wallTopLeft.bmp");
      wall[12] = loadImage("images/wallTopThin.bmp");
      wall[13] = loadImage("images/wallSideDouble.bmp");
      wall[14] = loadImage("images/wallBottomLeft.bmp");
      wall[15] = loadImage("images/wallBottomRight.bmp");
      wall[16] =loadImage("images/wallBottomThin.bmp");
      deco[0] = loadImage("images/decoSkeleton.bmp");
      deco[1] = loadImage("images/decoRock.bmp");
      deco[2] = loadImage("images/decoMushroom.bmp");
      hole[0] = loadImage("images/bigHole1.bmp");
      hole[1] = loadImage("images/bigHole2.bmp");
      hole[2] = loadImage("images/bigHole3.bmp");
      hole[3] = loadImage("images/bigHole4.bmp");
      hole[4] = loadImage("images/bigHole5.bmp");
      hole[5] = loadImage("images/bigHole6.bmp");
      hole[6] = loadImage("images/bigHole7.bmp");
      hole[7] = loadImage("images/bigHole8.bmp");
      hole[8] = loadImage("images/bigHole9.bmp");
      voidSpace = loadImage("images/void.bmp");

      grid = new GridValues[ROWS][COLS];
      gridDecoSel = new int[ROWS][COLS];
      gridFloorSel = new int[ROWS][COLS];
      gridHoleSel = new int[ROWS][COLS];
      gridObstacleSel = new int[ROWS][COLS];
      gridWallSel = new int[ROWS][COLS];
      initialize_grid(grid);

      noLoop();
   }

   private static void initialize_grid(GridValues[][] grid)
   {
      for(int row = 0; row < ROWS; row++) {
         for(int col = 0; col < COLS; col++) {
            grid[row][col] = GridValues.VOID;
         }
      }
   }

   public void draw()
   {
      draw_grid();
   }

   private void draw_grid()
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            draw_tile(row, col);
         }
      }
   }

   private PImage imageSelector(PImage[] imageArray, int sel){
      return imageArray[sel];
   }

//   private void draw_path(boolean foundPath)
//   {
//      for (Point p : path)
//      {
//         if (foundPath)
//            fill(128, 0, 0);
//         else
//            fill(0);
//
//         rect(p.x * TILE_SIZE + TILE_SIZE * 3 / 8,
//              p.y * TILE_SIZE + TILE_SIZE * 3 / 8,
//              TILE_SIZE / 4, TILE_SIZE / 4);
//      }
//   }

   private void draw_tile(int row, int col)
   {
      switch (grid[row][col])
      {
         case VOID:
            image(voidSpace, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case BACKGROUND:
            image(imageSelector(background, gridFloorSel[row][col]), col * TILE_SIZE, row * TILE_SIZE);
            break;
         case WALL:
            image(imageSelector(wall, gridWallSel[row][col]), col * TILE_SIZE, row * TILE_SIZE );
            break;
         case OBSTACLE:
            image(imageSelector(obstacle, gridObstacleSel[row][col]), col * TILE_SIZE, row * TILE_SIZE );
            break;
         case HOLE:
            image(imageSelector(hole, gridHoleSel[row][col]), col * TILE_SIZE, row * TILE_SIZE);
            break;
         case DECO:
            image(imageSelector(deco, gridDecoSel[row][col]), col * TILE_SIZE, row * TILE_SIZE);
            break;
         case PLAYER:
            image(character, col * TILE_SIZE, row * TILE_SIZE);
            break;


      }
   }

   public static void main(String args[])
   {
      PApplet.main("MapBuilder");
   }

   public void keyPressed()
   {
      switch (keyCode) {
         case 79:
            obstacleSelect++;
            if(obstacleSelect == obstacle.length)
               obstacleSelect = 0;
            System.out.print("Obstacle: " + obstacleSelect);
            tileSelect = 'O';
            break;
         case 80:
            System.out.print("Player");
            tileSelect = 'P';
            break;
         case '1':
            System.out.print("Delete");
            tileSelect = '1';
            break;
         case VK_F:
            floorSelect++;
            if(floorSelect == background.length)
               floorSelect = 0;
            System.out.print("background: " + floorSelect);
            tileSelect = 'F';
            break;
         case VK_W:
            wallSelect++;
            if(wallSelect == wall.length)
               wallSelect = 0;
            System.out.print("wall: " + wallSelect);
            tileSelect = 'W';
            break;
         case VK_D:
            decoSelect++;
            if(decoSelect == deco.length)
               decoSelect = 0;
            System.out.print("Deco: " + decoSelect);
            tileSelect = 'D';
            break;
         case VK_H:
            holeSelect++;
            if(holeSelect == hole.length)
               holeSelect = 0;
            System.out.print("hole: " + holeSelect);
            tileSelect = 'H';
            break;
         case 'S':
            saveWorld("World2");
            break;
      }
      System.out.println(" selected!");
   }

   public void mousePressed()
   {
      Point pressed = mouseToPoint(mouseX, mouseY);
      switch(tileSelect) {
         case '1': //delete all blocks
               grid[pressed.y][pressed.x] = GridValues.VOID;
               break;
         case 'O': //obstacle placer
               toggle(pressed, GridValues.OBSTACLE, GridValues.BACKGROUND);
            gridObstacleSel[pressed.y][pressed.x] = obstacleSelect;
            break;
         case 'P': //character placer
               toggle(pressed, GridValues.PLAYER, GridValues.BACKGROUND);
            break;
         case 'F': //background placer
               toggle(pressed, GridValues.BACKGROUND, GridValues.VOID);
            gridFloorSel[pressed.y][pressed.x] = floorSelect;
            break;
         case 'W': //Wall placer
               toggle(pressed, GridValues.WALL, GridValues.VOID);
            gridWallSel[pressed.y][pressed.x] = wallSelect;
            break;
         case 'D': //Deco placer
            toggle(pressed, GridValues.DECO, GridValues.BACKGROUND);
            gridDecoSel[pressed.y][pressed.x] = decoSelect;
            break;
         case 'H': //Hole placer
          gridHoleSel[pressed.y][pressed.x] = holeSelect;
              toggle(pressed, GridValues.HOLE, GridValues.VOID);
            break;
      }
      redraw();
      
   }

   public void toggle(Point pressed, GridValues material, GridValues background) {
      if (grid[pressed.y][pressed.x] == material)
         grid[pressed.y][pressed.x] = background;
      else if (grid[pressed.y][pressed.x] == background)
         grid[pressed.y][pressed.x] = material;
   }

   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/TILE_SIZE, mouseY/TILE_SIZE);
   }

   public void saveWorld(String fileName){
      try {
         FileWriter myWriter = new FileWriter(fileName);
         String entityType = "void";
         for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
               switch (grid[row][col])
               {
                  case BACKGROUND:
                     entityType = "background";
                     myWriter.write(entityType + " " + findImageName(GridValues.BACKGROUND, gridFloorSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;
                  case OBSTACLE:
                     entityType = "obstacle";
                     myWriter.write(entityType + " " + findImageName(GridValues.OBSTACLE, gridObstacleSel[row][col])
                             + " " + col + " " + row + "\n");
                     entityType = "background";
                     myWriter.write(entityType + " " + findImageName(GridValues.BACKGROUND, gridFloorSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;
                  case WALL:
                     entityType = "obstacle";
                     myWriter.write(entityType + " " + findImageName(GridValues.WALL, gridWallSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;
                  case DECO:
                     entityType = "deco";
                     myWriter.write(entityType + " " + findImageName(GridValues.DECO, gridDecoSel[row][col])
                             + " " + col + " " + row + "\n");
                     entityType = "background";
                     myWriter.write(entityType + " " + findImageName(GridValues.BACKGROUND, gridFloorSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;
                  case HOLE:
                     entityType = "obstacle";
                     myWriter.write(entityType + " " + findImageName(GridValues.HOLE, gridHoleSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;
                  case PLAYER:
                     entityType = "player";
                     myWriter.write(entityType + " MainCharacter_" + col + "_" + row + " " + col + " " + row + "\n");
                     entityType = "background";
                     myWriter.write(entityType + " " + findImageName(GridValues.BACKGROUND, gridFloorSel[row][col])
                             + " " + col + " " + row + "\n");
                     break;

                  case VOID:
                     entityType = "background";
                     myWriter.write(entityType + " " + "void"
                             + " " + col + " " + row + "\n");
                     break;
                  default:
                     break;
               }
            }
         }
         myWriter.close();
         System.out.println("Successfully wrote to the file.");
      }
      catch(IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }

   public String findImageName(GridValues type, int sel) {
      String imageName = "";
      switch (type) {
         case BACKGROUND:
            switch (sel) {
               case 0:
                  imageName = "background1";
               break;
               case 1:
                  imageName = "background2";
               break;
               case 2:
                  imageName = "background3";
               break;
               case 3:
                  imageName = "background4";
               break;
               case 4:
                  imageName = "background5";
               break;
               case 5:
                  imageName = "background6";
               break;
               case 6:
                  imageName = "background7";
               break;
               case 7:
                  imageName = "background8";
               break;
               case 8:
                  imageName = "background9";
               break;
               case 9:
                  imageName = "background10";
               break;
               case 10:
                  imageName = "stairs";
               break;
            }
            break;
         case OBSTACLE:
            switch (sel) {
               case 0:
                  imageName = "bigBoulder";
               break;
               case 1:
                  imageName = "bigCrystal";
               break;
               case 2:
                  imageName = "hole";
               break;
            }
            break;
         case WALL:
            switch (sel) {
               case 0:
                  imageName = "wallTop";
               break;
               case 1:
                  imageName = "wallTopRightCorner";
               break;
               case 2:
                  imageName = "wallTopLeftCorner";
               break;
               case 3:
                  imageName = "wallSideLeft";
               break;
               case 4:
                  imageName = "wallSideRight";
               break;
               case 5:
                  imageName = "wallBottomGeneral";
               break;
               case 6:
                  imageName = "wallFrontRightCorner";
               break;
               case 7:
                  imageName = "wallFrontLeftCorner";
               break;
               case 8:
                  imageName = "wallBottomAwkwardLeft";
               break;
               case 9:
                  imageName = "wallBottomAwkwardRight";
               break;
               case 10:
                  imageName = "wallTopRight";
               break;
               case 11:
                  imageName = "wallTopLeft";
               break;
               case 12:
                  imageName = "wallTopThin";
               break;
               case 13:
                  imageName = "wallSideDouble";
               break;
               case 14:
                  imageName = "wallBottomLeft";
               break;
               case 15:
                  imageName = "wallBottomRight";
               break;
               case 16:
                  imageName = "wallBottomThin";
               break;
            }
            break;
         case DECO:
            switch (sel) {
               case 0:
                  imageName = "decoSkeleton";
               break;
               case 1:
                  imageName = "decoRock";
               break;
               case 2:
                  imageName = "decoMushroom";
               break;
            }
            break;
         case HOLE:
            switch (sel) {
               case 0:
                  imageName = "bigHole1";
               break;
               case 1:
                  imageName = "bigHole2";
               break;
               case 2:
                  imageName = "bigHole3";
               break;
               case 3:
                  imageName = "bigHole4";
               break;
               case 4:
                  imageName = "bigHole5";
               break;
               case 5:
                  imageName = "bigHole6";
               break;
               case 6:
                  imageName = "bigHole7";
               break;
               case 7:
                  imageName = "bigHole8";
               break;
               case 8:
                  imageName = "bigHole9";
               break;

            }
            break;
         default:
            break;
      }
      return imageName;
   }
}
