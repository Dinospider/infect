import processing.core.PImage;

import java.util.List;

public abstract class MinerEntity extends MoveEntity {
    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    protected int resourceLimit;
    protected int resourceCount;

    public MinerEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public boolean transformZombie(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        if (world.getBackgroundCell(this.position).getId().equals("background_toxic")) {
            Point newPos = this.position;
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            ZombieMiner zombieMiner = new ZombieMiner(ZombieMiner.ZOMBIE_MINER_KEY, newPos,
                    imageStore.getImageList(ZombieMiner.ZOMBIE_MINER_KEY),
                    500, 4);

            world.addEntity(zombieMiner);
            zombieMiner.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;

    }

}
