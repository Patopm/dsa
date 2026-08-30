import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void exitFromMainMenu() {
        String output = runMenu(new Gym(2), """
                0
                """);
        assertTrue(output.contains("Gym escolar"));
        assertTrue(output.contains("Adiós."));
        assertFalse(output.contains("Formarse"));
    }

    @Test
    void invalidOptionIsRejected() {
        String output = runMenu(new Gym(2), """
                9
                0
                """);
        assertTrue(output.contains("Opción inválida."));
    }

    @Test
    void arriveWithSpaceShowsEnteredAndInsideList() {
        String output = runMenu(new Gym(2), """
                1
                1
                Ana
                A001
                prepa
                0
                2
                1
                0
                0
                """);
        assertTrue(output.contains("Entró: A001:Ana:PREPA"));
        assertTrue(output.contains("[A001:Ana:PREPA]"));
        assertTrue(output.contains("1/2"));
    }

    @Test
    void fullGymQueuesThenAutoEntersOnLeave() {
        String output = runMenu(new Gym(2), """
                1
                1
                Ana
                A001
                prepa
                1
                Luis
                A002
                uni
                1
                Mia
                A003
                prepa
                3
                0
                2
                2
                A001
                1
                0
                0
                """);
        assertTrue(output.contains("En fila: A003:Mia:PREPA"));
        assertTrue(output.contains("[A003:Mia:PREPA]"));
        assertTrue(output.contains("Salió: A001:Ana:PREPA"));
        assertTrue(output.contains("Entró: A003:Mia:PREPA"));
        assertTrue(output.contains("[A002:Luis:UNI -> A003:Mia:PREPA]"));
    }

    @Test
    void reportIncidentAndPeekTop() {
        String output = runMenu(new Gym(2), """
                1
                1
                Ana
                A001
                prepa
                0
                3
                1
                A001
                caida
                2
                0
                0
                """);
        assertTrue(output.contains("A001:Ana:PREPA - caida"));
    }

    private static String runMenu(Gym gym, String input) {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        Main main = new Main(new Scanner(input), new PrintStream(captured), gym);
        main.run();
        return captured.toString();
    }
}
