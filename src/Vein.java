import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Vein extends ActionEntity {

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;


    public Vein(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);


    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Ore ore = new Ore(Ore.ORE_ID_PREFIX + this.id,
                    openPt.get(), imageStore.getImageList(Ore.ORE_KEY), Ore.ORE_CORRUPT_MIN +
                            ActionEntity.rand.nextInt(Ore.ORE_CORRUPT_MAX - Ore.ORE_CORRUPT_MIN));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
    }

}
