package pqueue.heaps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;


public class HeapTests {

    @Test
    public void test1() throws EmptyHeapException {
        MinHeap<Integer> heap = new ArrayMinHeap<>();
        //MinHeap<Integer> heap = new LinkedMinHeap<>();
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < nums.length; i++) {
            heap.insert(nums[i]); 
        }
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



    }
    
}
