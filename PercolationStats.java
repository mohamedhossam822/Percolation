import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final double[] threshold;
	private final int trials;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		
		if (n < 1 || trials < 1)
			throw new IllegalArgumentException("Both arguments must be bigger than 1");
		
		int random_row, random_col;
		Percolation PercObj = new Percolation(n);
		this.trials = trials;
		threshold = new double[trials];
		
		for (int i = 0; i < trials; i++) {
			PercObj = new Percolation(n);
			while (!PercObj.percolates()) {
				
				random_row = StdRandom.uniform(1, n + 1);
				random_col = StdRandom.uniform(1, n + 1);
				
				if (!PercObj.isOpen(random_row, random_col)) {
					PercObj.open(random_row, random_col);
				}
			}
			threshold[i] = PercObj.numberOfOpenSites() / (double) (n * n);
		}

	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(threshold);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}

	// low end point of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / (Math.sqrt(trials)));
	}

	// high end point of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / (Math.sqrt(trials)));
	}

	// test client (see below)
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(200, 100);
		System.out.println("mean = " + ps.mean() + "\n");
		System.out.println("std dev = " + ps.stddev() + "\n");
		System.out.println("95% confidence interval = [ " + ps.confidenceLo() + ", " + ps.confidenceHi() + " ]");
	}

}
