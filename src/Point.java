final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
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

   public boolean adjacent(Point p2)
   {
      return (x == p2.x && Math.abs(y - p2.y) == 1) ||
              (y == p2.y && Math.abs(x - p2.x) == 1);
   }

   public int distanceSquared(Point p2)
   {
      int deltaX = x - p2.x;
      int deltaY = y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }
   public double heuristicDistance(Point p)
   {

      //diagonal movement is allowed - diagonal distance
//      return Math.sqrt( (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));

      //diagonal movement not allowed - manhattan distance
      return Math.abs(x - p.x) + Math.abs(y - p.y);

   }
}

//A point