/* Do not modify this file. */

import java.time.LocalDate;
import java.time.Period;

public class TestCDAccount {
    public static void main(String[] args) {
        testFirstConstructor();
        testSecondConstructor();
        testWithdraw();
        testEquals();
    }

    public static void testFirstConstructor() {
        BankAccount account = new CDAccount(new Name("Jane", "Doe"), Period.ofMonths(4));
        account.toString();
    }

    public static void testSecondConstructor() {
        try {
            BankAccount account = new CDAccount("11112222", new MonetaryValue(100.0), LocalDate.parse("2022-04-23"), new Name("Jane", "Doe"), Period.ofMonths(4));
            System.out.println(account);
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }

        try {
            BankAccount account = new CDAccount("1111222", new MonetaryValue(100.0), LocalDate.parse("2022-04-23"), new Name("Jane", "Doe"), Period.ofMonths(4));
            System.out.println(account);
        } catch (InvalidAccountNumberException e) {
            System.out.println(e);
        }
    }

    public static void testWithdraw() {
        BankAccount account1 = null, account2 = null;

        try {
            account1 = new CDAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), Period.ofMonths(3));
            account2 = new CDAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), Period.ofMonths(6));
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
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        }

        try {
            account1.withdraw(new MonetaryValue(150.00));
            System.out.println(account1.getBalance());  // $0.00
            account1.withdraw(new MonetaryValue(0.01));
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println(e);
        } catch (WithdrawalDuringTermException e) {
            System.out.println("A WithdrawalDuringTermException should not occur here");
        }

        try {
            account2.withdraw(new MonetaryValue(50.00));
        } catch (NegativeMonetaryValueException e) {
            System.out.println("A NegativeMonetaryValueException should not occur here");
        } catch (InsufficientFundsException e) {
            System.out.println("An InsufficientFundsException should not occur here");
        } catch (WithdrawalDuringTermException e) {
            System.out.println(e);
            System.out.println("End date: " + e.getEndDate());
        }
    }

    public static void testEquals() {
        try {
            Object account1 = new CDAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), Period.ofMonths(3));
            Object account2 = new CDAccount("11112222", new MonetaryValue(200.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), Period.ofMonths(6));
            System.out.println(account1.equals(account2));  // false
            Object account3 = new CDAccount("11112222", new MonetaryValue(100.00), LocalDate.parse("2022-01-01"), new Name("John", "Doe"), Period.ofMonths(3));
            System.out.println(account1.equals(account3));  // false
        } catch (InvalidAccountNumberException e) {
            System.out.println("An InvalidAccountNumberException should not occur here");
        }
    }
}
