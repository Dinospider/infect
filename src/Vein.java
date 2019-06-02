import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Vein extends DynamicEntity
{

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;


    Vein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id,position,actionPeriod,images, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(position);

        if (openPt.isPresent())
        {
            Ore ore = new Ore(Ore.ORE_ID_PREFIX + this.id,
                    openPt.get(), Ore.ORE_CORRUPT_MIN +
                            Ore.rand.nextInt(Ore.ORE_CORRUPT_MAX - Ore.ORE_CORRUPT_MIN),
                    imageStore.getImageList(Ore.ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.actionPeriod);

        //above is same as scheduleActions
    }

}
