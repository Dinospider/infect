import processing.core.PApplet;
import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreateGame {

    public static final int COLOR_MASK = 0xffffff;
    public static final int KEYED_IMAGE_MIN = 5;
    public static final int KEYED_RED_IDX = 2;
    public static final int KEYED_GREEN_IDX = 3;
    public static final int KEYED_BLUE_IDX = 4;

    public static final int PROPERTY_KEY = 0;

    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen)
    {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2)
        {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1)
            {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN)
                {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    public static List<PImage> getImages(Map<String, List<PImage>> images, String key)
    {
        List<PImage> imgs = images.get(key);
        if (imgs == null)
        {
            imgs = new LinkedList<>();
            images.put(key, imgs);
        }
        return imgs;
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    public static void setAlpha(PImage img, int maskColor, int alpha)
    {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
            {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

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
                case MinerEntity.MINER_KEY:
                    return parseMiner(properties, world, imageStore);
                case Obstacle.OBSTACLE_KEY:
                    return parseObstacle(properties, world, imageStore);
                case Ore.ORE_KEY:
                    return parseOre(properties, world, imageStore);
                case Blacksmith.SMITH_KEY:
                    return parseSmith(properties, world, imageStore);
                case Vein.VEIN_KEY:
                    return parseVein(properties, world, imageStore);
            }
        }

        return false;
    }

    public static boolean parseBackground(String [] properties, WorldModel world, ImageStore imageStore)
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

    public static boolean parseMiner(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == MinerEntity.MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MinerEntity.MINER_COL]),
                    Integer.parseInt(properties[MinerEntity.MINER_ROW]));
            MinerNotFull entity = new MinerNotFull(properties[MinerEntity.MINER_ID],
                    pt,
                    imageStore.getImageList(MinerEntity.MINER_KEY),
                    Integer.parseInt(properties[MinerEntity.MINER_LIMIT]),
                    Integer.parseInt(properties[MinerEntity.MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MinerEntity.MINER_ANIMATION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == MinerEntity.MINER_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[Obstacle.OBSTACLE_COL]),
                    Integer.parseInt(properties[Obstacle.OBSTACLE_ROW]));
            Obstacle entity = new Obstacle(properties[Obstacle.OBSTACLE_ID],
                    pt, imageStore.getImageList(Obstacle.OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseOre(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Ore.ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Ore.ORE_COL]),
                    Integer.parseInt(properties[Ore.ORE_ROW]));
            Ore entity = new Ore(properties[Ore.ORE_ID],
                    pt, imageStore.getImageList(Ore.ORE_KEY), Integer.parseInt(properties[Ore.ORE_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == Ore.ORE_NUM_PROPERTIES;
    }

    public static boolean parseSmith(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Blacksmith.SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Blacksmith.SMITH_COL]),
                    Integer.parseInt(properties[Blacksmith.SMITH_ROW]));
            Blacksmith entity = new Blacksmith(properties[Blacksmith.SMITH_ID],
                    pt, imageStore.getImageList(Blacksmith.SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Blacksmith.SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Vein.VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Vein.VEIN_COL]),
                    Integer.parseInt(properties[Vein.VEIN_ROW]));
            Vein entity = new Vein(properties[Vein.VEIN_ID],
                    pt, imageStore.getImageList(Vein.VEIN_KEY),
                    Integer.parseInt(properties[Vein.VEIN_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == Vein.VEIN_NUM_PROPERTIES;
    }


}
