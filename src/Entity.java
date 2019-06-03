import processing.core.PImage;

import java.util.List;

public abstract class Entity
{
    protected String id;
    protected Point position;
    protected List<PImage> images;

    public Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
    }

    public List<PImage> getImages() {
        return this.images;
    }

    public int getImageIndex() {
        return 0;
    }

    public Point getPosition() {
        return this.position;
    }

    public PImage getCurrentImage()
    {
        return this.getImages().get(this.getImageIndex());
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
