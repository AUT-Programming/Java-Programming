package Bank;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Scanner;

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

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

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

    public double getBalance() { return balance; }
    public int getWithdrawalsThisMonth() { return withdrawalsThisMonth; }
    public int getMonthsActive() { return monthsActive; }
    public List<Transaction> getStatement() { return new ArrayList<>(transactions); }
    public Customer getCustomer() { return customer; }
    public AccountType getType() { return type; }
    public String getAccountNumber() { return accountNumber; }
}

// Banking System Core
class BankingSystem {
    private final Map<String, BankAccount> accounts = new HashMap<>();
    private final Map<String, Customer> customers = new HashMap<>();

    public TransactionStatus createCustomer(String name, String email) {
        Customer customer = new Customer(name, email);
        if (customers.containsKey(customer.getCustomerId())) {
            return TransactionStatus.CUSTOMER_NOT_FOUND;
        }
        customers.put(customer.getCustomerId(), customer);
        return TransactionStatus.SUCCESS;
    }

    public TransactionStatus openAccount(String accountNumber, String customerId,
                                         AccountType type, double initialDeposit) {
        if (accounts.containsKey(accountNumber)) {
            return TransactionStatus.ACCOUNT_EXISTS;
        }

        Customer customer = customers.get(customerId);
        if (customer == null) {
            return TransactionStatus.CUSTOMER_NOT_FOUND;
        }

        if (initialDeposit <= 0) {
            return TransactionStatus.INVALID_AMOUNT;
        }

        accounts.put(accountNumber, new BankAccount(accountNumber, customer, type, initialDeposit));
        return TransactionStatus.SUCCESS;
    }

    public TransactionStatus withdraw(String accountNumber, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            return TransactionStatus.ACCOUNT_NOT_FOUND;
        }
        return account.withdraw(amount);
    }

    public void processEndOfMonth() {
        accounts.values().forEach(acc -> {
            acc.applyInterest();
            acc.monthlyUpdate();
        });
    }

    public String generateStatement(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            return "Account not found";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Account Statement for: ").append(accountNumber).append("\n");
        sb.append("Customer: ").append(account.getCustomer().getName()).append("\n");
        sb.append("Type: ").append(account.getType().name()).append("\n");
        sb.append("Current Balance: $").append(String.format("%.2f", account.getBalance())).append("\n");
        sb.append("Transaction History:\n");
        account.getStatement().forEach(t -> sb.append(t).append("\n"));
        return sb.toString();
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Map<String, BankAccount> getAllAccounts() {
        return new HashMap<>(accounts);
    }
}

// Terminal Interface
public class Bank {

    private static final BankingSystem bankingSystem = new BankingSystem();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Bank Terminal!");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminInterface();
                    break;
                case 2:
                    customerInterface();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminInterface() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create Customer");
            System.out.println("2. Open Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    openAccount();
                    break;
                case 3:
                    viewAllAccounts();
                    break;
                case 4:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void customerInterface() {
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = bankingSystem.getAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        while (true) {
            System.out.println("\nCustomer Menu for Account: " + accountNumber);
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Statement");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    deposit(account);
                    break;
                case 2:
                    withdraw(account);
                    break;
                case 3:
                    viewStatement(account);
                    break;
                case 4:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createCustomer() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        TransactionStatus status = bankingSystem.createCustomer(name, email);
        System.out.println(status.getMessage());
    }

    private static void openAccount() {
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account type (CHECKING, SAVINGS, FIXED_DEPOSIT): ");
        AccountType type = AccountType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter initial deposit: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();

        TransactionStatus status = bankingSystem.openAccount(accountNumber, customerId, type, initialDeposit);
        System.out.println(status.getMessage());
    }

    private static void viewAllAccounts() {
        System.out.println("\nAll Accounts:");
        bankingSystem.getAllAccounts().forEach((accountNumber, account) -> {
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Customer: " + account.getCustomer().getName());
            System.out.println("Type: " + account.getType());
            System.out.println("Balance: $" + account.getBalance());
            System.out.println("-----------------------------");
        });
    }

    private static void deposit(BankAccount account) {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        account.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + account.getBalance());
    }

    private static void withdraw(BankAccount account) {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        TransactionStatus status = account.withdraw(amount);
        System.out.println(status.getMessage());
        if (status == TransactionStatus.SUCCESS) {
            System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
        }
    }

    private static void viewStatement(BankAccount account) {
        System.out.println("\nAccount Statement:");
        System.out.println("Customer: " + account.getCustomer().getName());
        System.out.println("Type: " + account.getType());
        System.out.println("Balance: $" + account.getBalance());
        System.out.println("Transaction History:");
        account.getStatement().forEach(System.out::println);
    }
}