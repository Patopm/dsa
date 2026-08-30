import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListTest {

    @Test
    void emptyListLooksEmpty() {
        LinkedList<Integer> list = new LinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
        assertNull(list.peekFront());
        assertNull(list.removeFront());
        assertNull(list.findFirst(value -> true));
        assertNull(list.removeFirst(value -> true));
    }

    @Test
    void insertBackKeepsInsertionOrder() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insertBack(1);
        list.insertBack(2);
        list.insertBack(3);
        assertEquals("[1 -> 2 -> 3]", list.toString());
        assertEquals(3, list.size());
        assertEquals(1, list.peekFront());
        assertFalse(list.isEmpty());
    }

    @Test
    void insertFrontPutsNewValueAtHead() {
        LinkedList<String> list = new LinkedList<>();
        list.insertFront("b");
        list.insertFront("a");
        assertEquals("[a -> b]", list.toString());
        assertEquals("a", list.peekFront());
        assertEquals(2, list.size());
    }

    @Test
    void removeFrontRemovesHeadOnly() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insertBack(1);
        list.insertBack(2);
        assertEquals(1, list.removeFront());
        assertEquals("[2]", list.toString());
        assertEquals(1, list.size());
        assertEquals(2, list.removeFront());
        assertEquals("[]", list.toString());
        assertEquals(0, list.size());
    }

    @Test
    void findFirstAndRemoveFirstWorkInTheMiddle() {
        LinkedList<String> list = new LinkedList<>();
        list.insertBack("a");
        list.insertBack("b");
        list.insertBack("c");
        assertEquals("b", list.findFirst("b"::equals));
        assertEquals("b", list.removeFirst("b"::equals));
        assertEquals("[a -> c]", list.toString());
        assertEquals(2, list.size());
        assertNull(list.findFirst("b"::equals));
    }

    @Test
    void forEachVisitsFrontToBack() {
        LinkedList<String> list = new LinkedList<>();
        list.insertBack("a");
        list.insertBack("b");
        StringBuilder visited = new StringBuilder();
        list.forEach(visited::append);
        assertEquals("ab", visited.toString());
    }
}
