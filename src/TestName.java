// Do not modify this file.

public class TestName {
    public static void main(String[] args) {
        testThrowExceptionIfSetFirstNull();
        testThrowExceptionIfSetMiddleNull();
        testThrowExceptionIfSetLastNull();
        testCompareToBySorting();
    }

    private static void testThrowExceptionIfSetFirstNull() {
        try {
            Name name = new Name(null, "John", "Smith");
            name.toString();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // first cannot be null
        }
    }

    private static void testThrowExceptionIfSetMiddleNull() {
        try {
            Name name = new Name("John", null, "Smith");
            name.toString();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // middle cannot be null
        }
    }

    private static void testThrowExceptionIfSetLastNull() {
        try {
            Name name = new Name("John", "Smith", null);
            name.toString();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // last cannot be null
        }
    }

    private static void testCompareToBySorting() {
        Name[] names = {
            new Name("John", "Darren", "Doe"),
            new Name("Jane", "Dalia", "Doe"),
            new Name("John", "Darren", "Smith"),
            new Name("Jane", "Dalia", "Smith"),
            new Name("John", "Christopher", "Doe"),
            new Name("Jane", "Christina", "Doe"),
            new Name("John", "Christopher", "Smith"),
            new Name("Jane", "Christina", "Smith"),
        };

        java.util.Arrays.sort(names);

        for (Name name : names) {
            System.out.println(name);
        }
    }
}