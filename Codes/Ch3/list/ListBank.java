package list;

import java.util.ArrayList;

/**
 * Represents a bank account with basic operations such as deposit and withdrawal.
 * Each account is associated with a specific customer.
 */
class Account {
    private String accountNumber;
    private double balance;
    private Customer accountHolder;

    public Account(String accountNumber, Customer accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + " into account " + accountNumber);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew " + amount + " from account " + accountNumber);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }
}

/**
 * Represents a customer with a unique ID and name.
 */
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

/**
 * Represents a bank that manages accounts and facilitates fund transfers between them.
 */
public class ListBank {
    private ArrayList<Account> accounts;

    public ListBank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        // System.out.println("list.Account " + account.getAccountNumber() + " added to the bank.");
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        System.out.println("list.Account not found.");
        return null;
    }

    public void listAccounts() {
        System.out.println("Listing all accounts in the bank:");
        for (Account account : accounts) {
            System.out.println("list.Account Number: " + account.getAccountNumber() +
                    ", list.Account Holder: " + account.getAccountHolder().getName() +
                    ", Balance: " + account.getBalance());
        }
    }

    public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccountByNumber(fromAccountNumber);
        Account toAccount = findAccountByNumber(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("Transferred " + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber);
            } else {
                System.out.println("Insufficient funds in account " + fromAccountNumber);
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public static void main(String[] args) {
        ListBank bank = new ListBank();
        // list.ListBank bank = new list.ListBank();

        System.out.println("Creating Accounts");
        for (int i = 1; i <= 1e6; i++) {
            Customer customer = new Customer("C" + i, "Bob the " + i + "th");
            Account account = new Account("A" + i, customer, 0.0);
            bank.addAccount(account);
        }

        System.out.println("Creating accounts finished.");

        System.out.println("Searching for an account");

        String accountNumberToSearch = "A900900";
        long startTime = System.nanoTime();
        Account foundAccount = bank.findAccountByNumber(accountNumberToSearch);
        long endTime = System.nanoTime();
        double duration = (double) (endTime - startTime) / 1_000_000; // Convert to milliseconds

        if (foundAccount != null) {
            System.out.println("Account " + accountNumberToSearch + " found: ");
            System.out.println(foundAccount.getAccountHolder().getName());
        } else {
            System.out.println("No account found.");
        }

        System.out.println("Time taken to search: " + duration + " ms");
    }

}

