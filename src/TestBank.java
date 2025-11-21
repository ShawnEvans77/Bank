// Do not modify this file.

/*
tested with this input file: 

BankAccount,11112222,150.0,2020-08-23,John Adam Smith
CheckingAccount,22223333,200.0,2021-07-14,Dane Doe,50.0
CDAccount,33334444,300.0,2022-01-01,John Adam Smith,6
CheckingAccount,11112222,400.0,2021-07-14,Dane Smith,50.0
BankAccount,4444555,100.0,2020-08-23,John Adam Smith
BankAccount,444455555,100.0,2020-08-23,John Adam Smith
BankAccount,4444555a,100.0,2020-08-23,John Adam Smith
BankAccount,44445555,100.0,2020-08-23,John
BankAccount,55556666,100.0,2020-08-23,John Quincy Adam Smith
OtherTypeOfAccount,66667777,100.0,2020-08-23,John Adam Smith
*/

/*
got this output file:

BankAccount,11112222,250.0,2020-08-23,John Adam Smith
CheckingAccount,22223333,-25.0,2021-07-14,Dane Doe,50.0
CDAccount,33334444,300.0,2022-01-01,John Adam Smith,6
BankAccount,55556666,0.0,2022-05-01,Jane Doe
*/

import java.io.File;
import java.time.LocalDate;

public class TestBank {
    public static void main(String[] args) {
        Bank bank = testConstructor();
        System.out.println();

        testToString(bank);
        System.out.println();

        testAddAccount(bank);
        System.out.println();

        testDeposit(bank);
        System.out.println();

        testWithdraw(bank);
        System.out.println();

        testGetBalance(bank);
        System.out.println();

        testPrintToFile(bank);
    }

    private static Bank testConstructor() {
        File file = new File("input_accounts.csv");
        Bank bank = new Bank(file);
        return bank;
    }

    private static void testToString(Bank bank) {
        System.out.println(bank);
    }

    private static void testAddAccount(Bank bank) {
        try {
            BankAccount account = new BankAccount("22223333", MonetaryValue.ZERO, LocalDate.parse("2022-05-01"), new Name("Jane", "Doe"));
            bank.addAccount(account);
        } catch (DuplicateAccountNumberException e) {
            System.out.println(e);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            BankAccount account = new BankAccount("55556666", MonetaryValue.ZERO, LocalDate.parse("2022-05-01"), new Name("Jane", "Doe"));
            bank.addAccount(account);
        } catch (DuplicateAccountNumberException e) {
            System.out.println("A DuplicateAccountNumberException should not occur here");
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }
    }

    private static void testDeposit(Bank bank) {
        try {
            bank.deposit("11112222", new MonetaryValue(-100.0));
        } catch (NegativeMonetaryValueException e) {
            System.out.println(e);
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException shouldn't occur here");
        }

        try {
            bank.deposit("99999999", new MonetaryValue(100.0));
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException shouldn't occur here");
        } catch (NonexistentAccountException e) {
            System.out.println(e);
        }

        try {
            bank.deposit("11112222", new MonetaryValue(100.0)); // should be successful
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException shouldn't occur here");
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException shouldn't occur here");
        }

        try {
            System.out.println(bank.getBalance("11112222")); // $250.00
        } catch (NonexistentAccountException e) {
            System.out.println("An exception should not occur here");
        }
    }

    private static void testWithdraw(Bank bank) {
        try {
            bank.withdraw("22223333", new MonetaryValue(-300.0));
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        } catch (NegativeMonetaryValueException e) {
            System.out.println(e);
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        }

        try {
            bank.withdraw("99999999", new MonetaryValue(300.0));
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (NonexistentAccountException e) {
            System.out.println(e);
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        }
        
        try {
            bank.withdraw("22223333", new MonetaryValue(300.0));
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println(e);
        }

        try {
            // this should be successful: 22223333 is a CheckingAccount with balance of 200 and overdraft limit of 50
            bank.withdraw("22223333", new MonetaryValue(225.0)); 
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here"); 
        }

        try {
            bank.withdraw("33334444", new MonetaryValue(275.0)); 
        } catch (WithdrawalDuringTermException e) {
            System.out.println(e);
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (NonexistentAccountException e) {
            System.out.println("A NonexistentAccountException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here"); 
        }
    }

    private static void testGetBalance(Bank bank) {
        try {
            System.out.println(bank.getBalance("99999999"));
        } catch (NonexistentAccountException e) {
            System.out.println(e);
        }

        try {
            System.out.println(bank.getBalance("22223333")); // $-25.00
        } catch (NonexistentAccountException e) {
            System.out.println("An exception should not occur here");
        }
    }

    private static void testPrintToFile(Bank bank) {
        bank.printToFile("output_accounts.csv");
    }
}