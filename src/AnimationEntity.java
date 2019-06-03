import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActionEntity {

    protected int imageIndex;
    protected int animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.imageIndex = imageIndex;
        this.animationPeriod = animationPeriod;
    }

    public int getImageIndex() {
        return this.imageIndex;
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

}
