public class Incident {
    private final String description;
    private final Person person;

    public Incident(String description, Person person) {
        this.description = description;
        this.person = person.copy();
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return person + " - " + description;
    }
}
