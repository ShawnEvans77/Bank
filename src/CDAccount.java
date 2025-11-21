import java.time.LocalDate;
import java.time.Period;

/**
 * A class for representing CD accounts, a special type of bank account. 
 * A CD account has a term, a specified period of time after the account is opened,
 *  in which withdrawals are not allowed. The term is always measured in whole months.
 */
public class CDAccount extends BankAccount {
    /**
     * The period of time in which withdrawals are not allowed for this CD account.
     * This is measured in whole months. 
     */
    private Period term;
    
    /**
     * Constructs a CD account with the specified term, for the 
     * account holder with the specified name.
     * @param name The name of the account holder
     * @param term The term
     */
    public CDAccount(Name name, Period term) {
        super(name);
        this.term = term;
    }

    /**
     * Constructs a CD account with the information specified by the parameters.
     * @param accountNumber The account number
     * @param balance The balance
     * @param dateOpened The data this account was opened
     * @param name The account holder's name
     * @param term The term
     * @throws InvalidAccountNumberException If the specified account number isn't exactly
     *         8 characters long, or if it contains any non-digit characters 
     */
    public CDAccount(String accountNumber, MonetaryValue balance, LocalDate dateOpened, Name name, Period term) 
            throws InvalidAccountNumberException {
        super(accountNumber, balance, dateOpened, name);
        this.term = term;
    }

    /** 
     * Withdraws the specified amount of money from this CD account. This will be successful only 
     * if the term has completed.
     * <p>
     * <b>Note:</b> The following methods of the 
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html" target="_blank">LocalDate</a>
     * class may be useful here:
     * <ul>
     *  <li>now</li>
     *  <li>isBefore</li>
     *  <li>plus</li>
     * </ul>
     * @param amount The amount to withdraw
     * @throws NegativeMonetaryValueException If the specified amount of money is negative
     * @throws InsufficientFundsException If the amount to withdraw is greater than the balance
     * @throws WithdrawalDuringTermException If an attempt is made to withdraw money during the account's term
     */
    @Override
    public void withdraw(MonetaryValue amount) 
            throws NegativeMonetaryValueException, InsufficientFundsException, 
            WithdrawalDuringTermException {

        if (LocalDate.now().isBefore(dateOpened.plus(term))) {
            throw new WithdrawalDuringTermException(dateOpened, term);
        }
        
        super.withdraw(amount);
    }

    /** 
     * Determines whether this CD account is equal to {@code o}. They are 
     * considered equal if and only if {@code o} refers to an instance of 
     * {@code CDAccount} whose account number, balance, date opened, name, and term
     * are equal to the corresponding fields of this CD account.
     * @param o The thing to compare this CD account with
     * @return {@code true} if this CD account is equal to {@code o}, {@code false} otherwise 
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CDAccount) {
            CDAccount other = (CDAccount) o;
            return super.equals(other) && term.equals(other.term);
        } else {
            return false;
        }
    }

    /** 
     * Returns a string representation of this CD account, suitable for printing 
     * to a .csv (comma-separated values) file. For example (the last number is
     * always an integer giving the length of the term in months): 
     * <p>
     * {@code "CDAccount,11112222,150.0,2020-8-23,John Adam Smith,6"}
     * @return A string representation of this checking account
     */
    @Override
    public String toString() {
        return "CDAccount," + super.toString().substring(12) + "," + term.toTotalMonths();
    }

}