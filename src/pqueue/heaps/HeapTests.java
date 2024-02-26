package pqueue.heaps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;


public class HeapTests {
    MinHeap<Integer> heap;

    @Test
    public void test1() throws EmptyHeapException {
        heap = new ArrayMinHeap<>();
        subtest1();
    }
    public void test2() throws EmptyHeapException {
        heap = new LinkedMinHeap<>();
        subtest1();
    }
    public void subtest1() throws EmptyHeapException {
        int[] nums = {10, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < nums.length; i++) {
            heap.insert(nums[i]); 
        }
        assertEquals(heap.toString(), "1,2,3,4,5,6,7,8,9,10,");

        var it = heap.iterator();
        it.next();
        //heap.insert(11);
        heap.deleteMin();
        try {
            var y = it.next();
            System.out.println(y);
        } catch (ConcurrentModificationException e) {
            assertEquals(e.toString().trim(), "java.util.ConcurrentModificationException");
        }
        assertEquals(heap.toString(), "2,3,4,5,6,7,8,9,10,");
        int s = heap.size();
        for (int i = 0; i < s; i++) {
            heap.deleteMin();
        }
        assertTrue(heap.isEmpty());
        for (int i = 0; i < nums.length; i++) {
            heap.insert(nums[i]); 
        }
        assertEquals(heap.toString(), "1,2,3,4,5,6,7,8,9,10,");

    }
    
}
