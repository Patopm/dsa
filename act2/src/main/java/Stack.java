public class Stack<T> {
    private LinkedList<T> list = new LinkedList<>();

    public void push(T data) {
        list.insertFront(data);
    }

    public T pop() {
        if (list.isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }
        return list.removeFront();
    }

    public T peek() {
        if (list.isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }
        return list.peekFront();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
