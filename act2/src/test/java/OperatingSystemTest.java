import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OperatingSystemTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;
    private OperatingSystem os;

    @BeforeEach
    void setUp() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
        os = new OperatingSystem();
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void addProcessEnqueuesInOrder() {
        os.addProcess(1, "editor");
        os.addProcess(2, "browser");
        os.addProcess(3, "compiler");
        assertEquals("[1:editor -> 2:browser -> 3:compiler]", os.readyQueueString());
        assertEquals("[]", os.historyString());
    }

    @Test
    void runNextIsFcfsAndPushesHistory() {
        os.addProcess(1, "editor");
        os.addProcess(2, "browser");
        os.addProcess(3, "compiler");
        assertEquals("1:editor", os.runNext().toString());
        assertEquals("2:browser", os.runNext().toString());
        assertEquals("[3:compiler]", os.readyQueueString());
        assertEquals("[2:browser -> 1:editor]", os.historyString());
    }

    @Test
    void peeksDoNotMutate() {
        os.addProcess(1, "editor");
        os.addProcess(2, "browser");
        os.runNext();
        assertEquals("2:browser", os.peekReady().toString());
        assertEquals("1:editor", os.peekLastFinished().toString());
        assertEquals("[2:browser]", os.readyQueueString());
        assertEquals("[1:editor]", os.historyString());
    }

    @Test
    void emptyRunDoesNotChangeHistory() {
        os.addProcess(1, "editor");
        os.runNext();
        assertNull(os.runNext());
        assertEquals("[]", os.readyQueueString());
        assertEquals("[1:editor]", os.historyString());
        assertEquals("Queue is empty." + System.lineSeparator(), capturedOut.toString());
    }
}
