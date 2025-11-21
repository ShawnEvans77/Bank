import java.time.LocalDate;
import java.time.Period;

/**
 * A class for representing situations in which a withdrawal has been attempted during the 
 * term of a CDAccount, when withdrawals are not allowed. 
 * <p>
 * Note that for technical reasons this class is a direct subclass of RuntimeException, 
 * not Exception.
 */
// note: extends RuntimeException, not Exception, so as not to break the override contract
// in the withdraw method of CDAccount
public class WithdrawalDuringTermException extends RuntimeException {
    /**
     * The start date of the term (typically when the account was opened).
     */
    private LocalDate startDate;

    /**
     * The length of the term; for example, 4 months.
     */
    private Period term;

    /**
     * Constructs an exception object whose message indicates that the specified term 
     * began on the specified start date.
     * @param startDate The term's start date
     * @param term The term
     */
    public WithdrawalDuringTermException(LocalDate startDate, Period term) {
        super("term of " + term + " began on " + startDate);
        this.startDate = startDate;
        this.term = term;
    }

    /** 
     * Returns the date on which the term ends. 
     * <p>
     * <b>Note:</b> See the
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html#plus(java.time.temporal.TemporalAmount)" target="_blank">plus method</a>
     * of the {@code LocalDate} class; read the first two paragraphs of the documentation for that method. 
     * @return The term's end date
     */
    public LocalDate getEndDate() {
        return startDate.plus(term);
    }
}
