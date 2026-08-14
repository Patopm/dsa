import java.util.Objects;

public class CircularLinkedList<T> extends LinkedList<T> {
    private Node<T> tail;

    @Override
    public void insert(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
            node.setNext(node);
            return;
        }

        tail.setNext(node);
        node.setNext(head);
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
            return;
        }

        Node<T> previous = tail;
        Node<T> current = head;
        while (current != node) {
            previous = current;
            current = current.getNext();
        }

        previous.setNext(node.getNext());
        if (node == head) {
            head = node.getNext();
        }
        if (node == tail) {
            tail = previous;
        }
        node.setNext(null);
    }
}
