import java.util.Scanner;

public class GameLoad
{
    public static final int PROPERTY_KEY = 0;

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
            } catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            } catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }

    public static boolean processLine(String line, WorldModel world,
                               ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case Background.BGND_KEY:
                    return parseBackground(properties, world, imageStore);
                case Miner.MINER_KEY:
                    return parseMiner(properties, world, imageStore);
                case Obstacle.OBSTACLE_KEY:
                    return parseObstacle(properties, world, imageStore);
                case Ore.ORE_KEY:
                    return parseOre(properties, world,  imageStore);
                case Blacksmith.SMITH_KEY:
                    return parseSmith(properties, world, imageStore);
                case Vein.VEIN_KEY:
                    return parseVein(properties, world, imageStore);
            }
        }

        return false;
    }

    public static boolean parseBackground(String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Background.BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Background.BGND_COL]),
                    Integer.parseInt(properties[Background.BGND_ROW]));
            String id = properties[Background.BGND_ID];
            world.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == Background.BGND_NUM_PROPERTIES;
    }

    public static boolean parseMiner(String[] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Miner.MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Miner.MINER_COL]),
                    Integer.parseInt(properties[Miner.MINER_ROW]));
            MinerNotFull entity = new MinerNotFull(properties[MinerNotFull.MINER_ID],
                    Integer.parseInt(properties[Miner.MINER_LIMIT]),
                    pt,
                    Integer.parseInt(properties[Miner.MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[Miner.MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(Miner.MINER_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Miner.MINER_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(String[] properties, WorldModel world,
                                 ImageStore imageStore)
    {
        if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[Obstacle.OBSTACLE_COL]),
                    Integer.parseInt(properties[Obstacle.OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[Obstacle.OBSTACLE_ID],
                    pt, imageStore.getImageList(Obstacle.OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseOre(String[] properties, WorldModel world,
                            ImageStore imageStore)
    {
        if (properties.length == Ore.ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Ore.ORE_COL]),
                    Integer.parseInt(properties[Ore.ORE_ROW]));
            Entity entity = new Ore(properties[Ore.ORE_ID],
                    pt, Integer.parseInt(properties[Ore.ORE_ACTION_PERIOD]),
                    imageStore.getImageList(Ore.ORE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Ore.ORE_NUM_PROPERTIES;
    }

    public static boolean parseSmith(String[] properties, WorldModel world,
                              ImageStore imageStore)
    {
        if (properties.length == Blacksmith.SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Blacksmith.SMITH_COL]),
                    Integer.parseInt(properties[Blacksmith.SMITH_ROW]));
            Entity entity = new Blacksmith(properties[Blacksmith.SMITH_ID],
                    pt, imageStore.getImageList(Blacksmith.SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Blacksmith.SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(String[] properties, WorldModel world,
                             ImageStore imageStore)
    {
        if (properties.length == Vein.VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Vein.VEIN_COL]),
                    Integer.parseInt(properties[Vein.VEIN_ROW]));
            Entity entity = new Vein(properties[Vein.VEIN_ID],
                    pt,
                    Integer.parseInt(properties[Vein.VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(Vein.VEIN_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Vein.VEIN_NUM_PROPERTIES;
    }
}