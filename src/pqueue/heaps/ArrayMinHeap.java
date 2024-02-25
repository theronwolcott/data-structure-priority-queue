package pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import pqueue.exceptions.UnimplementedMethodException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * <p>
 * {@link ArrayMinHeap} is a {@link MinHeap} implemented using an internal
 * array. Since heaps are <b>complete</b>
 * binary trees, using contiguous storage to store them is an excellent idea,
 * since with such storage we avoid
 * wasting bytes per {@code null} pointer in a linked implementation.
 * </p>
 *
 * <p>
 * You <b>must</b> edit this class! To receive <b>any</b> credit for the unit
 * tests related to this class,
 * your implementation <b>must</b> be a <b>contiguous storage</b> implementation
 * based on a linear {@link java.util.Collection}
 * like an {@link java.util.ArrayList} or a {@link java.util.Vector} (but *not*
 * a {@link java.util.LinkedList} because it's *not*
 * contiguous storage!). or a raw Java array. We provide an array for you to
 * start with, but if you prefer, you can switch it to a
 * {@link java.util.Collection} as mentioned above.
 * </p>
 *
 * @author -- Theron Wolcott ---
 *
 * @see MinHeap
 * @see LinkedMinHeap
 * @see demos.GenericArrays
 */

public class ArrayMinHeap<T extends Comparable<T>> implements MinHeap<T> {

	/*
	 * *****************************************************************************
	 * ************************************
	 * This array will store your data. You may replace it with a linear Collection
	 * if you wish, but
	 * consult this class' * JavaDocs before you do so. We allow you this option
	 * because if you aren't
	 * careful, you can end up having ClassCastExceptions thrown at you if you work
	 * with a raw array of Objects.
	 * See, the type T that this class contains needs to be Comparable with other
	 * types T, but Objects are at the top
	 * of the class hierarchy; they can't be Comparable, Iterable, Clonable,
	 * Serializable, etc. See GenericArrays.java
	 * under the package demos* for more information.
	 *****************************************************************************************************************/
	private ArrayList<T> data;

	/*
	 * *****************************************************************************
	 * ****** *
	 * Write any further private data elements or private methods for LinkedMinHeap
	 * here...*
	 *************************************************************************************/

	/*
	 * *****************************************************************************
	 * ****************************
	 * Implement the following public methods. You should erase the throwings of
	 * UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/

	/**
	 * Default constructor initializes the data structure with some default
	 * capacity you can choose.
	 */
	public ArrayMinHeap() {
		this.data = new ArrayList<T>();
	}

	/**
	 * Second, non-default constructor which provides the element with which to
	 * initialize the heap's root.
	 * 
	 * @param rootElement the element to create the root with.
	 */
	public ArrayMinHeap(T rootElement) {
		this();
		data.add(rootElement);
	}

	/**
	 * Copy constructor initializes {@code this} as a carbon copy of the
	 * {@link MinHeap} parameter.
	 *
	 * @param other The MinHeap object to base construction of the current object
	 *              on.
	 */
	public ArrayMinHeap(MinHeap<T> other) {
		this();
		for (var t : other) {
			data.add(t);
		}
	}

	/**
	 * Standard {@code equals()} method. We provide it for you: DO NOT ERASE!
	 * Consider its implementation when implementing
	 * {@link #ArrayMinHeap(MinHeap)}.
	 * 
	 * @return {@code true} if the current object and the parameter object
	 *         are equal, with the code providing the equality contract.
	 * @see #ArrayMinHeap(MinHeap)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof MinHeap))
			return false;
		Iterator itThis = iterator();
		Iterator itOther = ((MinHeap) other).iterator();
		while (itThis.hasNext())
			if (!itThis.next().equals(itOther.next()))
				return false;
		return !itOther.hasNext();
	}

	private int getParentIndex(int index) {
		return (index - 1) / 2;
	}
	// private int getParentIndex (T element) {
	// int index = ((this.data.indexOf(element) - 1) / 2);
	// return index;
	// }
	// private T getParentElement(int index) {
	// return data.get(getParentIndex(index));
	// }
	// private T getParentElement (T element) {
	// T parent = this.data.get(getParentIndex(element));
	// return parent;
	// }

	private void swapUp(int index) {
		int parentIndex = getParentIndex(index);
		if ((index == 0) || data.get(parentIndex).compareTo(data.get(index)) <= 0) { // element is at root
			return;
		} else { // swap with parent
			// Collections.swap(this.data, getParentIndex(element),
			// this.data.indexOf(element));
			Collections.swap(this.data, index, parentIndex);
			swapUp(parentIndex);
		}
	}

	public void swapTest(T element, T element2) {
		Collections.swap(this.data, this.data.indexOf(element), this.data.indexOf(element2));
		System.out.println(this.data.toString());
	}

	@Override
	public void insert(T element) {
		this.data.add(element);
		swapUp(data.size() - 1);
	}

	// private T getLesserChild(T element) {
	// if (this.data.get((this.data.indexOf(element)) *
	// 2).compareTo(this.data.get(((this.data.indexOf(element)) / 2) + 1)) <= 0) {
	// return this.data.get((this.data.indexOf(element)) * 2);
	// } else {
	// return this.data.get(((this.data.indexOf(element)) * 2) + 1);
	// }
	// }

	private int leftChildIndex(int index) {
		return (index * 2) + 1;
	}

	private int rightChildIndex(int index) {
		return (index * 2) + 2;
	}

	private void swapDown(int index) {
		Boolean hasLeftChild = data.size() > leftChildIndex(index);
		Boolean hasRightChild = data.size() > rightChildIndex(index);
		T value;
		T leftChildValue;
		if (!hasLeftChild) {
			return;
		}
		value = data.get(index);
		leftChildValue = data.get(leftChildIndex(index));
		if (hasRightChild && data.get(rightChildIndex(index)).compareTo(leftChildValue) < 0) {
			if (data.get(rightChildIndex(index)).compareTo(value) < 0) {
				Collections.swap(data, index, rightChildIndex(index));
				swapDown(rightChildIndex(index));
			}
		} else if (leftChildValue.compareTo(value) < 0) {
			Collections.swap(data, index, leftChildIndex(index));
			swapDown(leftChildIndex(index));
		}
	}

	@Override
	public T deleteMin() throws EmptyHeapException { // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (data.size() == 0) {
			throw new EmptyHeapException("Heap is empty");
		}
		T target = this.data.get(0);
		Collections.swap(this.data, 0, (this.data.size() - 1));
		this.data.remove(this.data.size() - 1);
		swapDown(0);
		return target;
	}

	@Override
	public T getMin() throws EmptyHeapException { // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (data.size() == 0) {
			throw new EmptyHeapException("Heap is empty");
		}
		return this.data.get(0);
	}

	@Override
	public int size() {
		return this.data.size();
	}

	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	public void print(ArrayMinHeap<T> heap) {
		System.out.println(this.data.toString());
	}

	/**
	 * Standard equals() method.
	 * 
	 * @return {@code true} if the current object and the parameter object
	 *         are equal, with the code providing the equality contract.
	 */

	@Override
	public Iterator<T> iterator() {
		return this.data.iterator();
	}

}
