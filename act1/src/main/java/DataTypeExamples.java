public class DataTypeExamples {
    public void addPrimitives(LinkedList<Integer> list) {
        list.insert(10);
        list.insert(20);
        list.insert(30);
    }

    public void addComplexTypes(LinkedList<String> list) {
        list.insert("Mexico City");
        list.insert("Guadalajara");
        list.insert("Monterrey");
    }

    public void addContacts(LinkedList<Contact> list) {
        list.insert(new Contact("Ana Perez", "Calle 1", "555-0101"));
        list.insert(new Contact("Luis Mora", "Calle 2", "555-0102"));
    }
}
