import java.util.Random;

public class SortingAlgorithmTiming
{
	// Higher value means less repetition.
	private static int MAX_INTEGER_VALUE   = 10000;
	private static int[] SAMPLE_LENGTHS = {10000, 30000, 50000, 100000, 300000, 500000};

	public static void main(String[] args) {
		for (int length : SAMPLE_LENGTHS) {
			System.out.printf("Profiling with arrays of length %d.\n", length);

			int[] aryInsertSort = randomArray(length);
			int[] aryMergeSort  = new int[aryInsertSort.length];
			System.arraycopy(aryInsertSort, 0, aryMergeSort, 0, aryInsertSort.length);

			timeInsertSort(aryInsertSort);
			timeMergeSort(aryMergeSort);

			System.out.println("");
		}

	}

	private static void timeMergeSort(int[] ary) {
		Timer t = new Timer();
		Sorting.mergeSort(ary, 0, ary.length - 1);
		System.out.printf("Merge sort: Took %f seconds.\n", t.timeElapsed() / 1000.0);
	}

	private static void timeInsertSort(int[] ary) {
		Timer t = new Timer();
		Sorting.insertionSort(ary);
		System.out.printf("Insert sort: Took %f seconds.\n", t.timeElapsed() / 1000.0);
	}

	private static int[] randomArray(int length) {
		Random rnd = new Random();
		int[] ary = new int[length];

		for (int i = 0; i < length; i++) {
			ary[i] = rnd.nextInt(MAX_INTEGER_VALUE);
		}

		return ary;
	}
}
