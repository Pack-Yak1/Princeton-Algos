/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Percolation {
    private int[] fillGrid;
    private int[] percGrid;
    private int[] sizeGrid;
    private int openSites;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        int size = n * n + 2;
        fillGrid = new int[size];
        for (int i = 0; i < size; i++) {
            fillGrid[i] = i;
        }
        percGrid = new int[size];
        for (int i = 0; i < size; i++) {
            percGrid[i] = i;
        }
        sizeGrid = new int[size];
        for (int i = 0; i < size; i++) {
            sizeGrid[i] = 0;
        }
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

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = indexOf(row, col);
        int upIdx = row == 1 ? 0 : indexOf(row - 1, col);
        union(idx, upIdx, fillGrid);
        union(idx, upIdx, percGrid);
        if (col != 1) {
            int leftIdx = indexOf(row, col - 1);
            union(idx, leftIdx, fillGrid);
            union(idx, leftIdx, percGrid);
        }
        if (col != n) {
            int rightIdx = indexOf(row, col + 1);
            union(idx, rightIdx, fillGrid);
            union(idx, rightIdx, percGrid);
        }
        int downIdx = row == n ? n * n + 1 : indexOf(row + 1, col);
        union(idx, downIdx, percGrid);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
