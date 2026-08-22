import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StackTest {

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
    void pushPopIsLifo() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        assertEquals("[b -> a]", stack.toString());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemove() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
        assertEquals("[2 -> 1]", stack.toString());
    }

    @Test
    void emptyPopPrintsAndReturnsNull() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.pop());
        assertEquals("Stack is empty." + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void emptyPeekPrintsAndReturnsNull() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.peek());
        assertEquals("Stack is empty." + System.lineSeparator(), capturedOut.toString());
    }
}
