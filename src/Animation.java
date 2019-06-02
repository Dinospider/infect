public class Animation implements Action
{
    private ShapeShiftingEntity entity;
    private int repeatCount;

    Animation(ShapeShiftingEntity entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(entity,
                    new Animation(entity,
                            Math.max(repeatCount - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }

}
