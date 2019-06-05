import processing.core.PImage;

import java.util.List;

public class Parasite extends InfectOreBlob
{

    public static final String PARASITE_KEY = "parasite";

    public Parasite(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, 0, actionPeriod, animationPeriod);
    }

}