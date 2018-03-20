import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class RadixSort {

	/*
	 * Implements radix sort. Character arrays of different lengths
	 * are ordered lexicographically, for example, a<ab<b. The 
	 * implementation doesn't use counting sort as a stable sort. 
	 * Instead, it simply uses an array of queues for each character 
	 * value.  
	 * 
	 * @param A an array of character arrays with different lengths
	 * @param d the length of the longest array in A 
	 */
	public static void radixSort(char[][] A, int d) {

		int[] lengths = sortByLength(A, d);

		// 26 queues for 26 letters.
		List<LinkedList<char[]>> queues = new LinkedList<LinkedList<char[]>>();

		// Iterate over all to-be-compared chars, starting at
		// right-most char.
		for(int j = d - 1; j >= 0; j--) {
			queues.clear();
			for(int i = 0; i < 26; i++) {
				queues.add(new LinkedList<char[]>());
			}

			// Iterate over words long enough to compare character
			// at index j.
			// Mind: If you want to compare a char at index j = 4,
			// the word must have a length of at least 5.
			for(int i = A.length - lengths[j + 1]; i < A.length; i++) {
				char comparisonChar = A[i][j];
				int listOffset = comparisonChar - 'a';

				queues.get(listOffset).addLast(A[i]);
			}

			// Put newly-sorted words back into char array.
			int n = A.length - lengths[j + 1];
			for (LinkedList<char[]> queue : queues) {
				for (char[] word : queue) {
					A[n] = word;
					n++;
				}
			}
		}
	}

	/**
	 * Sorts supplied char array in-place by length ascendingly.
	 *
	 * Returns an array, with x[i] being the number of items in A with
	 * length >= i.
	 */
	private static int[] sortByLength(char[][] A, int d) {
		List<LinkedList<char[]>> queues = new ArrayList<LinkedList<char[]>>();
		int[] lengths = new int[d + 1];
		for (int i = 0; i <= d; i++) {
			lengths[i] = 0;
			queues.add(new LinkedList<char[]>());
		}


		for (char[] word : A) {
			int wordLength = word.length;
			lengths[wordLength]++;
			queues.get(wordLength).addLast(word);
		}

		for (int i = lengths.length - 2; i >= 0; i--) {
			lengths[i] = lengths[i + 1] + lengths[i];
		}

		int i = 0;
		for (LinkedList<char[]> queue : queues) {
			for (char[] word : queue) {
				A[i] = word;
				i++;
			}
		}

		return lengths;
	}
}
