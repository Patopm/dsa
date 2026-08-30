public class Person {
    private final String name;
    private final String id;
    private final Grade grade;

    public Person(String name, String id, Grade grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Grade getGrade() {
        return grade;
    }

    public Person copy() {
        return new Person(name, id, grade);
    }

    @Override
    public String toString() {
        return id + ":" + name + ":" + grade;
    }
}
