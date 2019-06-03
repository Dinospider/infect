import processing.core.PImage;

import java.util.List;

public class Ore extends ActionEntity {

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    public static final int ORE_REACH = 1;


    public Ore(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(this.id + OreBlob.BLOB_ID_SUFFIX,
                pos, imageStore.getImageList(OreBlob.BLOB_KEY), this.actionPeriod / OreBlob.BLOB_PERIOD_SCALE,
                OreBlob.BLOB_ANIMATION_MIN + ActionEntity.rand.nextInt(OreBlob.BLOB_ANIMATION_MAX - OreBlob.BLOB_ANIMATION_MIN));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

}
