import processing.core.PImage;

import java.util.*;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public int getNumRows()
   {
      return numRows;
   }

   public int getNumCols(){
      return numCols;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.getY()][pos.getX()];
   }

   public void setOccupancyCell(Point pos,
                                       Entity entity)
   {
      occupancy[pos.getY()][pos.getX()] = entity;
   }


   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }


   public boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < numRows &&
              pos.getX() >= 0 && pos.getX() < numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos))
      {
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos,
                                    Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }

   public Background getBackgroundCell(Point pos)
   {
      return background[pos.getY()][pos.getX()];
   }

   public void setBackgroundCell(Point pos,
                                        Background background)
   {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -Ore.ORE_REACH; dy <= Ore.ORE_REACH; dy++)
      {
         for (int dx = -Ore.ORE_REACH; dx <= Ore.ORE_REACH; dx++)
         {
            Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public Optional<Entity> findNearest(Point pos,
                                              Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : entities)
      {
         if (entity.getClass() == kind) //check
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public static Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

//   public boolean processLine(String line,
//                                     ImageStore imageStore)
//   {
//      String[] properties = line.split("\\s");
//      if (properties.length > 0)
//      {
//         switch (properties[PROPERTY_KEY])
//         {
//            case Background.BGND_KEY:
//               return parseBackground(properties, imageStore);
//            case MinerNotFull.MINER_KEY:
//               return parseMiner(properties, imageStore);
//            case Obstacle.OBSTACLE_KEY:
//               return parseObstacle(properties, imageStore);
//            case Ore.ORE_KEY:
//               return parseOre(properties, imageStore);
//            case Blacksmith.SMITH_KEY:
//               return parseSmith(properties, imageStore);
//            case Vein.VEIN_KEY:
//               return parseVein(properties, imageStore);
//         }
//      }
//
//      return false;
//   }
//
//   public boolean parseBackground(String [] properties, ImageStore imageStore)
//   {
//      if (properties.length == Background.BGND_NUM_PROPERTIES)
//      {
//         Point pt = new Point(Integer.parseInt(properties[Background.BGND_COL]),
//                 Integer.parseInt(properties[Background.BGND_ROW]));
//         String id = properties[Background.BGND_ID];
//         setBackground(pt,
//                 new Background(id, imageStore.getImageList(id)));
//      }
//
//      return properties.length == Background.BGND_NUM_PROPERTIES;
//   }
//
//   public boolean parseMiner(String [] properties, ImageStore imageStore)
//   {
//      if (properties.length == MinerNotFull.MINER_NUM_PROPERTIES)
//      {
//         Point pt = new Point(Integer.parseInt(properties[MinerNotFull.MINER_COL]),
//                 Integer.parseInt(properties[MinerNotFull.MINER_ROW]));
//         MinerNotFull entity = new MinerNotFull(properties[MinerNotFull.MINER_ID],
//                 Integer.parseInt(properties[MinerNotFull.MINER_LIMIT]),
//                 pt,
//                 Integer.parseInt(properties[MinerNotFull.MINER_ACTION_PERIOD]),
//                 Integer.parseInt(properties[MinerNotFull.MINER_ANIMATION_PERIOD]),
//                 imageStore.getImageList(MinerNotFull.MINER_KEY));
//         tryAddEntity(entity);
//      }
//
//      return properties.length == MinerNotFull.MINER_NUM_PROPERTIES;
//   }
//
//   public boolean parseObstacle(String [] properties,
//                                       ImageStore imageStore)
//   {
//      if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES)
//      {
//         Point pt = new Point(
//                 Integer.parseInt(properties[Obstacle.OBSTACLE_COL]),
//                 Integer.parseInt(properties[Obstacle.OBSTACLE_ROW]));
//         Entity entity = new Obstacle(properties[Obstacle.OBSTACLE_ID],
//                 pt, imageStore.getImageList(Obstacle.OBSTACLE_KEY));
//         tryAddEntity(entity);
//      }
//
//      return properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES;
//   }
//
//   public boolean parseOre(String [] properties,
//                                  ImageStore imageStore)
//   {
//      if (properties.length == Ore.ORE_NUM_PROPERTIES)
//      {
//         Point pt = new Point(Integer.parseInt(properties[Ore.ORE_COL]),
//                 Integer.parseInt(properties[Ore.ORE_ROW]));
//         Entity entity = new Ore(properties[Ore.ORE_ID],
//                 pt, Integer.parseInt(properties[Ore.ORE_ACTION_PERIOD]),
//                 imageStore.getImageList(Ore.ORE_KEY));
//         tryAddEntity(entity);
//      }
//
//      return properties.length == Ore.ORE_NUM_PROPERTIES;
//   }
//
//   public boolean parseSmith(String [] properties,
//                                    ImageStore imageStore)
//   {
//      if (properties.length == Blacksmith.SMITH_NUM_PROPERTIES)
//      {
//         Point pt = new Point(Integer.parseInt(properties[Blacksmith.SMITH_COL]),
//                 Integer.parseInt(properties[Blacksmith.SMITH_ROW]));
//         Entity entity = new Blacksmith(properties[Blacksmith.SMITH_ID],
//                 pt, imageStore.getImageList(Blacksmith.SMITH_KEY));
//         tryAddEntity(entity);
//      }
//
//      return properties.length == Blacksmith.SMITH_NUM_PROPERTIES;
//   }
//
//   public boolean parseVein(String [] properties,
//                                   ImageStore imageStore)
//   {
//      if (properties.length == Vein.VEIN_NUM_PROPERTIES)
//      {
//         Point pt = new Point(Integer.parseInt(properties[Vein.VEIN_COL]),
//                 Integer.parseInt(properties[Vein.VEIN_ROW]));
//         Entity entity = new Vein(properties[Vein.VEIN_ID],
//                 pt,
//                 Integer.parseInt(properties[Vein.VEIN_ACTION_PERIOD]),
//                 imageStore.getImageList(Vein.VEIN_KEY));
//         tryAddEntity(entity);
//      }
//
//      return properties.length == Vein.VEIN_NUM_PROPERTIES;
//   }

   public void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }


//   public void load(Scanner in, ImageStore imageStore)
//   {
//      int lineNumber = 0;
//      while (in.hasNextLine())
//      {
//         try
//         {
//            if (!processLine(in.nextLine(), imageStore))
//            {
//               System.err.println(String.format("invalid entry on line %d",
//                       lineNumber));
//            }
//         }
//         catch (NumberFormatException e)
//         {
//            System.err.println(String.format("invalid entry on line %d",
//                    lineNumber));
//         }
//         catch (IllegalArgumentException e)
//         {
//            System.err.println(String.format("issue on line %d: %s",
//                    lineNumber, e.getMessage()));
//         }
//         lineNumber++;
//      }
//   }


}
