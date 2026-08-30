import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StackTest {

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
    void emptyPopAndPeekReturnNull() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.pop());
        assertNull(stack.peek());
    }

    @Test
    void forEachFromTopDoesNotPop() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        StringBuilder visited = new StringBuilder();
        stack.forEachFromTop(visited::append);
        assertEquals("cba", visited.toString());
        assertEquals("[c -> b -> a]", stack.toString());
        assertEquals("c", stack.peek());
    }
}
