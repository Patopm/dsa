import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueueTest {

    @Test
    void enqueueDequeueIsFifo() {
        Queue<String> queue = new Queue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        assertEquals("[a -> b]", queue.toString());
        assertEquals("a", queue.dequeue());
        assertEquals("b", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void frontDoesNotRemove() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.front());
        assertEquals("[1 -> 2]", queue.toString());
    }

    @Test
    void emptyDequeueAndFrontReturnNull() {
        Queue<Integer> queue = new Queue<>();
        assertNull(queue.dequeue());
        assertNull(queue.front());
        assertTrue(queue.isEmpty());
    }

    @Test
    void removeFirstCanCancelSomeoneInTheMiddle() {
        Queue<Person> queue = new Queue<>();
        queue.enqueue(new Person("Ana", "A001", Grade.PREPA));
        queue.enqueue(new Person("Luis", "A002", Grade.UNI));
        queue.enqueue(new Person("Mia", "A003", Grade.PREPA));
        Person removed = queue.removeFirst(person -> person.getId().equals("A002"));
        assertEquals("A002", removed.getId());
        assertEquals("A001", queue.front().getId());
        assertFalse(queue.containsMatch(person -> person.getId().equals("A002")));
        assertTrue(queue.containsMatch(person -> person.getId().equals("A003")));
    }
}
