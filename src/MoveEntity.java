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

    public abstract Point nextPosition(WorldModel world, Point destPos);

}
