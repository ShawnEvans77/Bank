/**
 * A class for representing situations in which there are insufficient funds available
 * for some desired operation.
 */
public class InsufficientFundsException extends Exception {
    /**
     * Constructs an exception with a message indicating that the amount available is less than
     * the amount desired. For example: 
     * <p>
     * {@code ""}
     * @param amountAvailable The amount of money available
     * @param amountDesired The amount of money desired
     */
    public InsufficientFundsException(MonetaryValue amountAvailable, MonetaryValue amountDesired) {
        super(amountAvailable + " is less than " + amountDesired);
    }
}
