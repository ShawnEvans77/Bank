import java.time.LocalDate;

/**
 * A class for representing checking accounts, a special type of bank account. 
 * The balance of a checking account is allowed to go below zero.
 * The balance can go down until certain limit, known as the overdraft limit.
 */
public class CheckingAccount extends BankAccount {
    /**
     * The amount that the balance of this savings account is allowed to go below zero.
     */
    private MonetaryValue overdraftLimit;

    /**
     * Constructs a checking account with the specified overdraft limit, for the 
     * account holder with the specified name.
     * @param name The name of the account holder
     * @param overdraftLimit The overdraft limit
     */
    public CheckingAccount(Name name, MonetaryValue overdraftLimit) {
        super(name);
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Constructs a checking account with the information specified by the parameters.
     * @param accountNumber The account number
     * @param balance The balance
     * @param dateOpened The data this account was opened
     * @param name The account holder's name
     * @param overdraftLimit The overdraft limit
     * @throws InvalidAccountNumberException If the specified account number isn't exactly
     *         8 characters long, or if it contains any non-digit characters 
     */
    public CheckingAccount(String accountNumber, MonetaryValue balance, LocalDate dateOpened, Name name, MonetaryValue overdraftLimit) 
 throws InvalidAccountNumberException {
        super(accountNumber, balance, dateOpened, name);
        this.overdraftLimit = overdraftLimit;
    }

    /** 
     * Withdraws the specified amount of money from this checking account. This will be
     * successful only if there are sufficient funds in this account for the withdrawal
     * to occur; otherwise, nothing is withdrawn. That is, if attempting to withdraw 
     * desired amount would put the balance become negative beyond what is allowed by the 
     * overdraft limit, nothing should be withdrawn.
     * @param amount The amount of money to withdraw
     * @throws NegativeMonetaryValueException If the specified amount of money is negative
     * @throws InsufficientFundsException If the amount to withdraw is greater than 
     *         the balance plus the overdraft limit
     */
    @Override
    public void withdraw(MonetaryValue amount)
            throws NegativeMonetaryValueException, InsufficientFundsException {

        if (amount.isNegative()) {
            throw new NegativeMonetaryValueException(amount);
        } 

        MonetaryValue limit = balance.plus(overdraftLimit);

        if (amount.compareTo(limit) > 0) {
            throw new InsufficientFundsException(balance, amount);
        }

        balance = balance.minus(amount);
    }

    /** 
     * Determines whether this checking account is equal to {@code o}. They are 
     * considered equal if and only if {@code o} refers to an instance of 
     * {@code CheckingAccount} whose account number, balance, date opened, name, and overdraft limit
     * are equal to the corresponding fields of this checking account.
     * @param o The thing to compare this checking account with
     * @return {@code true} if this checking account is equal to {@code o}, {@code false} otherwise 
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CheckingAccount) {
            CheckingAccount other = (CheckingAccount) o;
            return super.equals(other) && this.overdraftLimit.equals(other.overdraftLimit);
        } else {
            return false;
        }
    }

    /** 
     * Returns a string representation of this checking account, suitable for printing 
     * to a .csv (comma-separated values) file. For example: 
     * <p>
     * {@code "CheckingAccount,11112222,150.0,2020-8-23,John Adam Smith,50.0"}
     * @return A string representation of this checking account
     */
    @Override
    public String toString() {
        return "CheckingAccount," + super.toString().substring(12) + "," + overdraftLimit.toDouble();
    }
}