/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double Z = 1.96;
    private final double[] x;
    private final int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int size = n * n;
        x = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation((n));
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            x[i] = (double) p.numberOfOpenSites() / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - Z * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + Z * stddev() / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, t);
        double mean = p.mean();
        double stddev = p.stddev();
        double lo = p.confidenceLo();
        double hi = p.confidenceHi();
        System.out.println(String.format("%-23s = %f", "mean", mean));
        System.out.println(String.format("%-23s = %f", "stddev", stddev));
        System.out.println(String.format("%-23s = [%f, %f]",
                                         "95% confidence interval", lo, hi));
    }

}
