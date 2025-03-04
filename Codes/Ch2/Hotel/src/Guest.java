package Hotel.src;

/**
 * Represents a guest who books a room at a hotel.
 * Stores basic guest details such as name and contact information.
 *
 * @author ApAssistants
 * @version 1.0
 * @since 2025-02-28
 */
public class Guest {
    private String name;
    private String contactInfo;

    /**
     * Constructs a new guest with a name and contact information.
     *
     * @param name        The name of the guest.
     * @param contactInfo Contact details (phone/email).
     */
    public Guest(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Guest{name='" + name + "', contact='" + contactInfo + "'}";
    }
}
