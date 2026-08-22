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
        assertEquals("[]", list.toString());
        assertNull(list.peekFront());
        assertNull(list.removeFront());
    }

    @Test
    void insertBackKeepsInsertionOrder() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insertBack(1);
        list.insertBack(2);
        list.insertBack(3);
        assertEquals("[1 -> 2 -> 3]", list.toString());
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
    }

    @Test
    void removeFrontRemovesHeadOnly() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insertBack(1);
        list.insertBack(2);
        assertEquals(1, list.removeFront());
        assertEquals("[2]", list.toString());
        assertEquals(2, list.removeFront());
        assertEquals("[]", list.toString());
    }

    @Test
    void toStringShowsNullData() {
        LinkedList<Integer> list = new LinkedList<>();
        list.insertBack(null);
        list.insertBack(1);
        assertEquals("[null -> 1]", list.toString());
    }
}
