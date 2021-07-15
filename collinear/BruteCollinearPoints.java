/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {
    private static final int TARGET = 4;
    private int seg;
    private final ArrayList<LineSegment> ls = new ArrayList<>();

    public BruteCollinearPoints(Point[] point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        int len = point.length;
        Point[] arr = new Point[TARGET];
        for (int i = 0; i < len; i++) {
            if (!validityCheck(point[i], null)) {
                throw new IllegalArgumentException();
            }
            arr[0] = point[i];
            for (int j = i + 1; j < len; j++) {
                if (!validityCheck(point[j], arr[0])) {
                    throw new IllegalArgumentException();
                }
                arr[1] = point[j];
                double grad = arr[1].slopeTo(arr[0]);
                for (int k = j + 1; k < len; k++) {
                    if (!validityCheck(point[k], arr[1])) {
                        throw new IllegalArgumentException();
                    }
                    arr[2] = point[k];
                    if (arr[2].slopeTo(arr[1]) != grad) {
                        continue;
                    }
                    for (int m = k + 1; m < len; m++) {
                        if (!validityCheck(point[m], arr[2])) {
                            throw new IllegalArgumentException();
                        }
                        arr[3] = point[m];
                        if (arr[3].slopeTo(arr[2]) == grad) {
                            seg++;
                            helper(arr);
                        }
                    }
                }
            }
        }
    }

    private void helper(Point[] arr) {
        Point min = arr[0];
        Point max = arr[0];
        for (int n = 1; n < TARGET; n++) {
            if (arr[n].compareTo(min) < 0) {
                min = arr[n];
            }
            if (arr[n].compareTo(max) > 0) {
                max = arr[n];
            }
        }
        ls.add(new LineSegment(min, max));
    }

    private boolean validityCheck(Point next, Point prev) {
        if (next == null) {
            return false;
        }
        if (prev != null) {
            return prev.slopeTo(next) != Double.NEGATIVE_INFINITY;
        }
        return true;
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
