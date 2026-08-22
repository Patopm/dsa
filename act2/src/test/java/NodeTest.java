import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NodeTest {

    @Test
    void storesDataAndNullNext() {
        Node<Integer> node = new Node<>(1);
        assertEquals(1, node.getData());
        assertNull(node.getNext());
    }

    @Test
    void linksToNextNode() {
        Node<String> first = new Node<>("a");
        first.setNext(new Node<>("b"));
        assertEquals("b", first.getNext().getData());
    }
}
