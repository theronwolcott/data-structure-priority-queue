package pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORTS ARE HERE ONLY TO MAKE THE JAVADOC AND iterator() METHOD SIGNATURE
 * "SEE" THE RELEVANT CLASSES. SOME OF THOSE IMPORTS MIGHT *NOT* BE NEEDED BY YOUR OWN
 * IMPLEMENTATION, AND IT IS COMPLETELY FINE TO ERASE THEM. THE CHOICE IS YOURS.
 * ********************************************************************************** */

import demos.GenericArrays;
import pqueue.exceptions.*;
import pqueue.fifoqueues.EmptyFIFOQueueException;
import pqueue.fifoqueues.FIFOQueue;
import pqueue.fifoqueues.LinkedFIFOQueue;
import pqueue.heaps.ArrayMinHeap;

import java.util.*;

/**
 * <p>
 * {@link LinearPriorityQueue} is a {@link PriorityQueue} implemented as a
 * linear {@link java.util.Collection}
 * of common {@link FIFOQueue}s, where the {@link FIFOQueue}s themselves hold
 * objects
 * with the same priority (in the order they were inserted).
 * </p>
 *
 * <p>
 * You <b>must</b> implement the methods in this file! To receive <b>any
 * credit</b> for the unit tests related to
 * this class, your implementation <b>must</b> use <b>whichever</b> linear
 * {@link Collection} you want (e.g
 * {@link ArrayList}, {@link LinkedList}, {@link java.util.Queue}), or even the
 * various {@link List} and {@link FIFOQueue}
 * implementations that we provide for you. You can also use <b>raw</b> arrays,
 * but take a look at {@link GenericArrays}
 * if you intend to do so. Note that, unlike {@link ArrayMinHeap}, we do not
 * insist that you use a contiguous storage
 * {@link Collection}, but any one available (including {@link LinkedList})
 * </p>
 *
 * @param <T> The type held by the container.
 *
 * @author ---- Theron Wolcott ----
 *
 * @see MinHeapPriorityQueue
 * @see PriorityQueue
 * @see GenericArrays
 */
public class LinearPriorityQueue<T> implements PriorityQueue<T> {

	/*
	 * *****************************************************************************
	 * ******
	 * Write any private data elements or private methods for LinearPriorityQueue
	 * here...*
	 ***********************************************************************************/

	private int size; // the size of the whole collection
	private int queueCount; // the number of queues
	private int modCount;
	private LinkedList<QueueWrapper> list;

	private class QueueWrapper {
		FIFOQueue<T> queue;
		int priority;

		QueueWrapper(int priority) {
			this.queue = new LinkedFIFOQueue<>();
			this.priority = priority;
		}
	}

	/*
	 * *****************************************************************************
	 * ****************************
	 * Implement the following public methods. You should erase the throwings of
	 * UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/

	/**
	 * Default constructor initializes the element structure with
	 * a default capacity. This default capacity will be the default capacity of the
	 * underlying element structure that you will choose to use to implement this
	 * class.
	 */
	public LinearPriorityQueue() {
		size = 0;
		queueCount = 0;
		modCount = 0;
		list = new LinkedList<>();
	}

	/**
	 * Non-default constructor initializes the element structure with
	 * the provided capacity. This provided capacity will need to be passed to the
	 * default capacity
	 * of the underlying element structure that you will choose to use to implement
	 * this class.
	 * 
	 * @see #LinearPriorityQueue()
	 * @param capacity The initial capacity to endow your inner implementation with.
	 * @throws InvalidCapacityException if the capacity provided is less than 1.
	 */
	public LinearPriorityQueue(int capacity) throws InvalidCapacityException { // DO *NOT* ERASE THE "THROWS"
																				// DECLARATION!
		this();
		if (capacity < 1) {
			throw new InvalidCapacityException("Invalid capacity");
		}
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException { // DO *NOT* ERASE THE "THROWS"
																					// DECLARATION!
		if (priority < 1) {
			throw new InvalidPriorityException("Invalid priority");
		}
		QueueWrapper wrap;
		modCount++;
		if (queueCount == 0) {
			// make first wrapper + queue
			wrap = new QueueWrapper(priority);
			wrap.queue.enqueue(element);
			list.add(wrap);
			queueCount++;
			size++;
			return;
		} else {
			int i = 0;
			for (var curr : list) {
				if (curr.priority == priority) {
					// add to existing queue
					curr.queue.enqueue(element);
					size++;
					return;
				} else if (priority < curr.priority) {
					// make new wrapper + queue at this index (pushing the curr down one)
					wrap = new QueueWrapper(priority);
					wrap.queue.enqueue(element);
					list.add(i, wrap);
					queueCount++;
					size++;
					return;
				}
				i++;
			}
		}
		// if it falls to here, add a new wrapper + queue to the end
		wrap = new QueueWrapper(priority);
		wrap.queue.enqueue(element);
		list.addLast(wrap);
		queueCount++;
		size++;
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException { // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (size == 0) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		modCount++;
		try {
			var target = list.getFirst().queue.dequeue();
			// check to see if this queue (for this priority) is empty
			if (list.getFirst().queue.isEmpty()) {
				// remove the first (lowest priority) queue
				list.remove();
				queueCount--;
			}
			size--;
			return target;
		} catch (NoSuchElementException | EmptyFIFOQueueException e) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException { // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (size == 0) {
			throw new EmptyPriorityQueueException("Empty queue");
		}
		try {
			return list.getFirst().queue.first();
		} catch (NoSuchElementException | EmptyFIFOQueueException e) {
			throw new EmptyPriorityQueueException("Empty queue");
		}

	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> iterator = new Iterator<T>() {
			int origCount = modCount;
			Iterator<QueueWrapper> outer = list.iterator();
			Iterator<T> inner = null;

			@Override
			public boolean hasNext() {
				return (outer.hasNext() || (inner != null && inner.hasNext()));
			}

			@Override
			public T next() {
				if (modCount != origCount) {
					throw new ConcurrentModificationException();
				}
				if (inner == null) {
					// advance outer
					inner = outer.next().queue.iterator();
				}
				// advance inner
				var target = inner.next();
				if (!inner.hasNext()) {
					// reset inner
					inner = null;
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