package ejercicio1;

public class AccountLockedException extends Exception {
    public AccountLockedException(String message) {
        super(message);
    }
}