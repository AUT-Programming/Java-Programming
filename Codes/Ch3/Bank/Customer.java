package Bank;

import java.util.concurrent.atomic.AtomicInteger;

// Customer Class
class Customer {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    private final String customerId;
    private final String name;
    private final String email;

    public Customer(String name, String email) {
        this.customerId = "CUST-" + ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
