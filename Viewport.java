/*
Viewport ideally helps control what part of the world we are looking at for drawing only what we see
Includes helpful helper functions to map between the viewport and the real world
 */


final class Viewport
{
   private int row;
   private int col;
   private int numRows;
   private int numCols;
   private Point center;

   public Viewport(int numRows, int numCols, Player main)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.col = main.getPosition().getX() - (numCols / 2);
      this.row = main.getPosition().getY() - (numRows / 2);
      this.center = new Point(col + (numCols / 2), row + (numRows / 2));
   }

   public void shift(int col, int row)
   {
      this.col = col;
      this.row = row;
      this.center = new Point(col + (numCols / 2), row + (numRows / 2));
   }

   public boolean contains(Point p)
   {
      return p.getY() >= row && p.getY() < row + numRows &&
              p.getX() >= col && p.getX() < col + numCols;
   }

   public Point viewportToWorld(int col, int row)
   {
      return new Point(col + this.col, row + this.row);
   }

   public Point worldToViewport(int col, int row)
   {
      return new Point(col - this.col, row - this.row);
   }

   public int getRow() {
      return row;
   }

   public int getCol() {
      return col;
   }

   public int getNumRows() {
      return numRows;
   }

   public int getNumCols() {
      return numCols;
   }

   public Point getCenter() {
      return center;
   }
}
