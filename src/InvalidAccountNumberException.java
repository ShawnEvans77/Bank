/**
 * A class for representing situations in which an account number is not valid.
 */
public class InvalidAccountNumberException extends Exception {
    /**
     * Constructs an exception object whose message consists of the invalid account number.
     * @param accountNumber The invalid account number
     */
    public InvalidAccountNumberException(String accountNumber) {
        super(accountNumber);
    }
}
