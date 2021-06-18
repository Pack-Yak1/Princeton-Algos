/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.Arrays;

public class Percolation {
    private int[] fillGrid;
    private int[] percGrid;
    private int[] sizeGrid;
    private boolean[] openGrid;
    private int openSites;
    private int n;
    private int endIdx;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        endIdx = n * n + 2;
        fillGrid = new int[endIdx];
        for (int i = 0; i < endIdx; i++) {
            fillGrid[i] = i;
        }
        percGrid = new int[endIdx];
        for (int i = 0; i < endIdx; i++) {
            percGrid[i] = i;
        }
        sizeGrid = new int[endIdx];
        for (int i = 0; i < endIdx; i++) {
            sizeGrid[i] = 0;
        }
        openGrid = new boolean[endIdx];
        Arrays.fill(openGrid, false);
        openGrid[0] = true;
        openGrid[endIdx - 1] = true;
        openSites = 0;
        this.n = n;
    }

    private int root(int i, int[] grid) {
        while (i != grid[i]) {
            grid[i] = grid[grid[i]];
            i = grid[i];
        }
        return i;
    }

    private void union(int p, int q, int[] grid) {
        int idx1 = root(p, grid);
        int idx2 = root(q, grid);
        if (idx1 != idx2) {
            if (sizeGrid[idx1] < sizeGrid[idx2]) {
                grid[idx1] = idx2;
                sizeGrid[idx2] += sizeGrid[idx1];
            }
            else {
                grid[idx2] = idx1;
                sizeGrid[idx1] += sizeGrid[idx2];
            }
        }
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
                union(idx, upIdx, fillGrid);
                union(idx, upIdx, percGrid);
            }
            if (col != 1) {
                int leftIdx = indexOf(row, col - 1);
                if (openGrid[leftIdx]) {
                    union(idx, leftIdx, fillGrid);
                    union(idx, leftIdx, percGrid);
                }
            }
            if (col != n) {
                int rightIdx = indexOf(row, col + 1);
                if (openGrid[rightIdx]) {
                    union(idx, rightIdx, fillGrid);
                    union(idx, rightIdx, percGrid);
                }
            }
            int downIdx = row == n ? n * n + 1 : indexOf(row + 1, col);
            if (openGrid[downIdx]) {
                union(idx, downIdx, percGrid);
                if (row != n) {
                    union(idx, downIdx, fillGrid);
                }
            }
            openSites++;
            openGrid[idx] = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openGrid[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        argCheck(row, col);
        return root(0, fillGrid) == root(indexOf(row, col), fillGrid);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return root(0, percGrid) == root(endIdx - 1, percGrid);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
