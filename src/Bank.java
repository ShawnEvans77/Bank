import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class for representing a bank. A bank has a list of accounts, which can be
 * plain bank accounts, checking accounts, or CD accounts.
 */
public class Bank {
    /**
     * The list of accounts in this bank.
     */
    private ArrayList<BankAccount> accounts;

    /**
     * Creates the ArrayList. Then reads the file line by line, passing each line to
     * the
     * processLine method for processing. If any exception occurs during the
     * processing of a
     * line, prints the exception and moves on to the next line.
     * 
     * @param file The file from which to read the accounts
     */
    public Bank(File file) {
        accounts = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {

                try {
                    processLine(sc.nextLine());
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Processes one line of input. Divides the line up into its parts, creates an
     * account of
     * the appropriate type containing the information from the line, and adds the
     * account
     * to the list of accounts.
     * <p>
     * The line is expected to be made up of the following five (or six)
     * comma-separated values:
     * <ul>
     * <li>The type of account: "BankAccount" or "CDAccount" or
     * "CheckingAccount"</li>
     * <li>The account number</li>
     * <li>The balance as a {@code double}</li>
     * <li>The date the account was opened, in the form year-month-dayOfMonth (for
     * example, 2020-08-23)</li>
     * <li>The name of the account holder</li>
     * </ul>
     * At the end the line, if it's a CD account, there should be an integer
     * representing
     * the length of the term in months; if it's a checking account there should
     * instead be
     * the overdraft limit as a {@code double}.
     * <p>
     * <b>Note:</b> The following methods may be helpful: the
     * <a href=
     * "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#split(java.lang.String)"
     * target="_blank">split method</a>
     * of the String class, and the
     * <a href=
     * "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html#parse(java.lang.CharSequence)"
     * target="_blank">parse method</a>
     * or <a href=
     * "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html#of(int,int,int)"
     * target="_blank">of method</a>
     * of the LocalDate class.
     * 
     * @param line The line to process
     * @throws DuplicateAccountNumberException If the account number already exists
     *                                         in the bank
     * @throws InvalidAccountNumberException   If the account number is invalid
     * @throws InvalidNameException            If the name does not consist of
     *                                         either two or three parts
     * @throws InputMismatchException          If the first item on the line isn't
     *                                         one of the three account types
     */
    private void processLine(String line)
            throws DuplicateAccountNumberException, InvalidAccountNumberException,
            InvalidNameException, InputMismatchException {

        String[] tokens = line.split(",");

        String accounttype = tokens[0];

        if (! (accounttype.equals("BankAccount") || accounttype.equals("CDAccount")
                || accounttype.equals("CheckingAccount"))) {
            throw new InputMismatchException("incorrect accounttype: " + accounttype);
        }

        String accountNumber = tokens[1];

        if (fetchaccount(accountNumber) != null) {
            throw new DuplicateAccountNumberException(accountNumber);
        }

        String[] accountname = tokens[4].split(" ");

        if (accountname.length != 2 && accountname.length != 3) {
            throw new InvalidNameException(tokens[4]);
        }

        Name name;

        if (accountname.length == 2) {
            name = new Name(accountname[0], accountname[1]);
        } else {
            name = new Name(accountname[0], accountname[1], accountname[2]);
        }
        
        LocalDate date = LocalDate.parse(tokens[3]);

        MonetaryValue balance = new MonetaryValue(Double.parseDouble(tokens[2]));

        BankAccount acc;
        
        switch (accounttype) {
            
            case "CDAccount":
                int period = Integer.parseInt(tokens[5]);
                Period term = Period.ofMonths(period);
                acc = new CDAccount(accountNumber, balance, date, name, term);
                break;
            case "CheckingAccount":
                MonetaryValue overdraftlimit = new MonetaryValue(Double.parseDouble(tokens[5]));
                acc = new CheckingAccount(accountNumber, balance, date, name, overdraftlimit);
                break;
            default:
                acc = new BankAccount(accountNumber, balance, date, name);
                break;

        }

        accounts.add(acc);

    }

    /**
     * Adds the specified account to the bank.
     * 
     * @param account The account to add
     * @throws DuplicateAccountNumberException If the bank already has an account
     *                                         with
     *                                         the same account number as the
     *                                         specified account
     */
    public void addAccount(BankAccount account) throws DuplicateAccountNumberException {

        BankAccount tocheck = fetchaccount(account.getAccountNumber());

        if (tocheck != null) {
           throw new DuplicateAccountNumberException(account.getAccountNumber());
        }
        accounts.add(account);
    }

    /**
     * Deposits the specified amount of money into the account with the specified
     * account number. The method does this by calling the deposit method of
     * BankAccount.
     * 
     * @param accountNumber The account number of the account we are interested in
     * @param amount        The amount to deposit
     * @throws NegativeMonetaryValueException If the amount to deposit is negative
     * @throws NonexistentAccountException    If there is no account in the bank
     *                                        with the
     *                                        specified account number
     */
    public void deposit(String accountNumber, MonetaryValue amount)
            throws NegativeMonetaryValueException, NonexistentAccountException {

        BankAccount tocheck = fetchaccount(accountNumber);
        
        if (tocheck == null) {
            throw new NonexistentAccountException(accountNumber);
        }
        
        tocheck.deposit(amount);
    }

    /**
     * Withdraws the specified amount of money from the account with the specified
     * account number. The method does this by calling a withdraw method of
     * BankAccount or
     * one of its subclasses.
     * 
     * @param accountNumber The account number of the account we are interested in
     * @param amount        The amount to withdraw
     * @throws NegativeMonetaryValueException If the amount to withdraw is negative
     * @throws NonexistentAccountException    If there is no account in the bank
     *                                        with the
     *                                        specified account number
     * @throws InsufficientFundsException     If there are insufficient funds in the
     *                                        account (the
     *                                        precise meaning of this depends on
     *                                        whether the account is a checking
     *                                        account or
     *                                        some other type of account)
     * @throws WithdrawalDuringTermException  If the account is a CD account and it
     *                                        is still
     *                                        during the term
     */
    public void withdraw(String accountNumber, MonetaryValue amount)
            throws NegativeMonetaryValueException, NonexistentAccountException,
            InsufficientFundsException, WithdrawalDuringTermException {

        BankAccount tocheck = fetchaccount(accountNumber);

        if (tocheck == null) {
            throw new NonexistentAccountException(accountNumber);
        }

        tocheck.withdraw(amount);
    }

    /**
     * Returns the balance of the account with the specified account number.
     * 
     * @param accountNumber The account number of the account we are interested in
     * @return The account's balance
     * @throws NonexistentAccountException If there is no account in the bank with
     *                                     the specified account number
     */
    public MonetaryValue getBalance(String accountNumber) throws NonexistentAccountException {

        BankAccount tocheck = fetchaccount(accountNumber);
        if (tocheck == null) {
            throw new NonexistentAccountException(accountNumber);
        }
        return tocheck.getBalance();
    }

    /** Simple method to find an account in the arraylist of bankaccounts based on a string accountnumber.
     * 
     * @param accountNumber
     * @return null if the accountnumber does not exist in the arraylist of bankaccounts, the account if it does exist
     */

    private BankAccount fetchaccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of this bank. Each account is put on its own
     * line.
     * 
     * @return A string representation of this bank
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (BankAccount account : accounts) {
            res.append(account.toString() + "\n");
        }
        return res.toString();
    }

    /**
     * Prints the bank to a file in comma-separated value format.
     * Each account is printed on its own line.
     * 
     * @param filename The name of the file to print to
     */
    public void printToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(filename)) {
            pw.println(this.toString());
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}