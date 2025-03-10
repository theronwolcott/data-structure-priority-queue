package pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

import java.util.ConcurrentModificationException;

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import java.util.Iterator;

import pqueue.exceptions.UnimplementedMethodException;

/**
 * <p>A {@link LinkedMinHeap} is a tree (specifically, a <b>complete</b> binary tree) where every node is
 * smaller than or equal to its descendants (as defined by the {@link Comparable#compareTo(Object)} overridings of the type T).
 * Percolation is employed when the root is deleted, and insertions guarantee maintenance of the heap property in logarithmic time. </p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a &quot;linked&quot;, <b>non-contiguous storage</b> implementation based on a
 * binary tree of nodes and references. Use the skeleton code we have provided to your advantage, but always remember
 * that the only functionality our tests can test is {@code public} functionality.</p>
 * 
 * @author --- YOUR NAME HERE! ---
 *
 * @param <T> The {@link Comparable} type of object held by {@code this}.
 *
 * @see MinHeap
 * @see ArrayMinHeap
 */
public class LinkedMinHeap<T extends Comparable<T>> implements MinHeap<T> {

	/* ***********************************************************************
	 * An inner class representing a minheap's node. YOU *SHOULD* BUILD YOUR *
	 * IMPLEMENTATION ON TOP OF THIS CLASS!                                  *
 	 * ********************************************************************* */
	private class MinHeapNode {
		private T data;
		private MinHeapNode lChild, rChild;

        /* *******************************************************************
         * Write any further data elements or methods for MinHeapNode here...*
         ********************************************************************* */
	
		MinHeapNode(T data) {
			this.data = data;
		}

	}

	/* *********************************
	  * Root of your tree: DO NOT ERASE!
	  * *********************************
	 */
	private MinHeapNode root;




    /* *********************************************************************************** *
     * Write any further private data elements or private methods for LinkedMinHeap here...*
     * *************************************************************************************/


	private int size;
	private int modCount;


    /* *********************************************************************************************************
     * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
     ***********************************************************************************************************/

	/**
	 * Default constructor.
	 */
	public LinkedMinHeap() {
		this.size = 0;
		this.modCount = 0;
	}

	/**
	 * Second constructor initializes {@code this} with the provided element.
	 *
	 * @param rootElement the data to create the root with.
	 */
	public LinkedMinHeap(T rootElement) {
		this();
		this.insert(rootElement);
	}

	/**
	 * Copy constructor initializes {@code this} as a carbon
	 * copy of the parameter, which is of the general type {@link MinHeap}!
	 * Since {@link MinHeap} is an {@link Iterable} type, we can access all
	 * of its elements in proper order and insert them into {@code this}.
	 *
	 * @param other The {@link MinHeap} to copy the elements from.
	 */
	public LinkedMinHeap(MinHeap<T> other) {
		this();
		for (var t : other) {
			insert(t);
		}
	}

    /**
     * Standard {@code equals} method. We provide this for you. DO NOT EDIT!
     * You should notice how the existence of an {@link Iterator} for {@link MinHeap}
     * allows us to access the elements of the argument reference. This should give you ideas
     * for {@link #LinkedMinHeap(MinHeap)}.
     * @return {@code true} If the parameter {@code Object} and the current MinHeap
     * are identical Objects.
     *
     * @see Object#equals(Object)
     * @see #LinkedMinHeap(MinHeap)
     */
	/**
	 * Standard equals() method.
	 *
	 * @return {@code true} If the parameter Object and the current MinHeap
	 * are identical Objects.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MinHeap))
			return false;
		Iterator itThis = iterator();
		Iterator itOther = ((MinHeap) other).iterator();
		while (itThis.hasNext())
			if (!itThis.next().equals(itOther.next()))
				return false;
		return !itOther.hasNext();
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	private int getParentPosition (int position) {
		return position / 2;
	}
	private int getLeftPosition (int position) {
		return position * 2;
	}
	private int getRightPosition (int position) {
		return (position * 2) + 1;
	}

	private MinHeapNode getNodeByPosition (int position) {
		if (position == 1 || root == null) {
			return root;
		} 
		MinHeapNode curr = root;
		String binary = Integer.toBinaryString(position).substring(1);
		for (char digit : binary.toCharArray()) {
			if (digit == '0') {
				curr = curr.lChild;
			} else {
				curr = curr.rChild;
			}
		}
		return curr;
	}

	private void swapUp(MinHeapNode child, int pos) {
		if (pos == 1) {
			return;
		}
		var parent = getNodeByPosition(getParentPosition(pos));
		if ((parent.data.compareTo(child.data)) > 0) {
			T temp = parent.data;
			parent.data = child.data;
			child.data = temp;
			swapUp(parent, getParentPosition(pos));
		}
 	}

	@Override
	public void insert(T element) {
		modCount++;
		var node = new MinHeapNode(element);
		if (size == 0) {
			root = node;
			size++;
			return;
		} 
		int pos = size + 1;
		var parent = getNodeByPosition(getParentPosition(pos));
		if (pos % 2 == 0) {
			parent.lChild = node;
		} else {
			parent.rChild = node;
		}
		swapUp(node, pos);
		size++;
	}

	@Override
	public T getMin() throws EmptyHeapException {		// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (size == 0) {
			throw new EmptyHeapException("Empty heap");
		}
		return root.data;
	}

	private enum LesserChild {
		None,
		Left,
		Right
	}

	private LesserChild findLesserChild (MinHeapNode parent) {
		if (parent.lChild == null) {
			return LesserChild.None;
		} 
		if (parent.rChild == null) {
			return LesserChild.Left;
		} 
		if ((parent.lChild.data.compareTo(parent.rChild.data)) <= 0) {
			return LesserChild.Left;
		} else {
			return LesserChild.Right;
		}
	}

	private void swapDown(MinHeapNode parent, int position) {
		T data;
		MinHeapNode childNode;
		int childPos;
		var lesserChild = findLesserChild(parent);
		if (lesserChild == LesserChild.None) {
			return;
		}
		childPos = lesserChild == LesserChild.Left ? getLeftPosition(position) : getRightPosition(position);
		childNode = lesserChild == LesserChild.Left ? parent.lChild : parent.rChild;
		if ((parent.data.compareTo(childNode.data)) > 0) {
			data = parent.data;
			parent.data = childNode.data;
			childNode.data = data;
			swapDown(childNode, childPos);
		}
	}

	@Override
	public T deleteMin() throws EmptyHeapException {    // DO *NOT* ERASE THE "THROWS" DECLARATION!
		T data;
		modCount++;
		if (size == 0) {
			throw new EmptyHeapException("Empty heap");
		} else if (size == 1) {
			data = root.data;
			root = null;
			size--;
		} else {
			data = root.data;
			root.data = getNodeByPosition(size).data;
			var parent = getNodeByPosition(getParentPosition(size));
			if (size % 2 == 0) {
				parent.lChild = null;
			} else {
				parent.rChild = null;
			}
			size--;
			swapDown(root, 1);
		}
		return data;
	}


	@Override
	public Iterator<T> iterator() {
		Iterator<T> iterator = new Iterator<T>() {
			int origCount = modCount;
			LinkedMinHeap<T> copy;
			{
				copy = new LinkedMinHeap<>();
				helper(root);
			}

			private void helper(MinHeapNode n) {
				if (n == null) {
					return;
				}
				copy.insert(n.data);
				helper(n.lChild);
				helper(n.rChild);
			}

			@Override
			public boolean hasNext() {
				boolean isEmpty = copy.isEmpty();
				return !isEmpty;
			}
	  
			@Override
			public T next() {
				T target;
				if (modCount != origCount) {
					throw new ConcurrentModificationException();
				}
				try {
					// pop off the top of the heap copy
					target = copy.deleteMin();
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
