import java.io.File;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The driver of the program.
 */
public class Main {
    /** 
     * Creates a bank by sending a File object to the constructor of the Bank class. 
     * The File object corresponds to an actual file (as usual). The name of that file can
     * be passed as a command line argument. If no command line argument is provided, the
     * user should be prompted for the file name. In either case, if the provided file name
     * doesn't exist, the program prompts the user repeatedly to enter a valid file name, 
     * until such a name is entered. 
     * <p>
     * Once the bank has been created, the program repeatedly gives the user the menu options
     * and performs the operation chosen by the user. Thus continues until the user decides
     * to quit. 
     * <p>
     * At the end, the user is prompted for an output file name, and the bank is printed to
     * the output file in CSV (comma-separated values) format.
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String inputFileName;
        File file;

        if (args.length != 0) {
            inputFileName = args[0];
        } else {
            System.out.print("Enter input file name: ");
            inputFileName = keyboard.next();
        }
        
        file = new File(inputFileName);

        while (!file.exists()) {
            System.out.print("Enter input file name: ");
            inputFileName = keyboard.next();
            file = new File(inputFileName);
        }
    

        Bank bank = new Bank(file);

        char choice;
        do {
            choice = printMenuAndGetChoice(keyboard);
            switch (choice) {
                case 'b':
                    lookupBalance(keyboard, bank);
                    break;
                case 'd':
                    makeDeposit(keyboard, bank);
                    break;
                case 'w':
                    makeWithdrawal(keyboard, bank);
                    break;
                case 'a':
                    addAccount(keyboard, bank);
                    break;
                case 'q':
                    break;
                case 'p':
                    printtoscreen(bank);
                    break;
                default:
                    System.out.println("Invalid option; try again.");
            }
        } while (choice != 'q');

        System.out.println();
        System.out.print("Output file name: ");
        String filename = keyboard.next();
        bank.printToFile(filename);
    }

    /**
     * Prints the menu and returns the user's selection.
     * 
     * @param sc The keyboard scanner
     * @return The user's selection, as a character
     */
    private static char printMenuAndGetChoice(Scanner sc) {
        System.out.println();
        System.out.println("Choices:");
        System.out.println("b: lookup balance");
        System.out.println("d: make deposit");
        System.out.println("w: make withdrawal");
        System.out.println("a: add account");
        System.out.println("p: print accounts to screen");
        System.out.println("q: quit");
        System.out.print("Enter a letter choice: ");
        return sc.next().charAt(0);
    }

    /**
     * Gets the account number from the user and looks up the balance.
     * If successful, prints the balance. Otherwise, prints an error message.
     * 
     * @param keyboard The keyboard scanner
     * @param bank     The bank
     */
    private static void lookupBalance(Scanner keyboard, Bank bank) {
        
        System.out.print("Enter an account number: ");
        String accountNumber = keyboard.next();
        try {
            System.out.println("Balance: " + bank.getBalance(accountNumber));
        } 
        catch (NonexistentAccountException ex) {
            System.out.println("Account number " + accountNumber + " doesn't exist, try again.");
        } 
    }

    /**
     * Gets the account number and the amount from the user, and makes the deposit.
     * If successful, prints the new balance. Otherwise, prints an appropriate error
     * message based on the type of exception that ocurred.
     * 
     * @param keyboard The keyboard scanner
     * @param bank     The bank
     */
    private static void makeDeposit(Scanner keyboard, Bank bank) {
        System.out.print("Enter an account number: ");
        String accountNumber = keyboard.next();
        System.out.print("Enter an amount to deposit: ");
        try {
        double amounttodeposit = keyboard.nextDouble();
        MonetaryValue deposit = new MonetaryValue(amounttodeposit);
        bank.deposit(accountNumber, deposit);
        } catch (InputMismatchException ex) {
            System.out.println("Please input a numerical value");
        } catch (NonexistentAccountException ex) {
            System.out.println("Account number " + accountNumber + " doesn't exist, try again.");
        } catch (NegativeMonetaryValueException ex) {
            System.out.println("Cannot have a negative amount; try again.");
        }
    }

    /**
     * Gets the account number and the amount from the user, and makes the
     * withdrawal.
     * If successful, prints the new balance. Otherwise, prints an appropriate error
     * message based on the type of exception that ocurred.
     * 
     * @param keyboard The keyboard scanner
     * @param bank     The bank
     */
    private static void makeWithdrawal(Scanner keyboard, Bank bank) {

        System.out.print("Enter an account number: ");
        String accountNumber = keyboard.next();
        System.out.print("Enter an amount to withdraw: ");
        try {
        double amounttowithdraw = keyboard.nextDouble();

        MonetaryValue withdraw = new MonetaryValue(amounttowithdraw);
        bank.withdraw(accountNumber, withdraw);
        } catch (InputMismatchException ex) {
            System.out.println("Error: Please input a numerical value");
        } catch (NonexistentAccountException ex) {
            System.out.println("Account number " + accountNumber + " doesn't exist, try again.");
        } catch (NegativeMonetaryValueException ex) {
            System.out.println("Cannot have a negative amount, try again.");
        } catch (InsufficientFundsException ex) {
            System.out.println("Withdrawl unsuccessful: " + ex.getMessage());
        } catch (WithdrawalDuringTermException ex) {
            System.out.println("Withdraw unsuccessful: cannot withdraw until " + ex.getEndDate());
        }
    }

    /**
     * Gets the account type and the name from the user, creates the appropriate
     * type of
     * account, and adds it to the bank. If successful, prints a message.
     * 
     * @param keyboard The keyboard scanner
     * @param bank     The bank
     */
    private static void addAccount(Scanner keyboard, Bank bank) {
        System.out.print("AccountType (CheckingAccount/CDAccount/BankAccount): ");
        String accounttype = keyboard.next();
        keyboard.nextLine();
        System.out.print("Enter the account's name: ");
        String accname = keyboard.nextLine();
        String[] nameparts = accname.split(" ");

        Name name;

        switch (nameparts.length) {
            case 2:
                name = new Name(nameparts[0], nameparts[1]);
                break;
            case 3:
                name = new Name(nameparts[0], nameparts[1], nameparts[2]);
                break;
            default:
                System.out.println("The name must be two or three parts.");
                return;
        }

        BankAccount acc;
        try {
        switch (accounttype) {
            case "CDAccount":
                System.out.print("Enter the period: ");
                int period = keyboard.nextInt();
                acc = new CDAccount(name, Period.ofMonths(period));
                System.out.println("Account added.");
                break;
            case "CheckingAccount":
                System.out.print("Enter the overdraft limit: ");
                double val = keyboard.nextDouble();
                MonetaryValue lim = new MonetaryValue(val);
                acc = new CheckingAccount(name, lim);
                System.out.println("Account added.");
                break;
            case "BankAccount":
                acc = new BankAccount(name);
                System.out.println("Account added.");
                break;
            default:
                System.out.println("Invalid account type, try again.");
                return;
        }
            bank.addAccount(acc);
        } catch (InputMismatchException ex) {
            System.out.println("Error: Please input a numerical value");
        } catch (DuplicateAccountNumberException ex) {
            // won't ever occur, technically
        }
    }

    private static void printtoscreen(Bank bank) {
        System.out.println(bank);
    }
}