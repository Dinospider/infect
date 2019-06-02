interface Action
{
//   private ActionKind kind;

//   public Action(ActionKind kind, Entity entity, WorldModel world,
//      ImageStore imageStore, int repeatCount)
//   {
//      this.kind = kind;
//      this.entity = entity;
//      this.world = world;
//      this.imageStore = imageStore;
//      this.repeatCount = repeatCount;
//   }
   void executeAction(EventScheduler scheduler);

}