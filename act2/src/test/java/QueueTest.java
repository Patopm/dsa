import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueueTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void captureStdout() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

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
    void peekDoesNotRemove() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.peek());
        assertEquals("[1 -> 2]", queue.toString());
    }

    @Test
    void emptyDequeuePrintsAndReturnsNull() {
        Queue<Integer> queue = new Queue<>();
        assertNull(queue.dequeue());
        assertEquals("Queue is empty." + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void emptyPeekPrintsAndReturnsNull() {
        Queue<Integer> queue = new Queue<>();
        assertNull(queue.peek());
        assertEquals("Queue is empty." + System.lineSeparator(), capturedOut.toString());
    }
}
