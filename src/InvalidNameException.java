/**
 * A class for representing situations in which a name is invalid. This can occur when
 * a name has less than two parts, or more than four parts.
 */
public class InvalidNameException extends Exception {
    /**
     * Constructs an exception object whose message consists of the invalid name.
     * @param invalidName The invalid name
     */
    public InvalidNameException(String invalidName) {
        super(invalidName);
    }
}
