import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessTest {

    @Test
    void storesPidAndName() {
        Process process = new Process(1, "editor");
        assertEquals(1, process.getPid());
        assertEquals("editor", process.getName());
        assertEquals("1:editor", process.toString());
    }
}
