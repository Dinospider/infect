import processing.core.PImage;

import java.util.List;

public abstract class DynamicEntity extends Entity
{
    protected int actionPeriod;
    DynamicEntity(String id,Point position, int actionPeriod, List<PImage> images, int imageIndex)
    {
        super(id,position,images, imageIndex);
        this.actionPeriod = actionPeriod;
    }
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
    }
}
