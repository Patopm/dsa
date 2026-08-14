import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataTypeExamplesTest {

    private final DataTypeExamples examples = new DataTypeExamples();

    @Test
    void addPrimitivesInsertsBoxedPrimitiveValues() {
        LinkedList<Integer> list = new SinglyLinkedList<>();
        examples.addPrimitives(list);

        assertEquals("[10 -> 20 -> 30]", list.toString());
        assertTrue(list.search(20));
        assertFalse(list.search(99));
    }

    @Test
    void addComplexTypesInsertsStringValues() {
        LinkedList<String> list = new DoublyLinkedList<>();
        examples.addComplexTypes(list);

        assertEquals("[Mexico City <-> Guadalajara <-> Monterrey]", list.toString());
        assertTrue(list.search("Guadalajara"));
        list.delete("Mexico City");
        assertEquals("[Guadalajara <-> Monterrey]", list.toString());
    }

    @Test
    void addContactsInsertsContactObjects() {
        LinkedList<Contact> list = new CircularDoublyLinkedList<>();
        examples.addContacts(list);

        Contact ana = new Contact("Ana Perez", "Calle 1", "555-0101");
        Contact luis = new Contact("Luis Mora", "Calle 2", "555-0102");

        assertTrue(list.search(ana));
        assertTrue(list.search(luis));
        assertEquals(
                "[Ana Perez - Calle 1 - 555-0101 <-> Luis Mora - Calle 2 - 555-0102]",
                list.toString());

        list.delete(ana);
        assertFalse(list.search(ana));
        assertTrue(list.search(luis));
    }
}
