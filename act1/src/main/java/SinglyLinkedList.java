import java.util.Objects;

public class SinglyLinkedList<T> extends LinkedList<T> {
    @Override
    public void insert(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(node);
    }

    @Override
    public void delete(T data) {
        if (head == null) {
            return;
        }

        if (Objects.equals(head.getData(), data)) {
            head = head.getNext();
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (Objects.equals(current.getNext().getData(), data)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }
}
