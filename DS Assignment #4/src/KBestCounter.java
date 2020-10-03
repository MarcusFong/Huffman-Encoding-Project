import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class KBestCounter<T extends Comparable<? super T>> {
	
	private PriorityQueue<T> prioQueue;
	private List<T> kBestList;
	private int k;
	
	
	public KBestCounter(int kSize) {
		prioQueue = new PriorityQueue<>();
		k = kSize;		
		kBestList = new ArrayList<>();
		
	}
	
	
	/**
	 * This method adds an element to the priority queue if the current 
	 * size is less than k. If the current size goes greater k, the 
	 * head value is first removed before adding the new value. 
	 * 
	 * @param x the element to be inserted into the priority queue
	 */
	public void count(T x) {
		//continue adding values to the minheap while currentSize is less than k
		
		//System.out.println("" + x + "  " + currentSize + "  " + prioQueue);

		if (prioQueue.size() < k) {
			prioQueue.add(x);
			
		}
		
		//if currentSize == k, remove the head value (because its the min)
		else if ( prioQueue.size() <= k && prioQueue.peek().compareTo(x) < 0){
			prioQueue.remove();
			prioQueue.add(x);
		}
	}
	
	/**
	 * This method polls each head of the priority queue into the 
	 * kBestList LinkedList to create a List of the k largest values 
	 * from the set in descending order. Once the list is created,
	 * it adds them back into the priority queue. 
	 * 
	 * @return a sorted List in descending order from the inputted values. 
	 */
	public List<T> kbest(){
		kBestList.clear();
		int size = prioQueue.size();
		//System.out.println(size);
		
		for (int i = 0; i < size; i++) {
			T add = prioQueue.poll();
			kBestList.add(0, add);
		}

		//put the elements back into the priority queue
		//start from the end of kBestList because its the smallest
		for (int j = kBestList.size()-1; j >= 0; j--) {
			count(kBestList.get(j));
		}
		
		return kBestList;
	}
	
}













