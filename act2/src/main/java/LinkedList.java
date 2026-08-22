public class LinkedList<T> {
    private Node<T> head;

    public void insertFront(T data) {
        Node<T> node = new Node<>(data);
        node.setNext(head);
        head = node;
    }

    public void insertBack(T data) {
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

    public T removeFront() {
        if (head == null) {
            return null;
        }
        T data = head.getData();
        head = head.getNext();
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
