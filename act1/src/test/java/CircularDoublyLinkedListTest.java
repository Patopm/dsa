import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularDoublyLinkedListTest {

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
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        assertEquals("[]", list.toString());
    }

    @Test
    void searchReturnsFalseWhenListIsEmpty() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        assertFalse(list.search(1));
    }

    @Test
    void insertAppendsValuesInOrder() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals("[1 <-> 2 <-> 3]", list.toString());
    }

    @Test
    void searchFindsInsertedValue() {
        CircularDoublyLinkedList<String> list = new CircularDoublyLinkedList<>();
        list.insert("a");
        list.insert("b");
        assertTrue(list.search("b"));
    }

    @Test
    void searchReturnsFalseWhenValueIsMissing() {
        CircularDoublyLinkedList<String> list = new CircularDoublyLinkedList<>();
        list.insert("a");
        assertFalse(list.search("c"));
    }

    @Test
    void deleteRemovesFirstOccurrence() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        assertEquals("[1 <-> 2 <-> 3]", list.toString());
    }

    @Test
    void deleteRemovesHead() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(1);
        assertEquals("[2]", list.toString());
    }

    @Test
    void deleteRemovesTail() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(2);
        assertEquals("[1]", list.toString());
    }

    @Test
    void deleteOnEmptyListIsNoOp() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.delete(1);
        assertEquals("[]", list.toString());
    }

    @Test
    void deleteMissingValueIsNoOp() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.delete(9);
        assertEquals("[1]", list.toString());
    }

    @Test
    void supportsNullValues() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
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
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.display();
        assertEquals("[1 <-> 2]" + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void deleteMiddleKeepsNeighborsLinked() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        list.delete(1);
        list.delete(3);
        assertEquals("[]", list.toString());
    }

    @Test
    void singleNodePointsToItselfInBothDirections() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        Node<Integer> head = headOf(list);
        assertSame(head, head.getNext());
        assertSame(head, head.getPrev());
        assertEquals("[1]", list.toString());
    }

    @Test
    void headAndTailWrapAroundInBothDirections() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        Node<Integer> head = headOf(list);
        Node<Integer> tail = head.getPrev();
        assertEquals(3, tail.getData());
        assertSame(head, tail.getNext());
        assertSame(tail, head.getPrev());
    }

    @Test
    void wrapAroundForwardTraversalVisitsHeadAgain() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);

        Node<Integer> current = headOf(list);
        List<Integer> visited = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            visited.add(current.getData());
            current = current.getNext();
        }

        assertEquals(List.of(1, 2, 3, 1, 2), visited);
    }

    @Test
    void wrapAroundBackwardTraversalVisitsHeadAgain() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);

        Node<Integer> current = headOf(list);
        List<Integer> visited = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            visited.add(current.getData());
            current = current.getPrev();
        }

        assertEquals(List.of(1, 3, 2, 1, 3), visited);
    }

    @Test
    void deleteHeadKeepsCircularLinks() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(1);
        Node<Integer> head = headOf(list);
        assertSame(head, head.getPrev().getNext());
        assertSame(head, head.getNext().getPrev());
        assertEquals("[2 <-> 3]", list.toString());
    }

    @Test
    void deleteTailKeepsCircularLinks() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(3);
        Node<Integer> head = headOf(list);
        assertSame(head, head.getPrev().getNext());
        assertSame(head, head.getNext().getPrev());
        assertEquals("[1 <-> 2]", list.toString());
    }

    @Test
    void deleteMiddleKeepsPrevAndNextCircular() throws Exception {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        Node<Integer> head = headOf(list);
        assertSame(head.getNext(), head.getPrev());
        assertSame(head, head.getNext().getNext());
        assertSame(head, head.getPrev().getPrev());
        assertEquals("[1 <-> 3]", list.toString());
    }

    @Test
    void deleteOnlyElementLeavesEmptyList() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        list.insert(1);
        list.delete(1);
        assertEquals("[]", list.toString());
        assertFalse(list.search(1));
    }

    @SuppressWarnings("unchecked")
    private static Node<Integer> headOf(CircularDoublyLinkedList<Integer> list) throws Exception {
        Field head = LinkedList.class.getDeclaredField("head");
        head.setAccessible(true);
        return (Node<Integer>) head.get(list);
    }
}
