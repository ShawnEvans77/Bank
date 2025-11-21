/**
 * A class for representing US monetary values, positive or negative.
 */
public class MonetaryValue implements Comparable<MonetaryValue> {
    /**
     * The number of pennies that this monetary value is made up of.
     */
    private int cents;
    /**
     * A constant representing $0.00.
     */
    public static final MonetaryValue ZERO = new MonetaryValue(0);

    /**
     * Constructs a default monetary value, which equals $0.00.
     */
    public MonetaryValue() {
        this(0);
    }

    /**
     * Constructs a monetary value made up of the specified number of cents (pennies).
     * @param cents The number of cents (pennies) that this monetary value represents 
     */
    public MonetaryValue(int cents) {
        this.cents = cents;
    }

    /**
     * Constructs a monetary value from the specified {@code double} value.
     * @param amount A {@code double}
     */
    public MonetaryValue(double amount) {
        this.cents = (int) (amount * 100);
    }

    /**
     * Copy constructor. Constructs a new monetary value that is a copy of the 
     * specified original monetary value. 
     * @param original The monetary value to make a copy of
     */
    public MonetaryValue(MonetaryValue original) {
        this.cents = original.cents;
    }

    /** 
     * Returns a string representation of this monetary value; for example, "$-59.70".
     * @return A string representation of this monetary value
     */
    public String toString() {
        int absCents = Math.abs(cents);
	    return "$"
	            + (isNegative()? "-" : "")
                + absCents / 100
                + "."
                + ((absCents % 100) < 10? "0" : "")
                + absCents % 100;
    }

    // alternative version:
    /*
    public String toString() {
        int dollarsPart = Math.abs(cents / 100), centsPart = Math.abs(cents % 100);
        String result = "$";

        if (isNegative()) {
            result += "-";
        }

        result += dollarsPart + ".";

        if (centsPart < 10) {
            result += "0" + centsPart;
        } else {
            result += centsPart;
        }

        return result;
    }
    */

    /** 
     * Returns a {@code double} representation of this monetary value.
     * @return a {@code double} representation of this monetary value
     */
    public double toDouble() {
        return cents / 100.0;
    }

    
    /** 
     * Determines whether this monetary value is negative.
     * @return {@code true} is this monetary value is negative, {@code false} otherwise
     */
    public boolean isNegative() {
        return cents < 0;
    }

    
     /** 
     * Determines whether this monetary value is equal to {@code o}. 
     * They are considered to be equal if and only if {@code o} refers to a 
     * {@code MonetaryValue} instance that represents the same amount of money 
     * as this monetary value. 
     * @param o The thing to compare this monetary value with
     * @return {@code true} if this monetary value is equal to {@code o}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MonetaryValue) {
            MonetaryValue other = (MonetaryValue) o;
            return this.cents == other.cents;
        } else {
            return false;
        }
    }
        
    /** 
     * Compares this monetary value with the specified monetary value. 
     * @param other The monetary value to compare this monetary value with
     * @return 0 if this monetary value is equal to {@code other}, 
     *         a positive integer if this monetary value is greater than {@code other}, 
     *         or a negative integer if this monetary value is less than {@code other}
     */
    @Override
    public int compareTo(MonetaryValue other) {
        return this.cents - other.cents;
    }

    /** 
     * Returns the sum of this monetary value and {@code amount}.
     * @param amount A monetary value
     * @return The sum of this monetary value and {@code amount}
     */
    public MonetaryValue plus(MonetaryValue amount) {
        return new MonetaryValue(this.cents + amount.cents);
    }

    /** 
     * Returns the difference of this monetary value and {@code amount}.
     * @param amount A monetary value
     * @return The difference of this monetary value and {@code amount}
     */
    public MonetaryValue minus(MonetaryValue amount) {
        return new MonetaryValue(this.cents - amount.cents);
    }

    
    /** 
     * Returns the sum of two monetary values.
     * @param value1 A monetary value
     * @param value2 Another monetary value
     * @return The sum of the two monetary values
     */
    public static MonetaryValue sum(MonetaryValue value1, MonetaryValue value2) {
        return new MonetaryValue(value1.cents + value2.cents);
    }

    
    /** 
     * Returns the difference of two monetary values.
     * @param value1 A monetary value
     * @param value2 Another monetary value
     * @return The difference of the two monetary values
     */
    public static MonetaryValue difference(MonetaryValue value1, MonetaryValue value2) {
        return new MonetaryValue(value1.cents - value2.cents);
    }
}