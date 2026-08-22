import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void captureStdout() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void enqueueRunAndShowFollowFcfs() {
        String output = runMenu("""
                1
                1
                editor
                1
                2
                browser
                5
                2
                5
                6
                0
                """);

        assertTrue(output.contains("OS simulator"));
        assertTrue(output.contains("[1:editor -> 2:browser]"));
        assertTrue(output.contains("Ran: 1:editor"));
        assertTrue(output.contains("[2:browser]"));
        assertTrue(output.contains("[1:editor]"));
        assertTrue(output.contains("Goodbye."));
    }

    @Test
    void invalidPidDoesNotEnqueue() {
        String output = runMenu("""
                1
                abc
                5
                0
                """);

        assertTrue(output.contains("Invalid pid."));
        assertTrue(output.contains("[]"));
        assertFalse(output.contains("abc"));
    }

    @Test
    void emptyPeekAndRunPrintEmptyMessages() {
        String output = runMenu("""
                2
                3
                4
                0
                """);

        assertTrue(output.contains("Queue is empty."));
        assertTrue(output.contains("Stack is empty."));
    }

    @Test
    void invalidOptionIsReported() {
        String output = runMenu("""
                9
                0
                """);

        assertTrue(output.contains("Invalid option."));
    }

    private String runMenu(String input) {
        Main main = new Main(new Scanner(input));
        main.run();
        return capturedOut.toString();
    }
}
