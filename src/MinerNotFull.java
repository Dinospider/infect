import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends MinerEntity {

    public MinerNotFull(String id, Point position,
                     List<PImage> images, int resourceLimit,
                     int actionPeriod, int animationPeriod)
    {
        super(id, position, images, 0, actionPeriod, animationPeriod, resourceLimit, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {

        Optional<Entity> notFullTarget = world.findNearest(this.position, Ore.class);

        if(this.transformZombie(world, imageStore, scheduler))
        {

        }
        else if (!notFullTarget.isPresent() || !this.moveTo(world, notFullTarget.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore))
        {

            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }

    }


    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            MinerFull miner = new MinerFull(this.id, this.position, this.images, this.resourceLimit, this.resourceLimit,
                     this.actionPeriod, this.animationPeriod);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {

        if (this.position.adjacent(target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


}
