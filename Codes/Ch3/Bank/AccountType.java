package Bank;

// Account Type Enum with Simple Notation
enum AccountType {
    CHECKING,
    SAVINGS,
    FIXED_DEPOSIT;

    public TransactionStatus validateWithdrawal(BankAccount account, double amount) {
        return switch (this) {
            case CHECKING -> account.getBalance() >= amount ?
                    TransactionStatus.SUCCESS :
                    TransactionStatus.INSUFFICIENT_BALANCE;
            case SAVINGS -> {
                if (account.getWithdrawalsThisMonth() >= 3) {
                    yield TransactionStatus.WITHDRAWAL_LIMIT_EXCEEDED;
                }
                yield account.getBalance() >= amount ?
                        TransactionStatus.SUCCESS :
                        TransactionStatus.INSUFFICIENT_BALANCE;
            }
            case FIXED_DEPOSIT -> {
                if (account.getMonthsActive() < 12) {
                    yield TransactionStatus.PREMATURE_WITHDRAWAL;
                }
                yield account.getBalance() >= amount ?
                        TransactionStatus.SUCCESS :
                        TransactionStatus.INSUFFICIENT_BALANCE;
            }
        };
    }

    public double calculateInterest(BankAccount account) {
        return switch (this) {
            case CHECKING -> 0.0;
            case SAVINGS -> account.getBalance() * (Math.pow(1 + 0.04 / 12, 1) - 1);
            case FIXED_DEPOSIT -> account.getBalance() * 0.065 * account.getMonthsActive() / 12;
        };
    }
}
