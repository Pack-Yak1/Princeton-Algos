/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private boolean solvable;
    private ArrayList<Board> path;
    private Node dest;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        if (initial.isGoal()) {
            solvable = true;
            path = new ArrayList<Board>();
            path.add(initial);
            dest = new Node(initial, 0, null);
        }
        else {
            NodeComparator cmp = new NodeComparator();
            MinPQ<Node> pq1 = new MinPQ<>(cmp);
            MinPQ<Node> pq2 = new MinPQ<>(cmp);
            Board twin = initial.twin();
            pq1.insert(new Node(initial, 0, null));
            pq2.insert(new Node(twin, 0, null));
            boolean solved = false;
            while (!solved) {
                Node n1 = makeMove(pq1);
                Node n2 = makeMove(pq2);
                if (n1 != null || n2 != null) {
                    // Solution found, check if original or twin was solved
                    solved = true;
                    solvable = n1 != null;
                    Node ptr = n1 == null ? n2 : n1;
                    dest = ptr;
                    // Store solution path in [path]
                    path = new ArrayList<>();
                    while (ptr != null) {
                        path.add(0, ptr.getBoard());
                        ptr = ptr.prev;
                    }
                }
            }
        }
    }

    /**
     * Executes a move in the A* search algorithm. Does not add a node to [pq]
     * if its board is the same as the board of the node 2 rounds before it.
     *
     * @param pq The priority queue maintaining the order of nodes to visit
     * @return A solution board if a neighbor of the minimal board in [pq] is a
     * solution, else returns null
     */
    private Node makeMove(MinPQ<Node> pq) {
        // System.out.println(pq.size());
        if (pq.isEmpty()) {
            return null;
        }
        Node n = pq.delMin();
        Node prev = n.getPrev();
        int moves = n.getMoves();
        for (Board b : n.getBoard().neighbors()) {
            if (b.isGoal()) {
                return new Node(b, moves + 1, n);
            }
            if (prev == null || !b.equals(prev.getBoard())) {
                pq.insert(new Node(b, moves + 1, n));
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? dest.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? path : null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class Node {
        private final Board b;
        private final int moves;
        private final Node prev;
        private final int manh;

        public Node(Board b, int m, Node p) {
            this.b = b;
            moves = m;
            prev = p;
            this.manh = b.manhattan() + moves;
        }

        public Board getBoard() {
            return b;
        }

        public int getMoves() {
            return moves;
        }

        public Node getPrev() {
            return prev;
        }
    }

    private static class NodeComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.manh - n2.manh;
        }
    }

}
