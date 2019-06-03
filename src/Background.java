import processing.core.PImage;

import java.util.List;

final class Background
{

   public static final String BGND_KEY = "background";
   public static final int BGND_NUM_PROPERTIES = 4;
   public static final int BGND_ID = 1;
   public static final int BGND_COL = 2;
   public static final int BGND_ROW = 3;

   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public List<PImage> getImages() {
      return this.images;
   }

   public int getImageIndex() {
      return this.imageIndex;
   }

   public PImage getCurrentImage()
   {
      return this.getImages().get(this.getImageIndex());
   }

   public String getId() {
      return this.id;
   }
}
