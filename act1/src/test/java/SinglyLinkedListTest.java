import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SinglyLinkedListTest {

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
    void emptyListHasEmptyStringRepresentation() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertEquals("[]", list.toString());
    }

    @Test
    void searchReturnsFalseWhenListIsEmpty() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertFalse(list.search(1));
    }

    @Test
    void insertAppendsValuesInOrder() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals("[1 -> 2 -> 3]", list.toString());
    }

    @Test
    void searchFindsInsertedValue() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insert("a");
        list.insert("b");
        assertTrue(list.search("b"));
    }

    @Test
    void searchReturnsFalseWhenValueIsMissing() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insert("a");
        assertFalse(list.search("c"));
    }

    @Test
    void deleteRemovesFirstOccurrence() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        assertEquals("[1 -> 2 -> 3]", list.toString());
    }

    @Test
    void deleteRemovesHead() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(1);
        assertEquals("[2]", list.toString());
    }

    @Test
    void deleteRemovesTail() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(2);
        assertEquals("[1]", list.toString());
    }

    @Test
    void deleteOnEmptyListIsNoOp() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.delete(1);
        assertEquals("[]", list.toString());
    }

    @Test
    void deleteMissingValueIsNoOp() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.delete(9);
        assertEquals("[1]", list.toString());
    }

    @Test
    void supportsNullValues() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(null);
        list.insert(1);
        assertTrue(list.search(null));
        assertEquals("[null -> 1]", list.toString());
        list.delete(null);
        assertFalse(list.search(null));
        assertEquals("[1]", list.toString());
    }

    @Test
    void displayPrintsStringRepresentation() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.display();
        assertEquals("[1 -> 2]" + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void findFirstReturnsMatchingValue() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals(2, list.findFirst(value -> value == 2));
    }

    @Test
    void findFirstReturnsNullWhenMissing() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insert(1);
        assertNull(list.findFirst(value -> value == 9));
    }
}
