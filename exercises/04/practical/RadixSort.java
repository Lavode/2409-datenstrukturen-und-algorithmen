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
	public static void radixSort(char[][] A, int d)
	{
		// 27 queues for 26 characters plus 'empty' character
		LinkedList[] queues = new LinkedList[27];
		
		// for all positions from right to left
		for(int j=d-1; j>=0; j--)
		{
			// initialize empty queues
			for(int i=0; i<27; i++) queues[i] = new LinkedList();
			
			// place each character array in correct queue
			for(int i=0; i<A.length; i++)
			{
				if(j<A[i].length)
				{
					// characters 'a'-'z'
					queues[A[i][j]-'a'+1].addLast(A[i]);
				} 
				else
				{
					// character array is shorter than current position.
					// place it in 'empty' queue. 'emtpy' queue is queue 0
					// to get lexicographically correct results, i.e., a<ab.
					queues[0].addLast(A[i]);
				}
			}
			
			// traverse queues 
			int n = 0;
			for(int i=0; i<27; i++)
			{				
				while(queues[i].size() > 0) 
				{
					A[n] = (char[])queues[i].removeFirst();
					n++;
				} 
			}
		}
	}
}
