import processing.core.PImage;

import java.util.List;

public abstract class MoveEntity extends AnimationEntity{

    //protected PathingStrategy strategy = new SingleStepPathingStrategy();
    protected PathingStrategy strategy = new AStarPathingStrategy();

    public MoveEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());

    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

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
    }

}
