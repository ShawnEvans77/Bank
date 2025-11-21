/* Do not modify this file */

import java.time.LocalDate;

public class TestCheckingAccount {
    public static void main(String[] args) {
        testFirstConstructor();
        testSecondConstructor();
        testWithdraw();
        testEquals();
    }

    public static void testFirstConstructor() {
        BankAccount account = new CheckingAccount(new Name("Jane", "Doe"), new MonetaryValue(50.00));
        account.toString();
    }

    public static void testSecondConstructor() {
        try {
            BankAccount account = new CheckingAccount("11112222", new MonetaryValue(100.0), LocalDate.parse("2022-04-23"), new Name("Jane", "Doe"), new MonetaryValue(50.00));
            System.out.println(account);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            BankAccount account = new CheckingAccount("1111222", new MonetaryValue(100.0), LocalDate.parse("2022-04-23"), new Name("Jane", "Doe"), new MonetaryValue(50.00));
            System.out.println(account);
        } catch (InvalidAccountNumberException e) {
            System.out.println(e);
        }
    }

    public static void testWithdraw() {
        BankAccount account1 = null;

        try {
            account1 = new CheckingAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), new MonetaryValue(50.00));
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            account1.withdraw(new MonetaryValue(50.00));
            System.out.println(account1.getBalance());  // $150.00
            account1.withdraw(new MonetaryValue(-50.00));
        } catch (NegativeMonetaryValueException e) {
            System.out.println(e);
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        } 

        try {
            account1.withdraw(new MonetaryValue(150.00));
            System.out.println(account1.getBalance());  // $0.00
            account1.withdraw(new MonetaryValue(50.01));
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("InsufficientFundsException");
        } 

        BankAccount account2 = null;

        try {
            account2 = new CheckingAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), new MonetaryValue(50.00));
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            account2.withdraw(new MonetaryValue(250.00));
            System.out.println(account2.getBalance());
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        } 
    }

    public static void testEquals() {
        try {
            Object account1 = new CheckingAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), new MonetaryValue(30.00));
            Object account2 = new CheckingAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), new MonetaryValue(60.00));
            System.out.println(account1.equals(account2));  // false
            Object account3 = new CheckingAccount("11112222", new MonetaryValue(100.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), new MonetaryValue(30.00));
            System.out.println(account1.equals(account3));  // false
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }
    }
}