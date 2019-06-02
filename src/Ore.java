import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore extends DynamicEntity
{
    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final int ORE_REACH = 1;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final Random rand = new Random();

    Ore(String id,Point position, int actionPeriod, List<PImage> images)
    {
        super(id,position,actionPeriod,images, 0);
    }

    public void executeActivity(WorldModel world,
                                   ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = position;
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(id + OreBlob.BLOB_ID_SUFFIX, pos,
                actionPeriod / OreBlob.BLOB_PERIOD_SCALE,
                OreBlob.BLOB_ANIMATION_MIN +
                        rand.nextInt(OreBlob.BLOB_ANIMATION_MAX - OreBlob.BLOB_ANIMATION_MIN),
                imageStore.getImageList(OreBlob.BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}
