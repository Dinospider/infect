import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerFull extends MinerEntity {


    public MinerFull(String id, Point position,
                   List<PImage> images, int resourceLimit, int resourceCount,
                   int actionPeriod, int animationPeriod)
    {
        super(id, position, images, 0, actionPeriod, animationPeriod, resourceLimit, resourceCount);
    }


    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position, Blacksmith.class);

        if (this.moveTo(world, fullTarget.get(), scheduler)) {
            if (world.getBackgroundCell(this.position).getId().equals("background_toxic")) {
                transformZombie(world, imageStore, scheduler);
            }
            if (fullTarget.isPresent()) {
                {
                    this.transformFull(world, scheduler, imageStore);
                }
            }
        }
        else
        {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        }
    }


    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = new MinerNotFull(this.id, this.position, this.images, this.resourceLimit,
                                              this.actionPeriod, this.animationPeriod);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }



    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
//            List<Point> path = strategy.computePath(this.position, target.position,
//                    p ->  !world.isOccupied(p),
//                    (p1, p2) -> p1.adjacent(p2),
//                    PathingStrategy.CARDINAL_NEIGHBORS);
//            if (path.size() == 0) {
//                return false;
//            }

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

    public void transformZombie(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {

        Point newPos = this.position;
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        ZombieMiner zombieMiner = new ZombieMiner(ZombieMiner.ZOMBIE_MINER_KEY, newPos, imageStore.getImageList(ZombieMiner.ZOMBIE_MINER_KEY),
                4, 4);
//        long nextPeriod = this.actionPeriod;

        world.addEntity(zombieMiner);
//        nextPeriod += this.actionPeriod;
        zombieMiner.scheduleActions(scheduler, world, imageStore);

    }

}
