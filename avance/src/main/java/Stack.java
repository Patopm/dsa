import java.util.function.Consumer;

public class Stack<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void push(T data) {
        list.insertFront(data);
    }

    public T pop() {
        return list.removeFront();
    }

    public T peek() {
        return list.peekFront();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void forEachFromTop(Consumer<T> action) {
        list.forEach(action);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
