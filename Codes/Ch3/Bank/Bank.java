package Bank;

import java.util.Scanner;

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