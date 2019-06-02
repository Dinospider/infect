import processing.core.PImage;

import java.util.List;

public abstract class ShapeShiftingEntity extends DynamicEntity
{
    protected int animationPeriod;
    protected int repeatCount;

    ShapeShiftingEntity(String id,Point position, int actionPeriod, int animationPeriod, List<PImage> images, int imageIndex, int repeatCount)
    {
        super(id,position,actionPeriod,images, imageIndex);
        this.animationPeriod = animationPeriod;
        this.repeatCount = repeatCount;
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, new Animation(this, repeatCount), animationPeriod);
    }
    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }


}
