package ejercicio1;

public class SistemaBancario {
    private double balance;
    private boolean locked;

    public SistemaBancario(double initialBalance) {
        this.balance = initialBalance;
        this.locked = false;
    }

    public void deposit(double amount) throws AccountLockedException {
        if(locked)
            throw new AccountLockedException("Account is locked");
        if (amount <= 0){
            throw new InvalidAmountException("Invalid amount: "+amount);
        }
            balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0)
            throw new InvalidAmountException("Invalid amount: " + amount);
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient funds to withdraw: " + amount, amount-balance);
        }
            balance -= amount;
    }

    public void transfer(SistemaBancario target, double amount)
            throws InsufficientBalanceException {
        try (TransactionLog log = new TransactionLog()) {
            withdraw(amount);
            log.log("Withdrawal of $" + amount + " from the source account. Balance: $"+balance);
            target.deposit(amount);
            log.log("Deposit of $" + amount + " from the destination account. Balance: $"+target.getBalance());
        } catch (AccountLockedException e) {
            throw new RuntimeException(e);
        }
    }

    public void lock() {
        this.locked = true;
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        SistemaBancario cuenta1 = new SistemaBancario(1000.00);
        SistemaBancario cuenta2 = new SistemaBancario(500.00);

        try {
            cuenta1.deposit(500);
            System.out.printf("\nSuccessful deposit. Balance: $%.2f%n", cuenta1.getBalance());

            cuenta1.withdraw(200);
            System.out.printf("Withdrawal successful. Balance: $%.2f%n\n", cuenta1.getBalance());

            cuenta1.transfer(cuenta2, 300);
            System.out.printf("\nTransfer successful.\nAccount #1 balance: $%.2f \nAccount #2 balance: $%.2f%n",
                    cuenta1.getBalance(), cuenta2.getBalance());
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AccountLockedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n=== Error handling ===");

        try {
            cuenta1.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AccountLockedException e) {
            throw new RuntimeException(e);
        }

        try {
            cuenta1.withdraw(999999);
        } catch (InsufficientBalanceException e) {
            System.out.printf("Error: %s (deficit: $%.2f)%n",
                    e.getMessage(), e.getDeficit());
        }
    }
}

