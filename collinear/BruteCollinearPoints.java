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
            arr[0] = point[i];
            if (!validityCheck(arr[0], null)) {
                throw new IllegalArgumentException();
            }
            for (int j = i; j < len; j++) {
                arr[1] = point[j];
                double grad = arr[1].slopeTo(arr[0]);
                if (!validityCheck(arr[1], arr[0])) {
                    throw new IllegalArgumentException();
                }
                for (int k = j; k < len; k++) {
                    arr[2] = point[k];
                    if (validityCheck(arr[2], arr[1])) {
                        throw new IllegalArgumentException();
                    }
                    if (arr[2].slopeTo(arr[1]) != grad) {
                        continue;
                    }
                    for (int m = k; m < len; m++) {
                        arr[3] = point[k];
                        if (validityCheck(arr[3], arr[2])) {
                            throw new IllegalArgumentException();
                        }
                        if (arr[3].slopeTo(arr[2]) == grad) {
                            seg++;
                            Point min, max;
                            min = arr[0];
                            max = arr[0];
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
                    }
                }
            }
        }
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
        return (LineSegment[]) ls.toArray();
    }
}
