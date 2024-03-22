package pqueue.priorityqueues;

import org.junit.Test;

import pqueue.exceptions.InvalidPriorityException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;

public class PriorityQueueTests {
    PriorityQueue<Integer> queue;

    @Test
    public void test1() throws InvalidPriorityException, EmptyPriorityQueueException {
        queue = new MinHeapPriorityQueue<>();
        subtest1();
    }

    @Test
    public void test2() throws InvalidPriorityException, EmptyPriorityQueueException {
        queue = new LinearPriorityQueue<>();
        subtest1();
    }

    public void subtest1() throws InvalidPriorityException, EmptyPriorityQueueException {
        int[] nums = {10, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < nums.length; i++) {
            queue.enqueue(nums[i], 1); 
        }
        assertEquals(queue.size(), nums.length);
        assertEquals(queue.toString(), "10,1,2,3,4,5,6,7,8,9,");
        var it = queue.iterator();
        it.next();
        queue.enqueue(11, 1);
        //queue.dequeue();
        try {
            var y = it.next();
            System.out.println(y);
        } catch (ConcurrentModificationException e) {
            assertTrue(e.toString().trim().contains("java.util.ConcurrentModificationException"));
        }
        int s = queue.size();
        for (int i = 0; i < s; i++) {
            queue.dequeue();
        }
        assertTrue(queue.isEmpty());
        for (int i = 0; i < nums.length; i++) {
            queue.enqueue(nums[i], 11 - nums[i]); 
        }
        assertEquals(queue.toString(), "10,9,8,7,6,5,4,3,2,1,");
    }
    
}
