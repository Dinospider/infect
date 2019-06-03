import processing.core.PImage;

import java.util.List;
import java.util.Random;

public abstract class ActionEntity extends Entity {

    public static final Random rand = new Random();

    protected int actionPeriod;

    public ActionEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);

    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
