import java.util.Objects;

public class CircularDoublyLinkedList<T> extends LinkedList<T> {
    private Node<T> tail;

    @Override
    public void insert(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
            node.setNext(node);
            node.setPrev(node);
            return;
        }

        node.setPrev(tail);
        node.setNext(head);
        tail.setNext(node);
        head.setPrev(node);
        tail = node;
    }

    @Override
    public void delete(T data) {
        if (head == null) {
            return;
        }

        Node<T> current = head;
        do {
            if (Objects.equals(current.getData(), data)) {
                unlink(current);
                return;
            }
            current = current.getNext();
        } while (current != head);
    }

    private void unlink(Node<T> node) {
        if (head == tail) {
            head = null;
            tail = null;
            node.setNext(null);
            node.setPrev(null);
            return;
        }

        Node<T> previous = node.getPrev();
        Node<T> next = node.getNext();
        previous.setNext(next);
        next.setPrev(previous);

        if (node == head) {
            head = next;
        }
        if (node == tail) {
            tail = previous;
        }

        node.setNext(null);
        node.setPrev(null);
    }

    @Override
    protected String linkSeparator() {
        return " <-> ";
    }
}
