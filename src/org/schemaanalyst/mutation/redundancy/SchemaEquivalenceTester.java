/*
 */
package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import org.schemaanalyst.sqlrepresentation.*;

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
    
    protected TableEquivalenceTester tableEquivalenceTester;
    protected ColumnEquivalenceTester columnEquivalenceTester;
    protected PrimaryKeyEquivalenceTester primaryKeyEquivalenceTester;
    protected ForeignKeyEquivalenceTester foreignKeyEquivalenceTester;
    protected UniqueEquivalenceTester uniqueEquivalenceTester;
    protected CheckEquivalenceTester checkEquivalenceTester;

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
            return false; // Add easy case - count tables are same
        } else {
            for (Iterator<Table> iter = a.getTablesInOrder().iterator(); iter.hasNext();) {
                Table aTable = iter.next();
                boolean tableFound = false;
                // Make a set of tables in B
                // Remove from set if they are equiv
                // if set is empty -> all tables equiv (regardless of ordering)
            }
            
        }
    }
    
}
