package Bank;

import java.util.ArrayList;
import java.util.List;

// Bank Account Class
class BankAccount {
    private final String accountNumber;
    private final Customer customer;
    private final AccountType type;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();
    private int withdrawalsThisMonth;
    private int monthsActive;

    public BankAccount(String accountNumber, Customer customer, AccountType type, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.type = type;
        this.balance = initialDeposit;
        transactions.add(new Transaction("OPENING", initialDeposit, TransactionStatus.SUCCESS));
    }

    public TransactionStatus withdraw(double amount) {
        if (amount <= 0) {
            transactions.add(new Transaction("WITHDRAWAL", amount, TransactionStatus.INVALID_AMOUNT));
            return TransactionStatus.INVALID_AMOUNT;
        }

        TransactionStatus status = type.validateWithdrawal(this, amount);
        if (status == TransactionStatus.SUCCESS) {
            balance -= amount;
            withdrawalsThisMonth++;
        }
        transactions.add(new Transaction("WITHDRAWAL", amount, status));
        return status;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            transactions.add(new Transaction("DEPOSIT", amount, TransactionStatus.INVALID_AMOUNT));
            return;
        }
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, TransactionStatus.SUCCESS));
    }

    public void applyInterest() {
        double interest = type.calculateInterest(this);
        balance += interest;
        transactions.add(new Transaction("INTEREST", interest, TransactionStatus.SUCCESS));
    }

    public void monthlyUpdate() {
        monthsActive++;
        withdrawalsThisMonth = 0;
    }

    public double getBalance() {
        return balance;
    }

    public int getWithdrawalsThisMonth() {
        return withdrawalsThisMonth;
    }

    public int getMonthsActive() {
        return monthsActive;
    }

    public List<Transaction> getStatement() {
        return new ArrayList<>(transactions);
    }

    public Customer getCustomer() {
        return customer;
    }

    public AccountType getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
