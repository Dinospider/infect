import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class ZombieMiner extends Infect
{

    public static final String ZOMBIE_MINER_KEY = "zombie_miner";

    public ZombieMiner(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, 0, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> minerFullTarget = world.findNearest(this.position, MinerFull.class);
        long nextPeriod = this.actionPeriod;

        if (minerFullTarget.isPresent())
        {
            Point tgtPos = minerFullTarget.get().getPosition();

            if (this.moveTo(world, minerFullTarget.get(), scheduler))
            {
                ZombieMiner zombieMiner = new ZombieMiner(ZombieMiner.ZOMBIE_MINER_KEY, tgtPos,
                        imageStore.getImageList(ZombieMiner.ZOMBIE_MINER_KEY),
                        500, 4);

                world.addEntity(zombieMiner);
                nextPeriod += this.actionPeriod;
                zombieMiner.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), nextPeriod);
    }


}
