/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private int zeroRow;
    private int zeroCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int digit = tiles[i][j];
                this.tiles[i][j] = digit;
                if (digit == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    public Board(Board b) {
        int n = b.dimension();
        int[][] outputTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                outputTiles[i][j] = b.tiles[i][j];
            }
        }
        tiles = outputTiles;
        zeroRow = b.zeroRow;
        zeroCol = b.zeroCol;
    }

    // string representation of this board
    public String toString() {
        int n = dimension();
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        for (int i = 0; i < n; i++) {
            sb.append('\n');
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j]);
                if (j != n - 1) {
                    sb.append(' ');
                }
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int d = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int position = (1 + (n * i) + j) % (n * n);
                if (tiles[i][j] != position) {
                    d++;
                }
            }
        }
        return d;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                int row = (tile - 1) / n;
                int col = (tile - 1) % n;
                sum += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || !(y.getClass() == Board.class)) {
            return false;
        }
        Board b = (Board) y;
        int n = dimension();
        if (n != b.dimension()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Swaps the values in positions (i1, j1) and
    private void swap(int i1, int j1, int i2, int j2) {
        tiles[i1][j1] = tiles[i1][j1] + tiles[i2][j2];
        tiles[i2][j2] = tiles[i1][j1] - tiles[i2][j2];
        tiles[i1][j1] = tiles[i1][j1] - tiles[i2][j2];
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int n = dimension();
        ArrayList<Board> output = new ArrayList<>();
        if (zeroRow > 0) {
            Board up = new Board(this);
            up.swap(zeroRow, zeroCol, zeroRow - 1, zeroCol);
            output.add(up);
        }
        if (zeroCol > 0) {
            Board left = new Board(this);
            left.swap(zeroRow, zeroCol, zeroRow, zeroCol - 1);
            output.add(left);
        }
        if (zeroRow < n - 1) {
            Board down = new Board(this);
            down.swap(zeroRow, zeroCol, zeroRow + 1, zeroCol);
            output.add(down);
        }
        if (zeroCol < n - 1) {
            Board right = new Board(this);
            right.swap(zeroRow, zeroCol, zeroRow, zeroCol + 1);
            output.add(right);
        }
        return output;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board output = new Board(this);
        int n = output.dimension();
        //    Pick 2 random positions. If first position is 0, shift left/right.
        //    If second position is 0, shift up/down.
        int i1 = StdRandom.uniform(0, n);
        int j1 = StdRandom.uniform(0, n);
        int i2 = StdRandom.uniform(0, n);
        int j2 = StdRandom.uniform(0, n);
        if (i1 == zeroRow && j1 == zeroCol) {
            j1 = j1 > 0 ? j1 - 1 : j1 + 1;
        }
        if (i2 == zeroRow && j2 == zeroCol) {
            j2 = j2 > 0 ? j2 - 1 : j2 + 1;
        }
        output.swap(i1, j2, i2, j2);
        return output;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
