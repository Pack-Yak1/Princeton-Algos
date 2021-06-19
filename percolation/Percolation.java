/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF fillGrid;
    private final WeightedQuickUnionUF percGrid;
    private final boolean[] openGrid;
    private int openSites;
    private final int n;
    private final int endIdx;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        endIdx = n * n + 2;
        fillGrid = new WeightedQuickUnionUF(endIdx);
        percGrid = new WeightedQuickUnionUF(endIdx);
        openGrid = new boolean[endIdx];
        for (int i = 0; i < endIdx; i++) {
            openGrid[i] = false;
        }
        openGrid[0] = true;
        openGrid[endIdx - 1] = true;
        openSites = 0;
        this.n = n;
    }

    private int indexOf(int row, int col) {
        return 1 + n * (row - 1) + col - 1;
    }

    private void argCheck(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        argCheck(row, col);
        if (!isOpen(row, col)) {
            int idx = indexOf(row, col);
            int upIdx = row == 1 ? 0 : indexOf(row - 1, col);
            if (openGrid[upIdx]) {
                fillGrid.union(idx, upIdx);
                percGrid.union(idx, upIdx);
            }
            if (col != 1) {
                int leftIdx = indexOf(row, col - 1);
                if (openGrid[leftIdx]) {
                    fillGrid.union(idx, leftIdx);
                    percGrid.union(idx, leftIdx);
                }
            }
            if (col != n) {
                int rightIdx = indexOf(row, col + 1);
                if (openGrid[rightIdx]) {
                    fillGrid.union(idx, rightIdx);
                    percGrid.union(idx, rightIdx);
                }
            }
            int downIdx = row == n ? n * n + 1 : indexOf(row + 1, col);
            if (openGrid[downIdx]) {
                percGrid.union(idx, downIdx);
                if (row != n) {
                    fillGrid.union(idx, downIdx);
                }
            }
            openSites++;
            openGrid[idx] = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        argCheck(row, col);
        return openGrid[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        argCheck(row, col);
        return fillGrid.find(0) == fillGrid.find(indexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percGrid.find(0) == percGrid.find(endIdx - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        //    Chose not to implement
    }
}
