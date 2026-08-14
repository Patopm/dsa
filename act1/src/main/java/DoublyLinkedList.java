import java.util.Objects;

public class DoublyLinkedList<T> extends LinkedList<T> {
    private Node<T> tail;

    @Override
    public void insert(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

        tail.setNext(node);
        node.setPrev(tail);
        tail = node;
    }

    @Override
    public void delete(T data) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.getData(), data)) {
                unlink(current);
                return;
            }
            current = current.getNext();
        }
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.getPrev();
        Node<T> next = node.getNext();

        if (previous != null) {
            previous.setNext(next);
        } else {
            head = next;
        }

        if (next != null) {
            next.setPrev(previous);
        } else {
            tail = previous;
        }

        node.setPrev(null);
        node.setNext(null);
    }

    @Override
    protected String linkSeparator() {
        return " <-> ";
    }
}
