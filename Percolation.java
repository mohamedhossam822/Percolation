import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final int Top, Bottom, size;
	private int count = 0;

	// Model Declaration
	private int[] Model;

	private WeightedQuickUnionUF cellStorage;

	public Percolation(int n) {
		if (n < 1) {
			throw new java.lang.IllegalArgumentException("Cann't Make a model with " + n + " dimensions");
		}

		// Assign Model
		Model = new int[n * n + 2];

		// Initialize Model
		Model[0] = 1;
		for (int i = 1; i < n * n + 1; i++)
			Model[i] = 0;
		Model[n * n + 1] = 1;

		// Assign CellStorage
		cellStorage = new WeightedQuickUnionUF(n * n + 2);

		// Initialize some variables
		size = n;
		Top = 1; // first row and column number
		Bottom = n; // last row and column number
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {

		if (row < 1 || row > size || col < 1 || col > size) {
			throw new java.lang.IllegalArgumentException("Out of Bounds Indices");
		}
		// Check if cell is already open
		if (isOpen(row, col))
			return;

		int temp_cell, cell_index;

		cell_index = getCellIndex(row, col);
		Model[cell_index] = 1;
		count++;

		// Check if cell is connected to the Top or Bottom
		if (row == Top)
			cellStorage.union(0, cell_index);
		else if (row == Bottom)
			cellStorage.union(size * size + 1, cell_index);

		// Check if a cell above exists
		if (row != Top && isOpen(row - 1, col)) {
			temp_cell = getCellIndex(row - 1, col);
			cellStorage.union(cell_index, temp_cell);
		}

		// Check if a cell below exists
		if (row != Bottom && isOpen(row + 1, col)) {
			temp_cell = getCellIndex(row + 1, col);
			cellStorage.union(cell_index, temp_cell);
		}

		// Check if left cell exist
		if (col != Top && isOpen(row, col - 1)) {
			temp_cell = getCellIndex(row, col - 1);
			cellStorage.union(cell_index, temp_cell);
		}

		// Check if right cell exist
		if (col != Bottom && isOpen(row, col + 1)) {
			temp_cell = getCellIndex(row, col + 1);
			cellStorage.union(cell_index, temp_cell);
		}

	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new java.lang.IllegalArgumentException("Out of Bounds Indices");
		}

		int temp_cell = getCellIndex(row, col);
		return Model[temp_cell] == 1;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new java.lang.IllegalArgumentException("Out of Bounds Indices");
		}
		int temp_cell = getCellIndex(row, col);
		return cellStorage.find(0) == cellStorage.find(temp_cell);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return count;
	}

	// does the system percolate?
	public boolean percolates() {
		return cellStorage.find(0) == cellStorage.find(Bottom * Bottom + 1);
	}

	// General Functions

	private int getCellIndex(int row, int col) {
		return (size * (row - 1)) + col;
	}

	public void ShowModel() {
		int i, j;
		System.out.println("***************");
		System.out.println(Model[0]);
		for (i = 1; i < size * size + 1;) {
			for (j = 0; j < size; j++) {
				System.out.print(Model[i]);
				i++;
			}
			System.out.println();
		}
		System.out.println(Model[size * size + 1]);
		System.out.println("***************");
	}

	// Main
	public static void main(String[] args) {

		Percolation obj = new Percolation(3);

		obj.ShowModel();

		obj.open(1, 3);
		obj.ShowModel();
		System.out.println("Precolation : " + obj.percolates());

		obj.open(2, 2);
		obj.ShowModel();
		System.out.println("Precolation : " + obj.percolates());

		obj.open(2, 1);
		obj.ShowModel();
		System.out.println("Precolation : " + obj.percolates());

		obj.open(2, 3);
		obj.ShowModel();
		System.out.println("Precolation : " + obj.percolates());

		obj.open(3, 1);
		obj.ShowModel();
		System.out.println("Precolation : " + obj.percolates());

	}

}