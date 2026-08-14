import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class LinkedList<T> {
    protected Node<T> head;

    public abstract void insert(T data);

    public abstract void delete(T data);

    public boolean search(T data) {
        for (T value : values()) {
            if (Objects.equals(value, data)) {
                return true;
            }
        }
        return false;
    }

    public T findFirst(Predicate<T> match) {
        for (T value : values()) {
            if (match.test(value)) {
                return value;
            }
        }
        return null;
    }

    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        List<T> values = values();
        if (values.isEmpty()) {
            return "[]";
        }

        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(linkSeparator(), "[", "]"));
    }

    protected String linkSeparator() {
        return " -> ";
    }

    private List<T> values() {
        List<T> values = new ArrayList<>();
        if (head == null) {
            return values;
        }

        Node<T> current = head;
        do {
            values.add(current.getData());
            current = current.getNext();
        } while (current != null && current != head);
        return values;
    }
}
