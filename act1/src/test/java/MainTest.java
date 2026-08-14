import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void insertAndDisplayOnSinglyLinkedList() {
        String output = runMenu("""
                1
                1
                hello
                1
                world
                4
                0
                """);

        assertTrue(output.contains("Singly linked list"));
        assertTrue(output.contains("[hello -> world]"));
    }

    @Test
    void searchReportsFoundAndMissingValues() {
        String output = runMenu("""
                1
                1
                hello
                3
                hello
                3
                missing
                0
                """);

        assertTrue(output.contains("Found: hello"));
        assertTrue(output.contains("Not found: missing"));
    }

    @Test
    void deleteRemovesValueBeforeDisplay() {
        String output = runMenu("""
                1
                1
                hello
                1
                world
                2
                hello
                4
                0
                """);

        assertTrue(output.contains("[world]"));
        assertFalse(output.contains("[hello -> world]"));
    }

    @Test
    void doublyLinkedListUsesBidirectionalDisplay() {
        String output = runMenu("""
                2
                1
                a
                1
                b
                4
                0
                """);

        assertTrue(output.contains("Doubly linked list"));
        assertTrue(output.contains("[a <-> b]"));
    }

    @Test
    void circularListTypesCanBeSelected() {
        String circular = runMenu("""
                3
                1
                a
                4
                0
                """);
        String circularDoubly = runMenu("""
                4
                1
                a
                4
                0
                """);

        assertTrue(circular.contains("Circular linked list"));
        assertTrue(circular.contains("[a]"));
        assertTrue(circularDoubly.contains("Circular doubly linked list"));
        assertTrue(circularDoubly.contains("[a]"));
    }

    @Test
    void invalidChoicesAreRejectedUntilValid() {
        String output = runMenu("""
                9
                1
                9
                4
                0
                """);

        assertTrue(output.contains("Invalid option"));
        assertTrue(output.contains("[]"));
    }

    @Test
    void exitFromListSelectionDoesNotRunOperations() {
        String output = runMenu("""
                0
                """);

        assertTrue(output.contains("Goodbye"));
        assertFalse(output.contains("Insert"));
    }

    @Test
    void changingListTypeStartsANewEmptyList() {
        String output = runMenu("""
                1
                1
                hello
                4
                7
                2
                4
                0
                """);

        assertTrue(output.contains("[hello]"));
        assertTrue(output.contains("Doubly linked list"));
        assertTrue(output.contains("[]"));
    }

    @Test
    void primitiveExamplesUseSelectedSinglyLinkedList() {
        String output = runMenu("""
                1
                5
                1
                0
                0
                """);

        assertTrue(output.contains("Data type examples"));
        assertTrue(output.contains("[10 -> 20 -> 30]"));
    }

    @Test
    void complexExamplesUseSelectedDoublyLinkedList() {
        String output = runMenu("""
                2
                5
                2
                0
                0
                """);

        assertTrue(output.contains("[Mexico City <-> Guadalajara <-> Monterrey]"));
    }

    @Test
    void contactExamplesUseSelectedCircularDoublyLinkedList() {
        String output = runMenu("""
                4
                5
                3
                0
                0
                """);

        assertTrue(output.contains("Ana Perez - Calle 1 - 555-0101"));
        assertTrue(output.contains("Luis Mora - Calle 2 - 555-0102"));
    }

    @Test
    void contactsManagerAddsSearchesDeletesAndDisplays() {
        String output = runMenu("""
                1
                6
                1
                Ada Lovelace
                London
                555-1000
                3
                Ada Lovelace
                4
                2
                Ada Lovelace
                4
                0
                0
                """);

        assertTrue(output.contains("Contacts manager"));
        assertTrue(output.contains("Found: Ada Lovelace - London - 555-1000"));
        assertTrue(output.contains("[Ada Lovelace - London - 555-1000]"));
        assertTrue(output.contains("[]"));
    }

    @Test
    void contactsManagerLoadsExampleContacts() {
        String output = runMenu("""
                1
                6
                5
                4
                0
                0
                """);

        assertTrue(output.contains("Ana Perez - Calle 1 - 555-0101"));
        assertTrue(output.contains("Luis Mora - Calle 2 - 555-0102"));
    }

    @Test
    void contactsManagerSearchesAndDeletesByNameOnly() {
        String output = runMenu("""
                1
                6
                5
                3
                Ana Perez
                2
                Ana Perez
                4
                3
                Ana Perez
                0
                0
                """);

        assertTrue(output.contains("Found: Ana Perez - Calle 1 - 555-0101"));
        assertTrue(output.contains("Deleted: Ana Perez - Calle 1 - 555-0101"));
        assertTrue(output.contains("[Luis Mora - Calle 2 - 555-0102]"));
        assertTrue(output.contains("Not found: Ana Perez"));
    }

    private static String runMenu(String input) {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        Main main = new Main(new Scanner(input), new PrintStream(captured));
        main.run();
        return captured.toString();
    }
}
