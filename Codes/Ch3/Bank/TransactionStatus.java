package Bank;

// Error Handling Enum
enum TransactionStatus {
    SUCCESS("Operation successful"),
    INSUFFICIENT_BALANCE("Insufficient balance"),
    WITHDRAWAL_LIMIT_EXCEEDED("Withdrawal limit exceeded"),
    PREMATURE_WITHDRAWAL("Account not mature for withdrawals"),
    INVALID_AMOUNT("Invalid amount specified"),
    ACCOUNT_EXISTS("Account already exists"),
    ACCOUNT_NOT_FOUND("Account not found"),
    CUSTOMER_NOT_FOUND("Customer not found");

    private final String message;

    TransactionStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
