/**
 * A class for representing situations in which an attempt is made to add 
 * an account to a bank, where the account number of that account is a duplicate 
 * of the account number of an account that's already in the bank.
 */
public class DuplicateAccountNumberException extends Exception {
    /**
     * Constructs an exception object whose message consists of the duplicate account number.
     * @param accountNumber The duplicate account number
     */
    public DuplicateAccountNumberException(String accountNumber) {
        super(accountNumber);
    }
    
}
