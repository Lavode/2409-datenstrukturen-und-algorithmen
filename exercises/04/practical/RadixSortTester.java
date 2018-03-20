
public class RadixSortTester {

	/**
	 * Generate a random character array of length d
	 * @param d length
	 * @return character array
	 */
	public static char[] randomCharArray(int d)
	{
		char[] s = new char[d];
		
		for(int i=0; i<d; i++)
		{
			s[i] = (char)('a'+Math.random()*26);
		}
		
		return s;
	}
	
	/**
	 * Generate an array of random character arrays of
	 * different lengths.
	 * @param n number of arrays
	 * @param d maximum length of arrays
	 * @return array of character arrays
	 */
	public static char[][] generateTestData(int n, int d)
	{
		char[][] testData = new char[n][];
		
		for(int i=0; i<n; i++)
		{
			testData[i] = randomCharArray(1+(int)Math.floor(Math.random()*d));
		}
		
		return testData;
	}
	
	public static void main(String[] args) {

		int n[] = {10000, 20000, 40000, 80000, 160000, 320000};
		int d = 150;
		
		// for(int i=0; i<n.length; i++)
		// {
		// 	char[][] testData = generateTestData(n[i], d);
		// 
		// 	Timer t = new Timer();
		// 	t.reset();			
		// 	RadixSort.radixSort(testData, d);			
		// 	System.out.printf("Number of elements: %d, Time: %dms\n", n[i], t.timeElapsed());
		// }
		char[][] testData = generateTestData(10, 5);
		RadixSort.radixSort(testData, 5);

		System.out.println("\nHopefully sorted output:");
		for (char[] word : testData) {
			String s = new String(word);
			System.out.println(s);
		}
	}
}
