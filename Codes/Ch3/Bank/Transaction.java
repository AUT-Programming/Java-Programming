package Bank;

import java.time.LocalDateTime;

// Transaction Record
class Transaction {
    private final LocalDateTime timestamp;
    private final String type;
    private final double amount;
    private final TransactionStatus status;

    public Transaction(String type, double amount, TransactionStatus status) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f (%s)",
                timestamp, type, amount, status.getMessage());
    }
}
