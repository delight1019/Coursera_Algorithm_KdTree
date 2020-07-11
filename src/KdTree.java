import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;

public class KdTree {
    private final int HORIZONTAL = 1;
    private final int VERTICAL = 2;

    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private Node left, right;
        private int direction;

        public Node(Point2D point, int direction) {
            this.point = point;
            this.direction = direction;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (root == null);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        root = insert(root, p, VERTICAL);
    }

    private Node insert(Node node, Point2D p, int direction) {
        if (node == null) {
            size++;
            return new Node(p, direction);
        }

        if (node.point.equals(p)) {
            return node;
        }

        if (direction == VERTICAL) {
            if (p.x() < node.point.x()) {
                node.left = insert(node.left, p, HORIZONTAL);
            }
            else {
                node.right = insert(node.right, p, HORIZONTAL);
            }
        }
        else if (direction == HORIZONTAL) {
            if (p.y() < node.point.y()) {
                node.left = insert(node.left, p, VERTICAL);
            }
            else {
                node.right = insert(node.right, p, VERTICAL);
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null){
            throw new IllegalArgumentException();
        }

        Node current = root;

        while (current != null) {
            if (current.point.equals(p)) {
                return true;
            }

            if (current.direction == VERTICAL) {
                if (p.x() < current.point.x()) {
                    current = current.left;
                }
                else {
                    current = current.right;
                }
            }
            else if (current.direction == HORIZONTAL) {
                if (p.y() < current.point.y()) {
                    current = current.left;
                }
                else {
                    current = current.right;
                }
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, null);
    }

    private void draw(Node node, Node parent) {
        if (node == null) {
            return;
        }

        if (node.direction == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);

            if (parent == null) {
                node.point.drawTo(new Point2D(node.point.x(), 0));
                node.point.drawTo(new Point2D(node.point.x(), 1));
            }
            else if (node.point.y() < parent.point.y()) {
                node.point.drawTo(new Point2D(node.point.x(), 0));
                node.point.drawTo(new Point2D(node.point.x(), parent.point.y()));
            }
            else {
                node.point.drawTo(new Point2D(node.point.x(), parent.point.y()));
                node.point.drawTo(new Point2D(node.point.x(), 1));
            }
        }
        else if (node.direction == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);

            if (parent == null) {
                node.point.drawTo(new Point2D(0, node.point.y()));
                node.point.drawTo(new Point2D(1, node.point.y()));
            }
            else if (node.point.x() < parent.point.x()) {
                node.point.drawTo(new Point2D(0, node.point.y()));
                node.point.drawTo(new Point2D(parent.point.x(), node.point.y()));
            }
            else {
                node.point.drawTo(new Point2D(parent.point.x(), node.point.y()));
                node.point.drawTo(new Point2D(1, node.point.y()));
            }
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        draw(node.left, node);
        draw(node.right, node);
    }

    private void range(RectHV rect, Node node, SET<Point2D> treeSet) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            treeSet.add(node.point);
        }

        range(rect, node.left, treeSet);
        range(rect, node.right, treeSet);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> treeSet = new SET<>();
        range(rect, root, treeSet);

        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return treeSet.iterator();
            }
        };
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return nearest(p, root.point, root);
    }

    private Point2D nearest(Point2D target, Point2D nearest, Node node) {
        if (node == null) {
            return nearest;
        }

        if (target.distanceSquaredTo(node.point) < target.distanceSquaredTo(nearest)) {
            nearest = node.point;
        }

        if (node.direction == VERTICAL) {
            if (target.x() < node.point.x()) {
                nearest = nearest(target, nearest, node.left);
                nearest = nearest(target, nearest, node.right);
            }
            else {
                nearest = nearest(target, nearest, node.right);
                nearest = nearest(target, nearest, node.left);
            }
        }
        else if (node.direction == HORIZONTAL) {
            if (target.y() < node.point.y()) {
                nearest = nearest(target, nearest, node.left);
                nearest = nearest(target, nearest, node.right);
            }
            else {
                nearest = nearest(target, nearest, node.right);
                nearest = nearest(target, nearest, node.left);
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
