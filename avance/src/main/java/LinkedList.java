import java.util.function.Consumer;
import java.util.function.Predicate;

public class LinkedList<T> {
    private Node<T> head;
    private int size;

    public void insertFront(T data) {
        Node<T> node = new Node<>(data);
        node.setNext(head);
        head = node;
        size++;
    }

    public void insertBack(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            size++;
            return;
        }
        Node<T> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(node);
        size++;
    }

    public T removeFront() {
        if (head == null) {
            return null;
        }
        T data = head.getData();
        head = head.getNext();
        size--;
        return data;
    }

    public T peekFront() {
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public T findFirst(Predicate<T> match) {
        Node<T> current = head;
        while (current != null) {
            if (match.test(current.getData())) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    public T removeFirst(Predicate<T> match) {
        if (head == null) {
            return null;
        }
        if (match.test(head.getData())) {
            return removeFront();
        }
        Node<T> current = head;
        while (current.getNext() != null) {
            if (match.test(current.getNext().getData())) {
                T data = current.getNext().getData();
                current.setNext(current.getNext().getNext());
                size--;
                return data;
            }
            current = current.getNext();
        }
        return null;
    }

    public void forEach(Consumer<T> action) {
        Node<T> current = head;
        while (current != null) {
            action.accept(current.getData());
            current = current.getNext();
        }
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            result.append(String.valueOf(current.getData()));
            if (current.getNext() != null) {
                result.append(" -> ");
            }
            current = current.getNext();
        }
        result.append("]");
        return result.toString();
    }
}
