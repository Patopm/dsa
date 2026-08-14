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

class CircularLinkedListTest {

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
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        assertEquals("[]", list.toString());
    }

    @Test
    void searchReturnsFalseWhenListIsEmpty() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        assertFalse(list.search(1));
    }

    @Test
    void insertAppendsValuesInOrder() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertEquals("[1 -> 2 -> 3]", list.toString());
    }

    @Test
    void searchFindsInsertedValue() {
        CircularLinkedList<String> list = new CircularLinkedList<>();
        list.insert("a");
        list.insert("b");
        assertTrue(list.search("b"));
    }

    @Test
    void searchReturnsFalseWhenValueIsMissing() {
        CircularLinkedList<String> list = new CircularLinkedList<>();
        list.insert("a");
        assertFalse(list.search("c"));
    }

    @Test
    void deleteRemovesFirstOccurrence() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(3);
        list.delete(2);
        assertEquals("[1 -> 2 -> 3]", list.toString());
    }

    @Test
    void deleteRemovesHead() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(1);
        assertEquals("[2]", list.toString());
    }

    @Test
    void deleteRemovesTail() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.delete(2);
        assertEquals("[1]", list.toString());
    }

    @Test
    void deleteOnEmptyListIsNoOp() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.delete(1);
        assertEquals("[]", list.toString());
    }

    @Test
    void deleteMissingValueIsNoOp() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.delete(9);
        assertEquals("[1]", list.toString());
    }

    @Test
    void supportsNullValues() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
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
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.display();
        assertEquals("[1 -> 2]" + System.lineSeparator(), capturedOut.toString());
    }

    @Test
    void singleNodePointsToItself() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        Node<Integer> head = headOf(list);
        assertSame(head, head.getNext());
        assertEquals("[1]", list.toString());
    }

    @Test
    void lastNodeWrapsAroundToHead() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        Node<Integer> head = headOf(list);
        assertSame(head, lastNode(head).getNext());
    }

    @Test
    void wrapAroundTraversalVisitsHeadAgain() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);

        Node<Integer> head = headOf(list);
        List<Integer> visited = new ArrayList<>();
        Node<Integer> current = head;
        for (int i = 0; i < 5; i++) {
            visited.add(current.getData());
            current = current.getNext();
        }

        assertEquals(List.of(1, 2, 3, 1, 2), visited);
    }

    @Test
    void deleteHeadKeepsCircularLink() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(1);
        Node<Integer> head = headOf(list);
        assertSame(head, lastNode(head).getNext());
        assertEquals("[2 -> 3]", list.toString());
    }

    @Test
    void deleteTailKeepsCircularLink() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.delete(3);
        Node<Integer> head = headOf(list);
        assertSame(head, lastNode(head).getNext());
        assertEquals("[1 -> 2]", list.toString());
    }

    @Test
    void deleteOnlyElementLeavesEmptyList() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.delete(1);
        assertEquals("[]", list.toString());
        assertFalse(list.search(1));
    }

    @SuppressWarnings("unchecked")
    private static Node<Integer> headOf(CircularLinkedList<Integer> list) throws Exception {
        Field head = LinkedList.class.getDeclaredField("head");
        head.setAccessible(true);
        return (Node<Integer>) head.get(list);
    }

    private static Node<Integer> lastNode(Node<Integer> head) {
        Node<Integer> current = head;
        while (current.getNext() != head) {
            current = current.getNext();
        }
        return current;
    }
}
