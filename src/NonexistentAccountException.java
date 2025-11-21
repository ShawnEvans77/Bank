/**
 * A class for representing situations in which a requested account doesn't exist in the bank.
 */
public class NonexistentAccountException extends Exception {
    /**
     * Constructs an exception object whose message consists of the requested account number.
     * @param accountNumber The requested account number
     */
    public NonexistentAccountException(String accountNumber) {
        super(accountNumber);
    }
}
