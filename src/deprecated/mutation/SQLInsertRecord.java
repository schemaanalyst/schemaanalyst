package deprecated.mutation;

/**
 * Represents the two-tuple (SQL Statement, Return Code), specialized for Insert
 * statements.
 */
public class SQLInsertRecord extends SQLExecutionRecord {

    /**
     * Whether this SQL statement is trying to satisfy or negate constraints
     */
    private boolean satisfying;

    /**
     * Empty constructor.
     *
     * @deprecated An SQLInsertRecord should only be constructed when the
     * appropriate values are available, therefore an empty constructor should
     * not be needed.
     */
    @Deprecated
    public SQLInsertRecord() {
        super();
    }

    /**
     * Constructor. Defaults 'satisfying' to false.
     *
     * @param statement The SQL Insert statement
     * @param returnCode The return code of executing 'statement'
     */
    public SQLInsertRecord(String statement, Integer returnCode) {
        this(statement, returnCode, false);
    }

    /**
     * Constructor.
     *
     * @param statement The SQL Insert statement
     * @param returnCode The return code of executing 'statement'
     * @param satisfying Whether the Insert is satisfying a constraint
     */
    public SQLInsertRecord(String statement, Integer returnCode, boolean satisfying) {
        super(statement, returnCode);
        this.satisfying = satisfying;
    }

    /**
     * Set this record to be trying to satisfy a constraint.
     *
     * @deprecated 'setSatisfying' follows Java conventions
     */
    @Deprecated
    public void tryToSatisfy() {
        satisfying = true;
    }

    /**
     * Set this record to be trying to negate a constraint.
     *
     * @deprecated 'setSatisfying' follows Java conventions
     */
    @Deprecated
    public void tryToNegate() {
        satisfying = false;
    }

    /**
     * Check if this insert is trying to satisfy.
     *
     * @return satisfying
     * @deprecated 'isSatisfying' follows Java conventions
     */
    @Deprecated
    public boolean isTryingToSatisfy() {
        return satisfying;
    }

    /**
     * Check if this insert is trying to negate.
     *
     * @return not satisfying
     * @deprecated 'isSatisfying' follows Java conventions
     */
    @Deprecated
    public boolean isTryingToNegate() {
        return !satisfying;
    }

    /**
     * Check if this insert is trying to satisfy a constraint.
     *
     * @return satisfying
     */
    public boolean isSatisfying() {
        return satisfying;
    }

    /**
     * Set whether this insert is trying to satisfy a constraint.
     *
     * @param isSatisfying satisfying
     */
    public void setSatisfying(boolean isSatisfying) {
        this.satisfying = isSatisfying;
    }

    @Override
    public String toString() {
        return "SQLInsertRecord{" + "isSatisfying=" + satisfying + '}';
    }
}