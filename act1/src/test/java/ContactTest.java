import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactTest {

    @Test
    void storesNameAddressAndPhone() {
        Contact contact = new Contact("Ana", "Calle 1", "555-0101");
        assertEquals("Ana", contact.getName());
        assertEquals("Calle 1", contact.getAddress());
        assertEquals("555-0101", contact.getPhone());
    }

    @Test
    void toStringIncludesAllFields() {
        Contact contact = new Contact("Ana", "Calle 1", "555-0101");
        assertEquals("Ana - Calle 1 - 555-0101", contact.toString());
    }

    @Test
    void equalsAndHashCodeUseAllFields() {
        Contact ana = new Contact("Ana", "Calle 1", "555-0101");
        Contact sameAna = new Contact("Ana", "Calle 1", "555-0101");
        Contact luis = new Contact("Luis", "Calle 2", "555-0102");

        assertEquals(ana, sameAna);
        assertEquals(ana.hashCode(), sameAna.hashCode());
        assertNotEquals(ana, luis);
    }

    @Test
    void canBeStoredSearchedAndDeletedInALinkedList() {
        LinkedList<Contact> list = new SinglyLinkedList<>();
        Contact ana = new Contact("Ana", "Calle 1", "555-0101");
        Contact luis = new Contact("Luis", "Calle 2", "555-0102");

        list.insert(ana);
        list.insert(luis);

        assertTrue(list.search(new Contact("Ana", "Calle 1", "555-0101")));
        list.delete(new Contact("Ana", "Calle 1", "555-0101"));
        assertFalse(list.search(ana));
        assertEquals("[Luis - Calle 2 - 555-0102]", list.toString());
    }

    @Test
    void canBeFoundAndDeletedByName() {
        LinkedList<Contact> list = new SinglyLinkedList<>();
        list.insert(new Contact("Ana", "Calle 1", "555-0101"));
        list.insert(new Contact("Luis", "Calle 2", "555-0102"));

        Contact found = list.findFirst(contact -> contact.getName().equals("Ana"));
        assertEquals(new Contact("Ana", "Calle 1", "555-0101"), found);

        list.delete(found);
        assertNull(list.findFirst(contact -> contact.getName().equals("Ana")));
        assertEquals("[Luis - Calle 2 - 555-0102]", list.toString());
    }
}
