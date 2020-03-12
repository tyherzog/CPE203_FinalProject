import java.util.Scanner;

/*
Functions - everything our virtual world is doing right now - is this a good design?
 */

final class Functions
{
   //constants
   private static final String OBSTACLE_KEY = "obstacle";
   private static final String CRYSTAL_KEY = "crystal";
   private static final String BGND_KEY = "background";
   private static final String PLAYER_KEY = "player";

   private static final int PROPERTY_KEY = 0;

   public static void load(Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                  lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
               lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
               lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private static boolean processLine(String line, WorldModel world,
                                     ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
         case BGND_KEY:
            return ParseBackground.parse(properties, world, imageStore);
         case OBSTACLE_KEY:
            return ParseObstacle.parse(properties, world, imageStore);
         case CRYSTAL_KEY:
            return ParseCrystal.parse(properties, world, imageStore);
         case PLAYER_KEY:
            return ParsePlayer.parse(properties, world, imageStore);
         }
      }

      return false;
   }
}
