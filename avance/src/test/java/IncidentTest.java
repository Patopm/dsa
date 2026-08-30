import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class IncidentTest {

    @Test
    void toStringIncludesPersonAndDescription() {
        Person person = new Person("Ana", "A001", Grade.PREPA);
        Incident incident = new Incident("caida", person);
        assertEquals("A001:Ana:PREPA - caida", incident.toString());
        assertEquals("caida", incident.getDescription());
        assertEquals("A001", incident.getPerson().getId());
    }

    @Test
    void constructorKeepsItsOwnPersonCopy() {
        Person person = new Person("Ana", "A001", Grade.PREPA);
        Incident incident = new Incident("caida", person);
        assertNotSame(person, incident.getPerson());
        assertEquals(person.toString(), incident.getPerson().toString());
    }
}
