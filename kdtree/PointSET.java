/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> s;

    public PointSET() {
        s = new TreeSet<>();
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        s.add(p);
    }

    boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return s.contains(p);
    }

    void draw() {
        for (Point2D p : s) {
            p.draw();
        }
    }

    Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> output = new ArrayList<>();
        for (Point2D p : s) {
            if (rect.contains(p)) {
                output.add(p);
            }
        }
        return output;
    }

    Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D best = null;
        double score = Double.POSITIVE_INFINITY;
        for (Point2D p2 : s) {
            double d = p.distanceTo(p2);
            if (score > d) {
                best = p2;
                score = d;
            }
        }
        return best;
    }
}
