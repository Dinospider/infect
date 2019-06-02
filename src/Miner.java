import processing.core.PImage;

import java.util.List;

public abstract class Miner extends MovingEntity
{
    protected int resourceLimit;
    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    Miner(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, int imageIndex, int resourceLimit)
    {
        super(id,position,actionPeriod, animationPeriod, images, imageIndex);
        this.resourceLimit = resourceLimit;
    }

}
