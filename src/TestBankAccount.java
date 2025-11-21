// Do not modify this file.

import java.time.LocalDate;

public class TestBankAccount {
	public static void main(String[] args) {
        /* testing constructor */

        try {
            BankAccount account1 = new BankAccount("11112222", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            System.out.println(account1);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            BankAccount account = new BankAccount("1111222", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account.toString();
        } catch (InvalidAccountNumberException e) {
            System.out.println(e);
        }

        try {
            BankAccount account = new BankAccount("111122222", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account.toString();
        } catch (InvalidAccountNumberException e) {
            System.out.println(e);
        }

        try {
            BankAccount account = new BankAccount("1111222a", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account.toString();
        } catch (InvalidAccountNumberException e) {
            System.out.println(e);
        }

        /* testing equals */

        try {
            BankAccount account1 = new BankAccount("11112222", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            BankAccount account2 = new BankAccount("11112222", new MonetaryValue(100.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            System.out.println(account1.equals(account2));  // true
            BankAccount account3 = new BankAccount("11112222", new MonetaryValue(1000.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            System.out.println(account1.equals(account3));  // false
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        /* testing deposit */

        try {
            BankAccount account1 = new BankAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account1.deposit(new MonetaryValue(50.00));
            System.out.println(account1.getBalance());  // $250.00
            account1.deposit(new MonetaryValue(-50.00));
        } catch (NegativeMonetaryValueException e) {
            System.out.println(e);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        /* testing withdraw */

        try {
            BankAccount account1 = new BankAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account1.withdraw(new MonetaryValue(50.00));
            System.out.println(account1.getBalance());  // $150.00
            account1.withdraw(new MonetaryValue(-50.00));
        } catch (NegativeMonetaryValueException e) {
            System.out.println(e);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        }

        try {
            BankAccount account1 = new BankAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-04-23"), new Name("John", "Doe"));
            account1.withdraw(new MonetaryValue(200.00));
            System.out.println(account1.getBalance());  // $0.00
            account1.withdraw(new MonetaryValue(0.01));
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println(e);
        }
    }
}