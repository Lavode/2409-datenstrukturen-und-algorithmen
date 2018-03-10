import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Allow sorting of generic lists using recursive quicksort.
 */
public class QuickSort {
	private static Random rnd = new Random();

	public static <T> void quickSort(ArrayList<T> array, int left, int right, Comparator<T> comp) {
		if (left < right) {
			int i = partition(array, left, right, comp);

			quickSort(array, left, i, comp);
			quickSort(array, i + 1, right, comp);
		}

	}

	private static <T> int partition(ArrayList<T> array, int left, int right, Comparator<T> comp) {
		// - Assume our partition's elements go from indices m to n.
		// - nextInt(upper) will return an integer within [0, upper[
		// - We want an index within [m, n]
		// Hence: [m, n] == [m, n + 1[ == m + [0, n - m +1[
		int randomPivotIndex = left + rnd.nextInt(right - left + 1);
		T pivot = array.get(randomPivotIndex);

		int l = left - 1;
		int r = right + 1;

		do {
			// Find leftmost element bigger than pivot.
			do {
				l++;
			} while (comp.compare(array.get(l), pivot) < 0);

			// Find rightmost element smaller than pivot.
			do {
				r--;
			} while (comp.compare(array.get(r), pivot) > 0);

			if (l >= r) {
				return r;
			}

			// Swap two elements
			T tmp = array.get(l);
			array.set(l, array.get(r));
			array.set(r, tmp);
		} while (true);
	}
}

