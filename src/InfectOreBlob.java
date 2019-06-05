import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class InfectOreBlob extends Infect
{
    public InfectOreBlob(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> parasiteOreBlobTarget = world.findNearest(this.position, OreBlob.class);
        long nextPeriod = this.actionPeriod;

        if (parasiteOreBlobTarget.isPresent())
        {
            Point tgtPos = parasiteOreBlobTarget.get().getPosition();

            if (this.moveTo(world, parasiteOreBlobTarget.get(), scheduler))
            {
                ParasiteOreBlob parasiteOreBlob = new ParasiteOreBlob(ParasiteOreBlob.PARASITE_BLOB_KEY, tgtPos, imageStore.getImageList(ParasiteOreBlob.PARASITE_BLOB_KEY),
                        4, 4);

                world.addEntity(parasiteOreBlob);
                nextPeriod += this.actionPeriod;
                parasiteOreBlob.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), nextPeriod);
    }


}
