import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final PrintStream out;
    private final DataTypeExamples examples = new DataTypeExamples();
    private ListKind listKind;
    private LinkedList<String> list;
    private LinkedList<Contact> contacts;

    public Main(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in), System.out).run();
    }

    public void run() {
        out.println("Linked List Menu");
        while (true) {
            if (list == null && !selectListType()) {
                out.println("Goodbye.");
                return;
            }
            if (!runOperation()) {
                out.println("Goodbye.");
                return;
            }
        }
    }

    private boolean selectListType() {
        while (true) {
            out.println("Select list type:");
            out.println("1) Singly linked list");
            out.println("2) Doubly linked list");
            out.println("3) Circular linked list");
            out.println("4) Circular doubly linked list");
            out.println("0) Exit");
            out.print("> ");

            switch (readChoice()) {
                case "1" -> {
                    return useListKind(ListKind.SINGLY, "Singly linked list selected.");
                }
                case "2" -> {
                    return useListKind(ListKind.DOUBLY, "Doubly linked list selected.");
                }
                case "3" -> {
                    return useListKind(ListKind.CIRCULAR, "Circular linked list selected.");
                }
                case "4" -> {
                    return useListKind(ListKind.CIRCULAR_DOUBLY, "Circular doubly linked list selected.");
                }
                case "0" -> {
                    return false;
                }
                default -> out.println("Invalid option.");
            }
        }
    }

    private boolean useListKind(ListKind kind, String message) {
        listKind = kind;
        list = createList();
        contacts = null;
        out.println(message);
        return true;
    }

    private boolean runOperation() {
        out.println("Operations:");
        out.println("1) Insert");
        out.println("2) Delete");
        out.println("3) Search");
        out.println("4) Display");
        out.println("5) Data type examples");
        out.println("6) Contacts manager");
        out.println("7) Change list type");
        out.println("0) Exit");
        out.print("> ");

        switch (readChoice()) {
            case "1" -> insertValue();
            case "2" -> deleteValue();
            case "3" -> searchValue();
            case "4" -> out.println(list);
            case "5" -> runDataTypeExamples();
            case "6" -> runContactsManager();
            case "7" -> {
                list = null;
                contacts = null;
                listKind = null;
            }
            case "0" -> {
                return false;
            }
            default -> out.println("Invalid option.");
        }
        return true;
    }

    private void runDataTypeExamples() {
        while (true) {
            out.println("Data type examples:");
            out.println("1) Primitives (int)");
            out.println("2) Complex types (String)");
            out.println("3) Abstract types (Contact)");
            out.println("0) Back");
            out.print("> ");

            switch (readChoice()) {
                case "1" -> {
                    LinkedList<Integer> demo = createList();
                    examples.addPrimitives(demo);
                    out.println("Primitive ints: " + demo);
                }
                case "2" -> {
                    LinkedList<String> demo = createList();
                    examples.addComplexTypes(demo);
                    out.println("Complex types: " + demo);
                }
                case "3" -> {
                    LinkedList<Contact> demo = createList();
                    examples.addContacts(demo);
                    out.println("Contacts: " + demo);
                }
                case "0" -> {
                    return;
                }
                default -> out.println("Invalid option.");
            }
        }
    }

    private void runContactsManager() {
        if (contacts == null) {
            contacts = createList();
        }

        while (true) {
            out.println("Contacts manager");
            out.println("1) Add contact");
            out.println("2) Delete contact");
            out.println("3) Search contact");
            out.println("4) Display contacts");
            out.println("5) Load example contacts");
            out.println("0) Back");
            out.print("> ");

            switch (readChoice()) {
                case "1" -> {
                    Contact contact = readContact();
                    contacts.insert(contact);
                    out.println("Added: " + contact);
                }
                case "2" -> deleteContactByName();
                case "3" -> searchContactByName();
                case "4" -> out.println(contacts);
                case "5" -> {
                    examples.addContacts(contacts);
                    out.println("Loaded example contacts.");
                }
                case "0" -> {
                    return;
                }
                default -> out.println("Invalid option.");
            }
        }
    }

    private Contact readContact() {
        out.print("Name: ");
        String name = readLine();
        out.print("Address: ");
        String address = readLine();
        out.print("Phone: ");
        String phone = readLine();
        return new Contact(name, address, phone);
    }

    private void searchContactByName() {
        String name = readName();
        Contact found = findContactByName(name);
        if (found != null) {
            out.println("Found: " + found);
        } else {
            out.println("Not found: " + name);
        }
    }

    private void deleteContactByName() {
        String name = readName();
        Contact found = findContactByName(name);
        if (found != null) {
            contacts.delete(found);
            out.println("Deleted: " + found);
        } else {
            out.println("Not found: " + name);
        }
    }

    private String readName() {
        out.print("Name: ");
        return readLine();
    }

    private Contact findContactByName(String name) {
        return contacts.findFirst(contact -> contact.getName().equals(name));
    }

    private void insertValue() {
        out.print("Value: ");
        String value = readLine();
        list.insert(value);
        out.println("Inserted: " + value);
    }

    private void deleteValue() {
        out.print("Value: ");
        String value = readLine();
        list.delete(value);
        out.println("Deleted: " + value);
    }

    private void searchValue() {
        out.print("Value: ");
        String value = readLine();
        if (list.search(value)) {
            out.println("Found: " + value);
        } else {
            out.println("Not found: " + value);
        }
    }

    private <T> LinkedList<T> createList() {
        return switch (listKind) {
            case SINGLY -> new SinglyLinkedList<>();
            case DOUBLY -> new DoublyLinkedList<>();
            case CIRCULAR -> new CircularLinkedList<>();
            case CIRCULAR_DOUBLY -> new CircularDoublyLinkedList<>();
        };
    }

    private String readChoice() {
        return readLine().trim();
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            return "0";
        }
        return scanner.nextLine();
    }

    private enum ListKind {
        SINGLY,
        DOUBLY,
        CIRCULAR,
        CIRCULAR_DOUBLY
    }
}
