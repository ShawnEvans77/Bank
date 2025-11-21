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

    /**
     * 
     * public class NegativeNumberException extends Exception {
     *      public NegativeNumberException(String msg) {
     *          super(msg);
     *      }
     * }
     * 
     * public void multiplyByTwo(int number) throws NegativeNumberException {
     *         if (number < 0)
     *              throw new NegativeNumberException("invalid number");
     *          }
     * }
     * 
     * public static void main(String[] args) {
     *      try {
     *      multiplyByTwo(-2);
     *      } catch (NegativeNumberException e) {
     *      System.out.println("Error: Negative number.");
     *      }
     * }
     * 
     * 
     */
}
