/**
 * A class for representing situations in which a monetary value is 
 * unexpectedly negative. 
 */
public class NegativeMonetaryValueException extends Exception {
    /**
     * Constructs an exception whose message is the string representation of {@code amount}.
     * @param amount The negative monetary value
     */
    public NegativeMonetaryValueException(MonetaryValue amount) {
        super(amount.toString());
    }
}
