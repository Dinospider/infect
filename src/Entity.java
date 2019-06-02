import java.util.List;
import processing.core.PImage;

public abstract class Entity
{
   //private EntityKind kind;
   protected String id;
   protected Point position;
   protected List<PImage> images;
   protected int imageIndex;
//   private int resourceLimit;
//   private int resourceCount;
//   private int actionPeriod;
//   private int animationPeriod;

   public Point getPosition()
   {
      return position;
   }

   public void setPosition(Point position)
   {
      this.position = position;
   }

   public List<PImage> getImages()
   {
      return images;
   }

   public PImage getCurrentImage()
   {
         return (images.get(imageIndex));
   }

   Entity(String id, Point position, List<PImage> images, int imageIndex)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
   }

}

