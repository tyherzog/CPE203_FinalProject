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
        Point currentNode = start;

        HashMap openPath = new HashMap();
        HashMap closedPath = new HashMap();
        PriorityQueue openPathBestF = new PriorityQueue();
        List<Point> finalPath = new LinkedList<>();

        currentNode.calculateG(currentNode, end);
        openPath.put(currentNode.hashCode(), currentNode);

        List<Point> NeighboringPoints = potentialNeighbors.apply(currentNode)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)
                                && !closedPath.containsKey(pt.hashCode()))
                .collect(Collectors.toList());

        while(!neighbors(currentNode, end) && openPath.size() != 0) {
            for (Point neighbor : NeighboringPoints) {
                if (!openPath.containsKey(neighbor.hashCode()))
                    openPath.put(neighbor.hashCode(), neighbor);
                neighbor.calculateG(currentNode, end);
                if(!openPathBestF.contains(neighbor))
                    openPathBestF.add(neighbor);
            }

            closedPath.put(currentNode.hashCode(), currentNode);
            openPath.remove(currentNode.hashCode(), currentNode);

            if(openPathBestF.size() != 0)
                currentNode = (Point)openPathBestF.poll();
            else {
                break;
            }

            NeighboringPoints = potentialNeighbors.apply(currentNode)
                    .filter(canPassThrough)
                    .filter(pt ->
                            !pt.equals(start)
                                    && !pt.equals(end)
                                    && !closedPath.containsKey(pt.hashCode()))
                    .collect(Collectors.toList());
        }

        if(neighbors(currentNode, end)) {
            while (currentNode != start) {
                finalPath.add(0, currentNode);
                currentNode = currentNode.getPrev();
            }
        }
        return finalPath;
    }

    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }
}
