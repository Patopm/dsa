import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private OperatingSystem os = new OperatingSystem();

    public Main(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in)).run();
    }

    public void run() {
        System.out.println("OS simulator");
        while (true) {
            System.out.println("1) Enqueue process");
            System.out.println("2) Run next (FCFS)");
            System.out.println("3) Peek next ready");
            System.out.println("4) Peek last finished");
            System.out.println("5) Show ready queue");
            System.out.println("6) Show history");
            System.out.println("0) Exit");
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                System.out.println("Goodbye.");
                return;
            }

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> enqueueProcess();
                case "2" -> runNext();
                case "3" -> peekReady();
                case "4" -> peekLastFinished();
                case "5" -> System.out.println(os.readyQueueString());
                case "6" -> System.out.println(os.historyString());
                case "0" -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void enqueueProcess() {
        System.out.print("Pid: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String pidText = scanner.nextLine().trim();
        int pid;
        try {
            pid = Integer.parseInt(pidText);
        } catch (NumberFormatException error) {
            System.out.println("Invalid pid.");
            return;
        }

        System.out.print("Name: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String name = scanner.nextLine();
        os.addProcess(pid, name);
    }

    private void runNext() {
        Process process = os.runNext();
        if (process != null) {
            System.out.println("Ran: " + process);
        }
    }

    private void peekReady() {
        Process process = os.peekReady();
        if (process != null) {
            System.out.println(process);
        }
    }

    private void peekLastFinished() {
        Process process = os.peekLastFinished();
        if (process != null) {
            System.out.println(process);
        }
    }
}
