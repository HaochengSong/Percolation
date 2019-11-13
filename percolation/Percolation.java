import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[] sites;
	private final int number;
	private final int virtual_bottom;
	private int opensites;
	private final WeightedQuickUnionUF WQU;
	private final WeightedQuickUnionUF WBW;
	public Percolation(int n) {
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException("Illegal n");
		}
		int sum = n * n + 2;
		number = n;
		virtual_bottom = n * n + 1;
		sites = new boolean[sum];
		opensites = 0;
		WQU = new WeightedQuickUnionUF(sum);
		WBW = new WeightedQuickUnionUF(sum - 1);
	}
	public void open(int row, int col) {
		if (row <= 0 || row > number || col <= 0 || col > number)
			throw new java.lang.IllegalArgumentException("Illegal row or col");
		if (isOpen(row, col))
			return;
		int point = xytrans(row, col);
		sites[xytrans(row, col)] = true;
		if (row == 1) {
			WQU.union(0, point);
			WBW.union(0, point);
		}
		if (row == number)
			WQU.union(virtual_bottom, point);
		int[] x = {1, -1, 0, 0};
		int[] y = {0, 0, 1, -1};
		for (int i = 0; i < 4; i++) {
			int newx = row + x[i];
			int newy = col + y[i];
			if(isBoundsValid(newx, newy) && isOpen(newx, newy)) {
				int newp = xytrans(newx,newy);
				WQU.union(point, newp);
				WBW.union(point, newp);
			}
		}
		opensites++;
	}
	public boolean isOpen(int row, int col) {	
		if (row <= 0 || row > number || col <= 0 || col > number)
			throw new java.lang.IllegalArgumentException("Illegal row or col");
		return sites[xytrans(row, col)];
	}
	public boolean isFull(int row, int col) {
		if (row <= 0 || row > number || col <= 0 || col > number)
			throw new java.lang.IllegalArgumentException("Illegal row or col");
		int point = xytrans(row, col);
		return WBW.connected(0, point);
	}
	public int numberOfOpenSites() {
		return opensites;
	}
	public boolean percolates() {
		return WQU.connected(0, virtual_bottom);
	}
	private int xytrans(int row, int col) {
		return number * (row - 1) + col;
	}
	private boolean isBoundsValid(int row, int col) {
		return !(row > number || row < 1 || col > number || col < 1);
	}
	public static void main(String[] args) {
		Percolation a = new Percolation(10);
		StdOut.print(a.isBoundsValid(10, 0));
	}
}
