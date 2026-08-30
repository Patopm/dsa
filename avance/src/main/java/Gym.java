public class Gym {
    public static final int DEFAULT_CAPACITY = 30;

    private final int capacity;
    private final LinkedList<Person> inside = new LinkedList<>();
    private final Queue<Person> waiting = new Queue<>();
    private final Stack<Incident> incidents = new Stack<>();

    public Gym(int capacity) {
        this.capacity = capacity;
    }

    public int capacity() {
        return capacity;
    }

    public int insideCount() {
        return inside.size();
    }

    public ArriveResult arrive(Person person) {
        if (containsId(person.getId())) {
            return new ArriveResult(ArriveResult.Status.DUPLICATE, person);
        }
        if (inside.size() < capacity) {
            inside.insertBack(person);
            return new ArriveResult(ArriveResult.Status.ENTERED, person);
        }
        waiting.enqueue(person);
        return new ArriveResult(ArriveResult.Status.QUEUED, person);
    }

    public LeaveResult leave(String id) {
        Person left = inside.removeFirst(person -> person.getId().equals(id));
        if (left == null) {
            return new LeaveResult(LeaveResult.Status.NOT_INSIDE, null, null);
        }
        if (waiting.isEmpty()) {
            return new LeaveResult(LeaveResult.Status.LEFT, left, null);
        }
        Person entered = waiting.dequeue();
        inside.insertBack(entered);
        return new LeaveResult(LeaveResult.Status.LEFT_AND_ENTERED, left, entered);
    }

    public Person cancelWait(String id) {
        return waiting.removeFirst(person -> person.getId().equals(id));
    }

    public Person waitingFront() {
        return waiting.front();
    }

    public String insideString() {
        return inside.toString();
    }

    public String waitingString() {
        return waiting.toString();
    }

    private boolean containsId(String id) {
        if (inside.findFirst(person -> person.getId().equals(id)) != null) {
            return true;
        }
        return waiting.containsMatch(person -> person.getId().equals(id));
    }

    public static final class ArriveResult {
        public enum Status {
            ENTERED,
            QUEUED,
            DUPLICATE
        }

        private final Status status;
        private final Person person;

        public ArriveResult(Status status, Person person) {
            this.status = status;
            this.person = person;
        }

        public Status getStatus() {
            return status;
        }

        public Person getPerson() {
            return person;
        }
    }

    public static final class LeaveResult {
        public enum Status {
            NOT_INSIDE,
            LEFT,
            LEFT_AND_ENTERED
        }

        private final Status status;
        private final Person left;
        private final Person entered;

        public LeaveResult(Status status, Person left, Person entered) {
            this.status = status;
            this.left = left;
            this.entered = entered;
        }

        public Status getStatus() {
            return status;
        }

        public Person getLeft() {
            return left;
        }

        public Person getEntered() {
            return entered;
        }
    }
}
