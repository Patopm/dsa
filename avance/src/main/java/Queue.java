import java.util.function.Predicate;

public class Queue<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void enqueue(T data) {
        list.insertBack(data);
    }

    public T dequeue() {
        return list.removeFront();
    }

    public T front() {
        return list.peekFront();
    }

    public T removeFirst(Predicate<T> match) {
        return list.removeFirst(match);
    }

    public boolean containsMatch(Predicate<T> match) {
        return list.findFirst(match) != null;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
