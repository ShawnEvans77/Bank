/**
 * A class for representing names, which contain a first name, a last name, 
 * and an optional middle name.
 */
public class Name implements Comparable<Name> {
    /**
     * The first name.
     */
    private String first;

    /**
     * The optional middle name (set to {@code ""} if not used).
     */
    private String middle;

    /**
     * The last name.
     */
    private String last;
    
    /**
     * Constructs a name with the specified first name and last name.
     * @param first The first name
     * @param last The last name
     * @throws IllegalArgumentException If any of the parameters are null; thrown by a setter method (setFirst, setMiddle, or setLast)
     */
    public Name(String first, String last) throws IllegalArgumentException {
        this(first, "", last);
    }

    /**
     * Constructs a name with the specified first name, middle name, and last name.
     * @param first The first name
     * @param middle The middle name
     * @param last The last name
     * @throws IllegalArgumentException If any of the parameters are null; thrown by a setter method (setFirst, setMiddle, or setLast)
     */
    public Name(String first, String middle, String last) throws IllegalArgumentException {
        setFirst(first);
        setMiddle(middle);
        setLast(last);
    }

    /**
     * Copy constructor. Constructs a new name that is a copy of the specified original name. 
     * @param original The name to make a copy of
     */
    public Name(Name original) {
        this(original.first, original.middle, original.last);
    }
    
    /**
     * Sets the first name.
     * @param first The desired first name
     * @throws IllegalArgumentException If the parameter is null
     */
    public void setFirst(String first) throws IllegalArgumentException {
        if (first == null) {
            throw new IllegalArgumentException("first cannot be null");
        }
        this.first = first;
    }
    
    /**
     * Sets the middle name.
     * @param middle The desired middle name
     * @throws IllegalArgumentException If the parameter is null
     */
    public void setMiddle(String middle) throws IllegalArgumentException {
        if (middle == null) {
            throw new IllegalArgumentException("middle cannot be null");
        }
        this.middle = middle;

    }
    
    /**
     * Sets the last name.
     * @param last The desired last name
     * @throws IllegalArgumentException If the parameter is null
     */
    public void setLast(String last) throws IllegalArgumentException {
        if (last == null) {
            throw new IllegalArgumentException("last cannot be null");
        }
        this.last = last;
    }

    /**
     * Returns the first name.
     * @return The first name
     */
    public String getFirst() {
        return first;
    }
    
    /**
     * Returns the middle name.
     * @return The middle name
     */
    public String getMiddle() {
        return middle;
    }
    
    /**
     * Returns the last name.
     * @return The last name
     */
    public String getLast() {
        return last;
    }

    /**
     * Returns a string representation of this name, such as "John Smith".
     * @return A string representation of this name
     */
    public String toString() {
        return first + " " + middle + (middle.isEmpty()? "" : " ") + last;
    }
    
    /** 
     * Determines whether this name is equal to {@code o}. They are considered to be equal 
     * if and only if {@code o} refers to a {@code Name} instance whose first, middle, and last names
     * are equal to those of this name. 
     * @param o The thing to compare this name with
     * @return {@code true} if this name is equal to {@code o}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Name) {
            Name other = (Name) o;

            return this.first.equals(other.first) 
                && this.middle.equals(other.middle) 
                && this.last.equals(other.last);
        } else {
            return false;
        }
    }
    
    /** 
     * Compares this name with the specified name. First the names are compared based on the 
     * lexicographic order of the last names (using the 
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#compareTo(java.lang.String)" target="_blank">compareTo</a> 
     * method of the String class). If the last names are equal, then the names are 
     * compared based on the lexicographic order of the first names. If the first names are also
     * equal, then the names are compared based on the lexicographic order of the middle names. 
     * @param other The name to compare this name with
     * @return 0 if this name is equal to {@code other}, a positive integer if this name is 
     *         greater than {@code other}, or a negative integer if this name is less than 
     *         {@code other}
     */
    @Override
    public int compareTo(Name other) {
        if (!this.last.equals(other.last)) {
            return this.last.compareTo(other.last);
        } else if (!this.first.equals(other.first)) {
            return this.first.compareTo(other.first);
        } else {
            return this.middle.compareTo(other.middle);
        }

    }
}
