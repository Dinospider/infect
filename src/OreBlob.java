import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OreBlob extends MoveEntity {

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;



    public OreBlob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, 0, actionPeriod, animationPeriod);
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(this.position, Vein.class);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(Quake.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), nextPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
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

    public Point nextPosition(WorldModel world, Point destPos)
    {

        List<Point> path = strategy.computePath(this.position, destPos,
                p ->  !world.isOccupied(p) && world.withinBounds(p),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size() == 0) {
            return this.position;
        }

        Point nextPos = path.get(0);
        return nextPos;

//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz,
//                this.position.y);
//
//        Optional<Entity> occupant = world.getOccupant(newPos);
//
//        if (horiz == 0 ||
//                (occupant.isPresent() && !(occupant.get().equals(Ore.class)))) //.getClass()??
//        {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//            occupant = world.getOccupant(newPos);
//
//            if (vert == 0 ||
//                    (occupant.isPresent() && !(occupant.get().equals(Ore.class))))
//            {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
    }

}
