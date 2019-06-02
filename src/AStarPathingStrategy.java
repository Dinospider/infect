import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{
//PAC1245R

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(Node::getF).thenComparing(Node::getG));
        HashMap<Point, Node> closedList = new HashMap();

        openList.offer(new Node(start, start.heuristicDistance(end),null));
        Node atNode = openList.poll();
        while(!withinReach.test(atNode.getPos(),end))
        {
            final Node nop = atNode;
            potentialNeighbors.apply(atNode.getPos()).filter(canPassThrough).filter(pt -> !closedList.containsKey(pt)).forEach(n ->
            {
                Node neighbor = new Node(n, n.heuristicDistance(end), nop);
                if(openList.contains(neighbor))
                {
                    openList.remove(neighbor);
                }
                openList.offer(neighbor);
            });

            closedList.put(atNode.getPos(), atNode);
            if(openList.isEmpty())
            {
                return path;
            }
            atNode = openList.poll();
        }

        while(atNode.getPrev() != null)
        {
            path.add(0, atNode.getPos());
            atNode = atNode.getPrev();
        }

        return path;
    }
}
