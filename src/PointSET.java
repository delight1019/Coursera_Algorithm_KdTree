import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> treeSet;

    // construct an empty set of points
    public PointSET() {
        treeSet = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return treeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        treeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null){
            throw new IllegalArgumentException();
        }

        return treeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : treeSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> rectTreeSet = new SET<>();

        for (Point2D point : treeSet) {
            if (rect.contains(point)) {
                rectTreeSet.add(point);
            }
        }

        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return rectTreeSet.iterator();
            }
        };
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D nearestPoint = null;

        for (Point2D point : treeSet) {
            if (nearestPoint == null) {
                nearestPoint = point;
            }

            if (point.distanceTo(p) < nearestPoint.distanceTo(p)) {
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
