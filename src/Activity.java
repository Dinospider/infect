public class Activity implements Action {

    private ActionEntity entity;
    private WorldModel world;
    private ImageStore imageStore;


    public Activity(ActionEntity entity, WorldModel world,
                    ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;

    }

    public void executeAction(EventScheduler scheduler)
    {
        this.entity.executeActivity(this.world, this.imageStore, scheduler);
//        } else {
//            throw new UnsupportedOperationException(
//                    String.format("executeActivityAction not supported for %s",
//                            this.entity.getClass()));
//        }
    }

//    public static Action createActivityAction(Entity entity, WorldModel world,
//                                              ImageStore imageStore)
//    {
//        return new Activity(entity, world, imageStore);
//    }


}
