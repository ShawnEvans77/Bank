// Do not modify this file.

public class TestMonetaryValue {
    public static void main(String[] args) {
        testEquals();
        testMonetaryValueImplementsComparable();
        testCompareToEqualsZero();
        testCompareToLessThanZero();
        testCompareToGreaterThanZero();
    }

    private static void testEquals() {
        MonetaryValue mv1 = new MonetaryValue(5.37);
        MonetaryValue mv2 = new MonetaryValue(5.37);
        System.out.println(mv1.equals(mv2));           // true
    }

    private static void testMonetaryValueImplementsComparable() {
        MonetaryValue mv1 = new MonetaryValue(5.37);
        System.out.println(mv1 instanceof Comparable); // true
    }

    private static void testCompareToEqualsZero() {
        MonetaryValue mv1 = new MonetaryValue(5.37);
        MonetaryValue mv2 = new MonetaryValue(5.37);
        System.out.println(mv1.compareTo(mv2) == 0);   // true
    }

    private static void testCompareToLessThanZero() {
        MonetaryValue mv1 = new MonetaryValue(5.37);
        MonetaryValue mv2 = new MonetaryValue(5.35);
        System.out.println(mv2.compareTo(mv1) < 0);    // true
    }

    private static void testCompareToGreaterThanZero() {
        MonetaryValue mv1 = new MonetaryValue(5.37);
        MonetaryValue mv2 = new MonetaryValue(5.35);
        System.out.println(mv1.compareTo(mv2) > 0);    // true
    }
}