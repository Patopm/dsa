import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testNodeInitialization() {
        Node<Integer> node = new Node<>(1);
        assertEquals(1, node.getData());
        assertNull(node.getNext());
        assertNull(node.getPrev());
    }

    @Test
    void testNodeToString() {
        Node<Integer> node = new Node<>(1);
        assertEquals("Node: 1", node.toString());
    }

    @Test
    void testEmptyNodeToString() {
        Node<Integer> node = new Node<>(null);
        assertEquals("Node: null", node.toString());
    }

    @Test
    void testNodeNext() {
        Node<Integer> node = new Node<>(1);
        assertNull(node.getNext());
        node.setNext(new Node<>(2));
        assertEquals(2, node.getNext().getData());
    }

    @Test
    void testNodePrev() {
        Node<Integer> node = new Node<>(1);
        assertNull(node.getPrev());
        node.setPrev(new Node<>(0));
        assertEquals(0, node.getPrev().getData());
    }

    @Test
    void testNodeNextAndPrev() {
        Node<Integer> node = new Node<>(1);
        node.setNext(new Node<>(2));
        node.setPrev(new Node<>(0));
        assertEquals(2, node.getNext().getData());
        assertEquals(0, node.getPrev().getData());
    }
}
