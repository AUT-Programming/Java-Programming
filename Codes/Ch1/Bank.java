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
class Bank {
    private Account[] accounts;
    private int numberOfAccounts;
    public static final int ACCOUNT_CAPACITY = 50; // Maximum number of accounts in the bank

    public Bank() {
        accounts = new Account[ACCOUNT_CAPACITY];
        this.numberOfAccounts = 0;
    }

    public void addAccount(Account account) {
        if (numberOfAccounts < ACCOUNT_CAPACITY) {
            accounts[numberOfAccounts++] = account;
            System.out.println("Account " + account.getAccountNumber() + " added to the bank.");
        } else {
            System.out.println("Account capacity reached.");
        }
    }

    public Account findAccountByNumber(String accountNumber) {
        for (int i = 0; i < numberOfAccounts; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    public void listAccounts() {
        System.out.println("Listing all accounts in the bank:");
        for (int i = 0; i < numberOfAccounts; i++) {
            Account account = accounts[i];
            System.out.println("Account Number: " + account.getAccountNumber() +
                    ", Account Holder: " + account.getAccountHolder().getName() +
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
        Bank bank = new Bank();

        Customer customer1 = new Customer("C001", "Alice");
        Customer customer2 = new Customer("C002", "Bob");

        Account account1 = new Account("A1001", customer1, 5000.0);
        Account account2 = new Account("A1002", customer2, 3000.0);

        bank.addAccount(account1);
        bank.addAccount(account2);

        account1.deposit(1500.0);
        account2.withdraw(500.0);

        bank.transferFunds("A1001", "A1002", 2000.0);

        bank.listAccounts();
    }

}

