import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoublyLinkedListTest {

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertEquals("[]", list.toString());
    }

    @Test
    void searchReturnsFalseWhenListIsEmpty() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertFalse(list.search(1));
    }

    @Test
    void insertAppendsValuesInOrder() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals("[1 <-> 2 <-> 3]", list.toString());
    }

    @Test
    void searchFindsInsertedValue() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.insert("a");
        list.insert("b");
        assertTrue(list.search("b"));
    }

    @Test
    void searchReturnsFalseWhenValueIsMissing() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.insert("a");
        assertFalse(list.search("c"));
    }

    @Test
    void deleteRemovesFirstOccurrence() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        assertEquals("[1 <-> 2 <-> 3]", list.toString());
    }

    @Test
    void deleteRemovesHead() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(1);
        assertEquals("[2]", list.toString());
    }

    @Test
    void deleteRemovesTail() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(2);
        assertEquals("[1]", list.toString());
    }

    @Test
    void deleteOnEmptyListIsNoOp() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.delete(1);
        assertEquals("[]", list.toString());
    }

    @Test
    void deleteMissingValueIsNoOp() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.delete(9);
        assertEquals("[1]", list.toString());
    }

    @Test
    void supportsNullValues() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(null);
        list.insert(1);
        assertTrue(list.search(null));
        assertEquals("[null <-> 1]", list.toString());
        list.delete(null);
        assertFalse(list.search(null));
        assertEquals("[1]", list.toString());
    }

    @Test
    void displayPrintsStringRepresentation() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.display();
        assertEquals("[1 <-> 2]" + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void deleteMiddleKeepsNeighborsLinked() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        list.delete(1);
        list.delete(3);
        assertEquals("[]", list.toString());
    }
}
