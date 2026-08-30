import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final PrintStream out;
    private final Gym gym;

    public Main(Scanner scanner, PrintStream out) {
        this(scanner, out, new Gym(Gym.DEFAULT_CAPACITY));
    }

    public Main(Scanner scanner, PrintStream out, Gym gym) {
        this.scanner = scanner;
        this.out = out;
        this.gym = gym;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in), System.out).run();
    }

    public void run() {
        out.println("Gym escolar");
        while (true) {
            out.println("1) Fila de entrada");
            out.println("2) Alumnos adentro");
            out.println("3) Incidentes");
            out.println("0) Salir");
            out.print("> ");
            String choice = readLine();
            if (choice == null || choice.equals("0")) {
                out.println("Adiós.");
                return;
            }
            switch (choice) {
                case "1" -> waitingMenu();
                case "2" -> insideMenu();
                case "3" -> incidentMenu();
                default -> out.println("Opción inválida.");
            }
        }
    }

    private void waitingMenu() {
        while (true) {
            out.println("1) Formarse");
            out.println("2) Ver siguiente");
            out.println("3) Ver fila");
            out.println("4) Cancelar espera");
            out.println("0) Volver");
            out.print("> ");
            String choice = readLine();
            if (choice == null || choice.equals("0")) {
                return;
            }
            switch (choice) {
                case "1" -> arrive();
                case "2" -> showFront();
                case "3" -> showWaiting();
                case "4" -> cancelWait();
                default -> out.println("Opción inválida.");
            }
        }
    }

    private void insideMenu() {
        while (true) {
            out.println("1) Ver quién está adentro");
            out.println("2) Salir del gym");
            out.println("3) Buscar por nombre");
            out.println("4) Buscar por matrícula");
            out.println("5) Buscar por grado");
            out.println("0) Volver");
            out.print("> ");
            String choice = readLine();
            if (choice == null || choice.equals("0")) {
                return;
            }
            switch (choice) {
                case "1" -> showInside();
                case "2" -> leave();
                case "3" -> findByName();
                case "4" -> findById();
                case "5" -> findByGrade();
                default -> out.println("Opción inválida.");
            }
        }
    }

    private void incidentMenu() {
        while (true) {
            out.println("1) Reportar");
            out.println("2) Ver más urgente");
            out.println("3) Atender más urgente");
            out.println("4) Ver por urgencia");
            out.println("5) Ver agrupados por grado");
            out.println("0) Volver");
            out.print("> ");
            String choice = readLine();
            if (choice == null || choice.equals("0")) {
                return;
            }
            switch (choice) {
                case "1" -> reportIncident();
                case "2" -> peekIncident();
                case "3" -> resolveIncident();
                case "4" -> showIncidentsByUrgency();
                case "5" -> showIncidentsByGrade();
                default -> out.println("Opción inválida.");
            }
        }
    }

    private void arrive() {
        String name = readRequired("Nombre: ");
        if (name == null) {
            return;
        }
        String id = readRequired("Matrícula: ");
        if (id == null) {
            return;
        }
        out.print("Grado (prepa/uni): ");
        String gradeText = readLine();
        if (gradeText == null) {
            return;
        }
        Grade grade = Grade.parse(gradeText);
        if (grade == null) {
            out.println("Grado inválido.");
            return;
        }
        Gym.ArriveResult result = gym.arrive(new Person(name, id, grade));
        switch (result.getStatus()) {
            case ENTERED -> out.println("Entró: " + result.getPerson());
            case QUEUED -> out.println("En fila: " + result.getPerson());
            case DUPLICATE -> out.println("Matrícula ya registrada.");
        }
    }

    private void showFront() {
        Person person = gym.waitingFront();
        if (person == null) {
            out.println("No hay elementos.");
            return;
        }
        out.println(person);
    }

    private void showWaiting() {
        if (gym.waitingFront() == null) {
            out.println("No hay elementos.");
            return;
        }
        out.println(gym.waitingString());
    }

    private void cancelWait() {
        String id = readRequired("Matrícula: ");
        if (id == null) {
            return;
        }
        Person person = gym.cancelWait(id);
        if (person == null) {
            out.println("No está en la fila.");
            return;
        }
        out.println("Salió de la fila: " + person);
    }

    private void showInside() {
        if (gym.insideCount() == 0) {
            out.println("No hay elementos.");
            return;
        }
        out.println(gym.insideString());
        out.println(gym.insideCount() + "/" + gym.capacity());
    }

    private void leave() {
        String id = readRequired("Matrícula: ");
        if (id == null) {
            return;
        }
        Gym.LeaveResult result = gym.leave(id);
        switch (result.getStatus()) {
            case NOT_INSIDE -> out.println("No está en el gym.");
            case LEFT -> out.println("Salió: " + result.getLeft());
            case LEFT_AND_ENTERED -> {
                out.println("Salió: " + result.getLeft());
                out.println("Entró: " + result.getEntered());
            }
        }
    }

    private void findByName() {
        String name = readRequired("Nombre: ");
        if (name == null) {
            return;
        }
        LinkedList<Person> found = gym.findByName(name);
        if (found.isEmpty()) {
            out.println("No encontrado.");
            return;
        }
        out.println(found);
    }

    private void findById() {
        String id = readRequired("Matrícula: ");
        if (id == null) {
            return;
        }
        Person person = gym.findById(id);
        if (person == null) {
            out.println("No encontrado.");
            return;
        }
        out.println(person);
    }

    private void findByGrade() {
        out.print("Grado (prepa/uni): ");
        String gradeText = readLine();
        if (gradeText == null) {
            return;
        }
        Grade grade = Grade.parse(gradeText);
        if (grade == null) {
            out.println("Grado inválido.");
            return;
        }
        LinkedList<Person> found = gym.findByGrade(grade);
        if (found.isEmpty()) {
            out.println("No encontrado.");
            return;
        }
        out.println(found);
    }

    private void reportIncident() {
        String id = readRequired("Matrícula: ");
        if (id == null) {
            return;
        }
        String description = readRequired("Descripción: ");
        if (description == null) {
            return;
        }
        Incident incident = gym.reportIncident(id, description);
        if (incident == null) {
            out.println("Debe estar dentro del gym.");
            return;
        }
        out.println(incident);
    }

    private void peekIncident() {
        Incident incident = gym.peekIncident();
        if (incident == null) {
            out.println("No hay incidentes.");
            return;
        }
        out.println(incident);
    }

    private void resolveIncident() {
        Incident incident = gym.resolveIncident();
        if (incident == null) {
            out.println("No hay incidentes.");
            return;
        }
        out.println(incident);
    }

    private void showIncidentsByUrgency() {
        Incident top = gym.peekIncident();
        if (top == null) {
            out.println("No hay incidentes.");
            return;
        }
        out.println(gym.incidentsByUrgencyString());
    }

    private void showIncidentsByGrade() {
        Incident top = gym.peekIncident();
        if (top == null) {
            out.println("No hay incidentes.");
            return;
        }
        out.println(gym.incidentsByGradeString());
    }

    private String readRequired(String prompt) {
        out.print(prompt);
        String value = readLine();
        if (value == null) {
            return null;
        }
        if (value.isEmpty()) {
            out.println("Datos incompletos.");
            return null;
        }
        return value;
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }
}
