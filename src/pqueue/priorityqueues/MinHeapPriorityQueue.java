package pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******


/* *****************************************************************************************
 * THE FOLLOWING IMPORTS WILL BE NEEDED BY YOUR CODE, BECAUSE WE REQUIRE THAT YOU USE
 * ANY ONE OF YOUR EXISTING MINHEAP IMPLEMENTATIONS TO IMPLEMENT THIS CLASS. TO ACCESS
 * YOUR MINHEAP'S METHODS YOU NEED THEIR SIGNATURES, WHICH ARE DECLARED IN THE MINHEAP
 * INTERFACE. ALSO, SINCE THE PRIORITYQUEUE INTERFACE THAT YOU EXTEND IS ITERABLE, THE IMPORT OF ITERATOR
 * IS NEEDED IN ORDER TO MAKE YOUR CODE COMPILABLE. THE IMPLEMENTATIONS OF CHECKED EXCEPTIONS
 * ARE ALSO MADE VISIBLE BY VIRTUE OF THESE IMPORTS.
 ** ********************************************************************************* */

import pqueue.exceptions.*;
import pqueue.heaps.ArrayMinHeap;
import pqueue.heaps.EmptyHeapException;
import pqueue.heaps.MinHeap;

import java.util.Iterator;
/**
 * <p>{@link MinHeapPriorityQueue} is a {@link PriorityQueue} implemented using a {@link MinHeap}.</p>
 *
 * <p>You  <b>must</b> implement the methods of this class! To receive <b>any credit</b> for the unit tests
 * related to this class, your implementation <b>must</b> use <b>whichever</b> {@link MinHeap} implementation
 * among the two that you should have implemented you choose!</p>
 *
 * @author  ---- Theron Wolcott ----
 *
 * @param <T> The Type held by the container.
 *
 * @see LinearPriorityQueue
 * @see MinHeap
 * @see PriorityQueue
 */
public class MinHeapPriorityQueue<T> implements PriorityQueue<T>{

	/* ***********************************************************************************
	 * Write any private data elements or private methods for MinHeapPriorityQueue here...*
	 * ***********************************************************************************/

	private ArrayMinHeap<Node> heap;
	int count = 0;

	private class Node implements Comparable<Node>{
		T data;
		int priority;
		int sequence; 
		
		Node(T data, int priority) {
			this.data = data;
			this.priority = priority;
			this.sequence = count++;
		}

		@Override
		public int compareTo(Node o) {
			if (o.priority == this.priority) {
				if (o.sequence < this.sequence) {
					return 1;
				} else {
					return -1;
				}
			} else if (o.priority < this.priority) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	/* *********************************************************************************************************
	 * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/
		/**
	 * Simple default constructor.
	 */
	public MinHeapPriorityQueue(){
		heap = new ArrayMinHeap<>();
	}


	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (priority < 1) {
			throw new InvalidPriorityException("Invalid priority");
		}
		heap.insert(new Node(element, priority));
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException {		// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (heap.isEmpty()) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		T data;
		try {
			data = heap.deleteMin().data;
		} catch (EmptyHeapException e) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		return data;
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (heap.isEmpty()) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		T data;
		try {
			data = heap.getMin().data;
		} catch (EmptyHeapException e) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		return data;
	}

	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}


	@Override
	public Iterator<T> iterator() {
		Iterator<T> iterator = new Iterator<T>() {
			// duplicate the heap so we can pop off the top, in order, without destroying our heap
			ArrayMinHeap<Node> copy = new ArrayMinHeap<>(heap);
	  
			@Override
			public boolean hasNext() {
				// heap copy still has more in it
				return !copy.isEmpty();
			}
	  
			@Override
			public T next() {
				T target;
				try {
					// pop off the top of the heap copy
					target = copy.deleteMin().data;
				} catch (EmptyHeapException e) {
					// this really shouldn't happen, but if it does...
					target = null;
				}
				return target;
			}
		};
	  
		  return iterator;
	}
	@Override
	public String toString() {
		String s = "";
		for (T element : this) {
			s += element.toString() + ",";
		}
		return s;
	}
}
