import java.util.Comparator;

final class Point implements Comparable<Point>
{
   public final int x;
   public final int y;
   private int g;
   private int h;
   private int f;
   private Point prev;


   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
      this.g = 0;
      this.h = 0;
      this.f = 0;
      this.prev = null;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
      //+ "G: " + g + " H: " + h
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent(Point p)
   {
      return (x == p.x && Math.abs(y - p.y) == 1) ||
              (y == p.y && Math.abs(x - p.x) == 1);
   }

   public void calculateG(Point prev, Point end) {
      int newG = (int)(prev.getG() + Math.sqrt(Math.pow(prev.getX() - this.x, 2) + Math.pow(prev.getY() - this.y, 2))*10);
      if(this.g > newG || this.g == 0) {
         this.g = newG;
         this.prev = prev;
         calculateH(end);
         setF();
      }
   }

   public int getG() {
      return g;
   }

   public void calculateH(Point end) {
      this.h = (int)(Math.sqrt(Math.pow(end.getX() - this.x, 2) + Math.pow(end.getY() - this.y, 2))*10);
   }

   public void setF() {
      this.f = this.g + this.h;
   }

   public int getF() {
      return f;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public Point getPrev() {
      return prev;
   }

   public void setPrev(Point prev) {
      this.prev = prev;
   }


   @Override
   public int compareTo(Point o) {
      return this.getF() - o.getF();
   }

}
