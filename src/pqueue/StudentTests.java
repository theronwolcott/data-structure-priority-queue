package pqueue;

import org.junit.Test;
import pqueue.exceptions.InvalidCapacityException;
import pqueue.exceptions.InvalidPriorityException;
import pqueue.heaps.ArrayMinHeap;
import pqueue.heaps.EmptyHeapException;
import pqueue.heaps.LinkedMinHeap;
import pqueue.heaps.MinHeap;
import pqueue.priorityqueues.EmptyPriorityQueueException;
import pqueue.priorityqueues.LinearPriorityQueue;
import pqueue.priorityqueues.MinHeapPriorityQueue;
import pqueue.priorityqueues.PriorityQueue;

import static org.junit.Assert.*;

/**
 * {@link StudentTests} is a {@code jUnit} testing library which you should extend with your own tests.
 *
 * @author  <a href="https://github.com/JasonFil">Jason Filippou</a> and --- YOUR NAME HERE! ----
 */
public class StudentTests {

    private static String throwableInfo(Throwable thrown){
        return "Caught a " + thrown.getClass().getSimpleName() +
                " with message: " + thrown.getMessage();
    }

    private MinHeap<String> myHeap;
    private PriorityQueue<String> myQueue;

    @Test
    public void testLinkedListMinHeap() throws EmptyHeapException {
        MinHeap<Integer> testHeap = new LinkedMinHeap<>();

        assertTrue(testHeap.isEmpty()); // test isEmpty

        testHeap.insert(2);
        testHeap.insert(1);
        System.out.println(testHeap);
        testHeap.insert(0);
        System.out.println(testHeap);
        assertFalse(testHeap.isEmpty()); // test isEmpty

        testHeap.insert(0);
        System.out.println(testHeap);
        assertTrue(testHeap.size() == 4); // test size
        testHeap.insert(0);
        System.out.println(testHeap);
        testHeap.insert(0);
        System.out.println(testHeap);
        assertTrue(testHeap.getMin() == 0);

        testHeap.insert(10);
        testHeap.insert(7);
        testHeap.insert(4);
        testHeap.insert(2);
        System.out.println(testHeap);
        System.out.println(testHeap.deleteMin());
        System.out.println(testHeap);

        while (testHeap.size() > 0) {
            System.out.print(testHeap.deleteMin() + ", ");
        }
        assertTrue(testHeap.isEmpty()); // test isEmpty

    }
        @Test
    public void testArrayMinHeap() throws EmptyHeapException {
        ArrayMinHeap<Integer> testHeap = new ArrayMinHeap<>();

        testHeap.insert(0);
        assertFalse(testHeap.isEmpty()); // test isEmpty

        testHeap.insert(1);
        testHeap.insert(2);
        testHeap.insert(3);
        System.out.println(testHeap);
        //testHeap.print(testHeap);
        assertTrue(testHeap.size() == 4); // test size

        assertTrue(testHeap.getMin() == 0);

        testHeap.insert(10);
        testHeap.insert(7);
        testHeap.insert(4);
        testHeap.insert(2);
        System.out.println(testHeap);
        //testHeap.print(testHeap);

    }
    @Test
    public void initAndAddOneElement() throws InvalidPriorityException {
        try {
            myHeap = new ArrayMinHeap<>();
            myQueue = new MinHeapPriorityQueue<>();
        } catch(Throwable t){
            fail(throwableInfo(t));
        }
        assertTrue("After initialization, all MinHeap and PriorityQueue implementations should report that they are empty.",
                myHeap.isEmpty() && myQueue.isEmpty());
        assertTrue("After initialization, all MinHeap and PriorityQueue implementations should report a size of 0.",
                (myHeap.size() == 0) && (myQueue.size() == 0));
        myHeap.insert("Mary");
        assertEquals("After inserting an element, ArrayMinHeap instances should report a size of 1.", 1, myHeap.size());

        // MinHeap::enqueue() declares that it checks InvalidPriorityException if priority <= 0 (from the docs of MinHeap).
        // In this case, we know for sure that InvalidPriorityException should *not* be thrown, since priority = 2 >= 0.
        // To avoid cluttering a code with "dummy" try-catch blocks, we declare InvalidPriorityException as checked from
        // this test as well. This is why we have the throws declaration after the name of the test.
        myQueue.enqueue("Jason", 2);
        assertEquals("After inserting an element, MinHeapPriorityQueue instances should report a size of 1.", 1, myQueue.size());
    }

    // Here is one simple way to write tests that expect an Exception to be thrown. Another, more powerful method is to
    // use the class org.junit.rules.ExpectedException: https://junit.org/junit4/javadoc/4.12/org/junit/rules/ExpectedException.html
    @Test(expected = InvalidCapacityException.class)
    public void ensureInvalidCapacityExceptionThrown() throws InvalidCapacityException{
         myQueue = new LinearPriorityQueue<>(-2);
    }

    @Test(expected = InvalidPriorityException.class)
    public void ensureInvalidPriorityExceptionThrown() throws InvalidPriorityException, InvalidCapacityException{
        myQueue = new LinearPriorityQueue<>(4);
        myQueue.enqueue("Billy", -1);
    }

    @Test
    public void testEnqueingOrder() throws InvalidPriorityException, EmptyPriorityQueueException {
        myQueue = new MinHeapPriorityQueue<>();
        myQueue.enqueue("Ashish", 8);
        myQueue.enqueue("Diana", 2);        // Lower priority, so should be up front.
        myQueue.enqueue("Adam", 2);        // Same priority, but should be second because of FIFO.
        assertEquals("We were expecting Diana up front.", "Diana", myQueue.getFirst());
    }
    @Test
    public void testEnqueingOrder2() throws InvalidPriorityException, EmptyPriorityQueueException {
        myQueue = new LinearPriorityQueue<>();
        myQueue.enqueue("Ashish", 8);
        myQueue.enqueue("Diana", 2);        // Lower priority, so should be up front.
        myQueue.enqueue("Adam", 2);        // Same priority, but should be second because of FIFO.
        assertEquals("We were expecting Diana up front.", "Diana", myQueue.getFirst());
    }

    @Test
    public void testDequeuingOrder() throws InvalidPriorityException, EmptyPriorityQueueException {
        testEnqueingOrder();    // To populate myQueue with the same elements.
        myQueue.dequeue();      // Now Adam should be up front.
        assertEquals("We were expecting Adam up front.", "Adam", myQueue.getFirst());
    }
    @Test
    public void testDequeuingOrder2() throws InvalidPriorityException, EmptyPriorityQueueException {
        testEnqueingOrder2();    // To populate myQueue with the same elements.
        assertEquals("Diana", myQueue.dequeue());      // Now Adam should be up front.
        assertEquals("We were expecting Adam up front.", "Adam", myQueue.getFirst());
        myQueue.dequeue();
        myQueue.dequeue();
        assertTrue(myQueue.isEmpty());
    }

    @Test
    public void testIterator() throws InvalidPriorityException, EmptyPriorityQueueException { 
        testEnqueingOrder();    // To populate myQueue with the same elements.
        String[] names = {"Diana", "Adam", "Ashish"};
        var iter = myQueue.iterator();
        for (int i = 0; i < names.length; i++) {
            assertTrue(iter.hasNext());
            assertEquals(iter.next(), names[i]);
        }
        assertFalse(iter.hasNext());
    }
        @Test
    public void testIterator2() throws InvalidPriorityException, EmptyPriorityQueueException { 
        testEnqueingOrder2();    // To populate myQueue with the same elements.
        String[] names = {"Diana", "Adam", "Ashish"};
        var iter = myQueue.iterator();
        for (int i = 0; i < names.length; i++) {
            assertTrue(iter.hasNext());
            assertEquals(iter.next(), names[i]);
        }
        assertFalse(iter.hasNext());
    }

    /* ******************************************************************************************************** */
    /* ********************** YOU SHOULD ADD TO THESE UNIT TESTS BELOW. *************************************** */
    /* ******************************************************************************************************** */
}
