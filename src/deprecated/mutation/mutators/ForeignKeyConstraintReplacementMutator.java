/*
 */
package deprecated.mutation.mutators;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * Produces mutants where each local column in a foreign key constraint is
 * replaced with each other column of the same type in the same table.
 *
 * @author Chris J. Wright
 */
public class ForeignKeyConstraintReplacementMutator extends Mutator {

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        List<ForeignKeyConstraint> fkeys = table.getForeignKeyConstraints();
        for (int i = 0; i < fkeys.size(); i++) {
            ForeignKeyConstraint fkey = fkeys.get(i);
            for (Column column : fkey.getColumns()) {
                for (Column replacementCol : findColumns(column, table)) {
                    if (!fkey.getColumns().contains(replacementCol)) {
                        mutants.add(mutate(column, replacementCol, i, table));
                    }
                }
            }
        }
    }

    /**
     * Produces a single mutant. Handles the re-mapping to the correct objects
     * in the duplicated schema.
     *
     * @param originalCol The original local column
     * @param replacementCol The original replacement column
     * @param fkeyIndex The index of the foreign key in
     * table.getForeignKeyConstraints()
     * @param table The original table under mutation
     * @return The mutated schema
     */
    private Schema mutate(Column originalCol, Column replacementCol, int fkeyIndex, Table table) {
        // Duplicate the schema and get required vars
        Schema mutSchema = table.getSchema().duplicate();
        Table mutTable = mutSchema.getTable(table.getName());

        // Get the fkey in the duplicate schema
        List<ForeignKeyConstraint> mutFkeys = mutTable.getForeignKeyConstraints();
        ForeignKeyConstraint mutFkey = null;
        for (int i = 0; i < mutFkeys.size(); i++) {
            if (i == fkeyIndex) {
                mutFkey = mutFkeys.get(i);
            }
        }

        // Change columns in the fkey        
        Column mutOriginalCol = mutTable.getColumn(originalCol.getName());
        Column mutReplacementCol = mutTable.getColumn(replacementCol.getName());
        int columnIndexInKey = mutFkey.getColumns().indexOf(mutOriginalCol);
        Column mutReferenceCol = mutFkey.getReferenceColumns().get(columnIndexInKey);
        mutFkey.removeColumnPair(mutOriginalCol);
        mutFkey.addColumnPair(mutReplacementCol, mutReferenceCol);

        return mutSchema;
    }

    /**
     * Finds possible replacements for a given column in a given table, by
     * matching the column datatype.
     *
     * @param colToReplace The column to replace
     * @param localTable The table with candidate replacements
     * @return The possible replacements
     */
    private List<Column> findColumns(Column colToReplace, Table localTable) {
        List<Column> replacementCols = new ArrayList<>();
        for (Column candidateCol : localTable.getColumns()) {
            if (candidateCol != colToReplace) {
                if (candidateCol.getDataType().getClass().equals(colToReplace.getDataType().getClass())) {
                    replacementCols.add(candidateCol);
                }
            }
        }
        return replacementCols;
    }

    public static void main(String[] args) {
        Schema s = new TestSchema();
        List<Schema> mutants = new ForeignKeyConstraintReplacementMutator().produceMutants(s);

        SQLWriter writer = new SQLWriter();
        System.out.println("Original:");
        for (String stmt : writer.writeCreateTableStatements(s)) {
            System.out.println(stmt);
        }
        System.out.println("Mutant count: " + mutants.size());
        for (int i = 0; i < mutants.size(); i++) {
            System.out.println("Mutant " + (i + 1) + ":");
            for (String stmt : writer.writeCreateTableStatements(mutants.get(i))) {
                System.out.println(stmt);
            }
        }
    }

    public static class TestSchema extends Schema {

        public TestSchema() {
            super("TestSchema");

            /*
		  
             CREATE TABLE LONG_NAMED_PEOPLE 
             (
             FIRSTNAME VARCHAR (373),
             LASTNAME VARCHAR (373),
             AGE INT,
             PRIMARY KEY (FIRSTNAME, LASTNAME)
             );

             */

            Table longNamedPeopleTable = createTable("LONG_NAMED_PEOPLE");

            Column firstName = longNamedPeopleTable.addColumn("FIRSTNAME", new VarCharDataType(373));
            Column lastName = longNamedPeopleTable.addColumn("LASTNAME", new VarCharDataType(373));
            Column age = longNamedPeopleTable.addColumn("AGE", new IntDataType());

            longNamedPeopleTable.setPrimaryKeyConstraint(firstName, lastName);

            /*

             CREATE TABLE ORDERS 
             (
             FIRSTNAME VARCHAR (373),
             LASTNAME VARCHAR (373),
             TITLE VARCHAR (80),
             COST NUMERIC(5,2),
             FOREIGN KEY (FIRSTNAME, LASTNAME)
             REFERENCES LONG_NAMED_PEOPLE
             );

             */

            Table ordersTable = createTable("ORDERS");

            Column firstNameOrders = ordersTable.addColumn("FIRSTNAME", new VarCharDataType(373));
            Column lastNameOrders = ordersTable.addColumn("LASTNAME", new VarCharDataType(373));
            Column title = ordersTable.addColumn("TITLE", new VarCharDataType(80));
            Column cost = ordersTable.addColumn("COST", new NumericDataType(5, 2));

            ordersTable.addForeignKeyConstraint(cost, longNamedPeopleTable, age);
            ordersTable.addForeignKeyConstraint(firstNameOrders, lastNameOrders, longNamedPeopleTable, firstName, lastName);
        }
    }
}
