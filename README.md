# Heaps and Priority Queues Project

## Overview
This project involves implementing several core data structures for managing priority queues using heaps. The goal is to practice:
- Binary heaps (linked and array-based)
- Priority queues (linear and heap-based)
- Iterator functionality

## Implemented Classes

1. **LinkedMinHeap**
   - A linked structure implementing a min-heap.
   - Supports insertion, deletion, and iteration in level-order.

2. **ArrayMinHeap**
   - An array-based min-heap using contiguous storage.
   - Provides efficient memory representation and operations.

3. **LinearPriorityQueue**
   - A linear implementation of a priority queue.
   - Enqueues elements based on their priority in O(n) time.

4. **MinHeapPriorityQueue**
   - A priority queue implemented using a binary min-heap.
   - Ensures efficient enqueue and dequeue operations in O(log n) time.

## Key Features

### Heaps
- **Insertion:** Elements are inserted in the "leftmost" unoccupied space and percolated up to maintain the min-heap invariant.
- **Deletion:** Always deletes the root element, replaces it with the last element, and percolates down to restore heap properties.
- **Efficient Representation:** Array-based heaps utilize a level-order traversal for compact storage, with efficient child and parent indexing.

### Priority Queues
- **LinearPriorityQueue:** Maintains a sorted order based on priorities (ascending). 
- **MinHeapPriorityQueue:** Builds on the min-heap implementation for fast priority management.

### Iterators
- Fail-fast iterators detect and respond to concurrent modifications.
- Iteration for heaps follows ascending order.
- Iteration for priority queues follows priority-FIFO order.

## Prerequisites
- Knowledge of binary search trees, heaps, stacks, and queues.
- Familiarity with Java programming, including iterators, exceptions, and interfaces.

## Notes
- `ArrayMinHeap` and `MinHeapPriorityQueue` strictly adhere to contiguous storage and heap-based implementation requirements.
- The iterators throw `ConcurrentModificationException` and `NoSuchElementException` when appropriate.


 
