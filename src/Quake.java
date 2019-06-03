import processing.core.PImage;

import java.util.List;

public class Quake extends AnimationEntity {

    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;


    public Quake(Point position, List<PImage> images)
    {
        super(QUAKE_ID, position, images, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

    }


    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this, new Animation(this, Quake.QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

}
