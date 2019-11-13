import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private final int count;
    private final double[] threshold;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
			throw new java.lang.IllegalArgumentException("Illegal n or trials");
		threshold = new double[trials];
		count = trials;
		for (int i = 0; i < trials; i++) {
			Percolation a = new Percolation(n);
			while (!a.percolates()) {
				int x = StdRandom.uniform(n) + 1;
				int y = StdRandom.uniform(n) + 1;
				if (!a.isOpen(x, y)) {
					a.open(x, y);
				}
			}
			threshold[i] = a.numberOfOpenSites() / (double)(n * n);
		}
	}
	public double mean() {
		double sum = 0;
		for (int i = 0; i < count; i++) {
			sum += threshold[i];
		}
		return sum / count;
	}
	public double stddev() {
		double sum = 0;
		for (int i = 0; i < count; i++) {
			sum += Math.pow(threshold[i] - mean(), 2);
		}
		return Math.sqrt(sum / (count - 1));
	}
	public double confidenceLo() {		
		return StdStats.mean(threshold) - 1.96 * StdStats.stddev(threshold) / Math.sqrt(count);
	}
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(count);
	}
	public static void main(String[] args) {
		int n = 200, T = 100;
		PercolationStats p = new PercolationStats(n, T);
		StdOut.println("mean" + "\t= " + p.mean());
		StdOut.println("stddev" + "\t= " + p.stddev());
		StdOut.println("95% confidence interval" + "\t= " + "[" + p.confidenceLo() + ", "  + p.confidenceHi()+ "]");
	}
}
