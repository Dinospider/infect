import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class MovingEntity extends ShapeShiftingEntity
{
    protected PathingStrategy pathingStrategy;
    MovingEntity(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, int imageIndex)
    {
        super(id,position,actionPeriod, animationPeriod, images, imageIndex, 0);
        this.pathingStrategy = new AStarPathingStrategy();
//        this.pathingStrategy = new SingleStepPathingStrategy();

    }
    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public Point nextPosition(WorldModel world, Point destPos)
    {

        Predicate<Point> canPassThrough = n -> !world.isOccupied(n);
        BiPredicate<Point, Point> withinReach =  (n1,n2) -> n1.adjacent(n2);
        List<Point> p = pathingStrategy.computePath(position, destPos, canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
        if(p.size()>0)
        {
            return p.get(0);
        }
        else
        {
            return position;
        }
//        int horiz = Integer.signum(destPos.getX() - position.getX());
//        Point newPos = new Point(position.getX() + horiz,
//                position.getY());
//
//        if (horiz == 0 || world.isOccupied(newPos))
//        {
//            int vert = Integer.signum(destPos.getY() - position.getY());
//            newPos = new Point(position.getX(),
//                    position.getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos))
//            {
//                newPos = position;
//            }
//        }
//
//        return newPos;
    }

}
