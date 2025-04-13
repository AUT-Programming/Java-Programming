package Bank;

import java.util.HashMap;
import java.util.Map;

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
