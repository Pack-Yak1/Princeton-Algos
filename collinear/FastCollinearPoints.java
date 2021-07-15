/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int TARGET = 4;
    private int seg;
    private final ArrayList<LineSegment> ls = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] storage = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            storage[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            // System.out.println("Constructor adding point: " + p);
            Arrays.sort(storage, p.slopeOrder());
            if (storage.length > 1 && p.slopeTo(storage[1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            process(storage, p);
        }
    }

    // private void pp(Point[] arr, int startIdx, int endIdx) {
    //     System.out.print("[");
    //     for (int i = startIdx; i < endIdx; i++) {
    //         System.out.print(arr[i]);
    //         if (i != endIdx - 1) {
    //             System.out.print(", ");
    //         }
    //     }
    //     System.out.println("]");
    // }

    /**
     * Add LineSegments of Points from [storage] iff they contain 4 collinear points, and [origin]
     * is the maximal point according to [Point.compareTo()].
     *
     * @param storage A sorted array of Points using [origin.slopeOrder()] as the comparator
     * @param origin  The reference Point by which [storage] was sorted
     */
    private void process(Point[] storage, Point origin) {
        // System.out.printf("Origin: %s\n", origin);
        // pp(storage);
        if (storage.length < TARGET) {
            return;
        }
        int currentStreak = 1;
        int startIdx = 0;
        double current = origin.slopeTo(storage[0]);
        for (int i = 1; i < storage.length; i++) {
            Point p = storage[i];
            double slope = origin.slopeTo(p);
            // System.out.println("From point " + origin + ", the slope to " + p + " is " + slope);
            if (slope == current) {
                currentStreak++;
            }
            else {
                // Only require streak of 3 because origin is the 4th Point
                if (currentStreak >= TARGET - 1) {
                    processHelper(storage, startIdx, i, origin);
                }
                currentStreak = 1;
                startIdx = i;
                current = slope;
            }
        }
        if (currentStreak >= TARGET - 1) {
            processHelper(storage, startIdx, storage.length, origin);
        }
    }

    /**
     * Add the maximal line segment from [arr[start]] to [arr[end - 1]]
     * (inclusive of [p]), to [ls], if [p] is the maximal point.
     * Requires: All Points from [arr[start]] to [arr[end - 1]] are collinear
     *
     * @param arr   The array of points in the Collinear program
     * @param start The index of [arr] to begin checking from
     * @param end   The first index of [arr] not to be checked
     * @param p     The reference point from which the Points in [arr] are ordered
     */
    private void processHelper(Point[] arr, int start, int end, Point p) {
        Point min = p;
        Point max = p;
        // pp(arr, start, end);
        for (int i = start; i < end; i++) {
            if (arr[i].compareTo(min) < 0) {
                min = arr[i];
            }
            if (arr[i].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        if (max.compareTo(p) == 0) {
            seg++;
            ls.add(new LineSegment(min, max));
            // System.out.println("LineSegment added with origin: " + p);
        }
    }

    public int numberOfSegments() {
        return seg;
    }

    public LineSegment[] segments() {
        int sz = ls.size();
        LineSegment[] output = new LineSegment[sz];
        for (int i = 0; i < sz; i++) {
            output[i] = ls.get(i);
        }
        return output;
    }
}
