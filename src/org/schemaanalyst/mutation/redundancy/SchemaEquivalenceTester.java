/*
 */
package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * Compares two {@link org.schemaanalyst.sqlrepresentation.Schema} objects and
 * determines whether they are equivalent in terms of semantics, such that they
 * may not be syntactically identical.
 *
 * This class delegates much of the equivalence testing to a collection of
 * classes that extend the
 * {@link org.schemaanalyst.mutation.redundancy.EquivalenceTester} class, to
 * compare each sub-component of a schema for equivalence. Altering this
 * behaviour, e.g. for a specific DBMS that has different semantics, can be
 * achieved by providing alternative
 * {@link org.schemaanalyst.mutation.redundancy.EquivalenceTester} classes.
 *
 * @author Chris J. Wright
 */
public class SchemaEquivalenceTester extends EquivalenceTester<Schema> {

    protected EquivalenceTester<Table> tableEquivalenceTester;
    protected EquivalenceTester<Column> columnEquivalenceTester;
    protected EquivalenceTester<PrimaryKeyConstraint> primaryKeyEquivalenceTester;
    protected EquivalenceTester<ForeignKeyConstraint> foreignKeyEquivalenceTester;
    protected EquivalenceTester<UniqueConstraint> uniqueEquivalenceTester;
    protected EquivalenceTester<CheckConstraint> checkEquivalenceTester;

    /**
     * Constructor with specified equivalence testers for each sub-component of
     * a {@link org.schemaanalyst.sqlrepresentation.Schema} object.
     *
     * @param tableEquivalenceTester
     * @param columnEquivalenceTester
     * @param primaryKeyEquivalenceTester
     * @param foreignKeyEquivalenceTester
     * @param uniqueEquivalenceTester
     * @param checkEquivalenceTester
     */
    public SchemaEquivalenceTester(TableEquivalenceTester tableEquivalenceTester, ColumnEquivalenceTester columnEquivalenceTester, PrimaryKeyEquivalenceTester primaryKeyEquivalenceTester, ForeignKeyEquivalenceTester foreignKeyEquivalenceTester, UniqueEquivalenceTester uniqueEquivalenceTester, CheckEquivalenceTester checkEquivalenceTester) {
        this.tableEquivalenceTester = tableEquivalenceTester;
        this.columnEquivalenceTester = columnEquivalenceTester;
        this.primaryKeyEquivalenceTester = primaryKeyEquivalenceTester;
        this.foreignKeyEquivalenceTester = foreignKeyEquivalenceTester;
        this.uniqueEquivalenceTester = uniqueEquivalenceTester;
        this.checkEquivalenceTester = checkEquivalenceTester;
    }

    /**
     * Constructor that uses a default set of equivalence testers for each
     * sub-component of a {@link org.schemaanalyst.sqlrepresentation.Schema}
     * object.
     */
    public SchemaEquivalenceTester() {
        this.columnEquivalenceTester = new ColumnEquivalenceTester();
        this.tableEquivalenceTester = new TableEquivalenceTester(columnEquivalenceTester);
        this.primaryKeyEquivalenceTester = new PrimaryKeyEquivalenceTester();
        this.foreignKeyEquivalenceTester = new ForeignKeyEquivalenceTester();
        this.uniqueEquivalenceTester = new UniqueEquivalenceTester();
        this.checkEquivalenceTester = new CheckEquivalenceTester();
    }

    @Override
    public boolean areEquivalent(Schema a, Schema b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (a.getTables().size() != b.getTables().size()) {
            return false;
        } else if (!tableEquivalenceTester.areEquivalent(a.getTablesInOrder(), b.getTablesInOrder())) {
            return false;
        } else if (!primaryKeyEquivalenceTester.areEquivalent(a.getPrimaryKeyConstraints(), b.getPrimaryKeyConstraints())) {
            return false;
        } else if (!foreignKeyEquivalenceTester.areEquivalent(a.getForeignKeyConstraints(), b.getForeignKeyConstraints())) {
            return false;
        } else if (!uniqueEquivalenceTester.areEquivalent(a.getUniqueConstraints(), b.getUniqueConstraints())) {
            return false;
        } else if (!checkEquivalenceTester.areEquivalent(a.getCheckConstraints(), b.getCheckConstraints())) {
            return false;
        } else {
            return true;
        }
    }
}
