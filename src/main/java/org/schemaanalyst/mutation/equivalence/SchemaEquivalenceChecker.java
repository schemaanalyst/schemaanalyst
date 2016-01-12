/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link Schema} objects to 
 * determine if they are equivalent.
 * </p>
 *
 * <p>
 * This class delegates much of the equivalence testing to a collection of
 * classes that extend the
 * {@link org.schemaanalyst.mutation.equivalence.EquivalenceChecker} class, to
 * compare each sub-component of a schema for equivalence. Altering this
 * behaviour, e.g. for a specific DBMS that has different semantics, can be
 * achieved by providing alternative
 * {@link org.schemaanalyst.mutation.equivalence.EquivalenceChecker} classes.
 * </p>
 * 
 * @author Chris J. Wright
 */
public class SchemaEquivalenceChecker extends EquivalenceChecker<Schema> {

    protected EquivalenceChecker<Table> tableEquivalenceChecker;
    protected EquivalenceChecker<Column> columnEquivalenceChecker;
    protected EquivalenceChecker<PrimaryKeyConstraint> primaryKeyEquivalenceChecker;
    protected EquivalenceChecker<ForeignKeyConstraint> foreignKeyEquivalenceChecker;
    protected EquivalenceChecker<UniqueConstraint> uniqueEquivalenceChecker;
    protected EquivalenceChecker<CheckConstraint> checkEquivalenceChecker;
    protected EquivalenceChecker<NotNullConstraint> notNullEquivalenceChecker;

    /**
     * Constructor with specified equivalence testers for each sub-component of
     * a {@link org.schemaanalyst.sqlrepresentation.Schema} object.
     *
     * @param tableEquivalenceChecker
     * @param columnEquivalenceChecker
     * @param primaryKeyEquivalenceChecker
     * @param foreignKeyEquivalenceChecker
     * @param uniqueEquivalenceChecker
     * @param checkEquivalenceChecker
     * @param notNullEquivalenceChecker
     */
    public SchemaEquivalenceChecker(TableEquivalenceChecker tableEquivalenceChecker, ColumnEquivalenceChecker columnEquivalenceChecker, PrimaryKeyEquivalenceChecker primaryKeyEquivalenceChecker, ForeignKeyEquivalenceChecker foreignKeyEquivalenceChecker, UniqueEquivalenceChecker uniqueEquivalenceChecker, CheckEquivalenceChecker checkEquivalenceChecker, NotNullEquivalenceChecker notNullEquivalenceChecker) {
        this.tableEquivalenceChecker = tableEquivalenceChecker;
        this.columnEquivalenceChecker = columnEquivalenceChecker;
        this.primaryKeyEquivalenceChecker = primaryKeyEquivalenceChecker;
        this.foreignKeyEquivalenceChecker = foreignKeyEquivalenceChecker;
        this.uniqueEquivalenceChecker = uniqueEquivalenceChecker;
        this.checkEquivalenceChecker = checkEquivalenceChecker;
        this.notNullEquivalenceChecker = notNullEquivalenceChecker;
    }

    /**
     * Constructor that uses a default set of equivalence testers for each
     * sub-component of a {@link org.schemaanalyst.sqlrepresentation.Schema}
     * object.
     */
    public SchemaEquivalenceChecker() {
        this.columnEquivalenceChecker = new ColumnEquivalenceChecker();
        this.tableEquivalenceChecker = new TableEquivalenceChecker(columnEquivalenceChecker);
        this.primaryKeyEquivalenceChecker = new PrimaryKeyEquivalenceChecker();
        this.foreignKeyEquivalenceChecker = new ForeignKeyEquivalenceChecker();
        this.uniqueEquivalenceChecker = new UniqueEquivalenceChecker();
        this.checkEquivalenceChecker = new CheckEquivalenceChecker();
        this.notNullEquivalenceChecker = new NotNullEquivalenceChecker();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(Schema a, Schema b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (a.getTables().size() != b.getTables().size()) {
            return false;
        } else if (!tableEquivalenceChecker.areEquivalent(a.getTablesInOrder(), b.getTablesInOrder())) {
            return false;
        } else if (!primaryKeyEquivalenceChecker.areEquivalent(a.getPrimaryKeyConstraints(), b.getPrimaryKeyConstraints())) {
            return false;
        } else if (!foreignKeyEquivalenceChecker.areEquivalent(a.getForeignKeyConstraints(), b.getForeignKeyConstraints())) {
            return false;
        } else if (!uniqueEquivalenceChecker.areEquivalent(a.getUniqueConstraints(), b.getUniqueConstraints())) {
            return false;
        } else if (!checkEquivalenceChecker.areEquivalent(a.getCheckConstraints(), b.getCheckConstraints())) {
            return false;
        } else return notNullEquivalenceChecker.areEquivalent(a.getNotNullConstraints(), b.getNotNullConstraints());
    }
}
