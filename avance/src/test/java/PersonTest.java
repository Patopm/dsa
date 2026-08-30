import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonTest {

    @Test
    void parseAcceptsPrepaAndUniIgnoringCase() {
        assertEquals(Grade.PREPA, Grade.parse("prepa"));
        assertEquals(Grade.UNI, Grade.parse("UNI"));
        assertEquals(Grade.PREPA, Grade.parse(" Prepa "));
    }

    @Test
    void parseRejectsUnknownText() {
        assertNull(Grade.parse("area"));
        assertNull(Grade.parse(""));
        assertNull(Grade.parse(null));
    }

    @Test
    void toStringIsIdNameGrade() {
        Person person = new Person("Ana", "A001", Grade.PREPA);
        assertEquals("A001:Ana:PREPA", person.toString());
        assertEquals("Ana", person.getName());
        assertEquals("A001", person.getId());
        assertEquals(Grade.PREPA, person.getGrade());
    }

    @Test
    void copyIsANewEqualSnapshot() {
        Person original = new Person("Ana", "A001", Grade.PREPA);
        Person copy = original.copy();
        assertNotSame(original, copy);
        assertEquals(original.toString(), copy.toString());
        assertEquals("Ana", copy.getName());
        assertEquals("A001", copy.getId());
        assertEquals(Grade.PREPA, copy.getGrade());
    }
}
