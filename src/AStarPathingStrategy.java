import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();

        Comparator<Node> fComparator = Comparator.comparing(Node::getF).thenComparing(Node::getG);
        PriorityQueue<Node> openListPQ = new PriorityQueue<>(fComparator);
        HashMap<Point, Node> openListHM = new HashMap<>();

        HashSet<Point> closedList = new HashSet<>();

        Node startNode = new Node(start);
        startNode.setG(0);
        int hAndFStart = (Math.abs(end.x - start.x)) + (Math.abs(end.y - start.y));
        startNode.setH(hAndFStart);
        startNode.setF(hAndFStart);

        Node currentNode = startNode;
        openListPQ.add(currentNode);
        openListHM.put(currentNode.getPoint(), currentNode);



        while (!withinReach.test(currentNode.getPoint(), end)) {
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getPoint())
                                                     .filter(canPassThrough)
                                                     .filter(p -> !closedList.contains(p))
                                                     .collect(Collectors.toList());
            for (Point neighbor : neighbors) {
                if (!openListHM.containsKey(neighbor)) {
                    Node neighborNode = new Node(neighbor);
                    openListPQ.add(neighborNode);
                    openListHM.put(neighbor, neighborNode);
                }
                int g = currentNode.getG() + 1;
                if (openListHM.get(neighbor).getG() == -1 || openListHM.get(neighbor).getG() > g) {
                    openListPQ.remove(openListHM.get(neighbor));
                    openListHM.get(neighbor).setG(g);
                    int h = (Math.abs(end.x - neighbor.x)) + (Math.abs(end.y - neighbor.y));
                    openListHM.get(neighbor).setH(h);
                    int f = g + h;
                    openListHM.get(neighbor).setF(f);
                    openListHM.get(neighbor).setPriorNode(currentNode);
                    openListPQ.add(openListHM.get(neighbor));
                }
            }
            closedList.add(currentNode.getPoint());
            openListPQ.remove(currentNode);
            openListHM.remove(currentNode.getPoint());
            if (openListPQ.size() == 0) {
                return path;
            }
            currentNode = openListPQ.peek();
        }

        while (currentNode != startNode) {
            path.add(0, currentNode.getPoint());
            currentNode = currentNode.getPriorNode();
        }

        return path;
    }
}
