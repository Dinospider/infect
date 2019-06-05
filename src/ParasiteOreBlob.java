import processing.core.PImage;

import java.util.List;

public class ParasiteOreBlob extends InfectOreBlob
{

    public static final String PARASITE_BLOB_KEY = "parasite_blob";

    public ParasiteOreBlob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, 0, actionPeriod, animationPeriod);
    }


}
