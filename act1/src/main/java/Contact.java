import java.util.Objects;

public class Contact {
    private final String name;
    private final String address;
    private final String phone;

    public Contact(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Contact contact)) {
            return false;
        }
        return Objects.equals(name, contact.name)
                && Objects.equals(address, contact.address)
                && Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }

    @Override
    public String toString() {
        return name + " - " + address + " - " + phone;
    }
}
