public class Queue<T> {
    private LinkedList<T> list = new LinkedList<>();

    public void enqueue(T data) {
        list.insertBack(data);
    }

    public T dequeue() {
        if (list.isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        return list.removeFront();
    }

    public T peek() {
        if (list.isEmpty()) {
            System.out.println("Queue is empty.");
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
