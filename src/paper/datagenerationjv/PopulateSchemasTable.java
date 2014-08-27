package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.List;

import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 15/08/2014.
 */
public class PopulateSchemasTable {

    protected ResultsDatabase resultsDatabase;

    public void populate(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);

        List<String> schemaNames = resultsDatabase.getNames("schemas");
        for (String schemaName : schemaNames) {

            Schema schema = instantiateSchema(schemaName);

            int tables = schema.getTables().size();
            int columns = 0;
            for (Table table : schema.getTables()) {
                columns += table.getColumns().size();
            }

            int pks = schema.getPrimaryKeyConstraints().size();
            int multiClausePks = 0;
            for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
                if (pk.getColumns().size() > 1) {
                    multiClausePks ++;
                }
            }

            int fks = schema.getForeignKeyConstraints().size();
            int multiClauseFks = 0;
            for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints()) {
                if (fk.getColumns().size() > 1) {
                    multiClauseFks ++;
                }
            }

            int uniques = schema.getUniqueConstraints().size();
            int multiClauseUniques = 0;
            for (UniqueConstraint uc : schema.getUniqueConstraints()) {
                if (uc.getColumns().size() > 1) {
                    multiClauseUniques ++;
                }
            }

            int nns = schema.getNotNullConstraints().size();

            int checks = schema.getCheckConstraints().size();
            int multiClauseChecks = 0;
            for (CheckConstraint check : schema.getCheckConstraints()) {
                Expression expression = check.getExpression();
                if (expression instanceof ParenthesisedExpression) {
                    expression = ((ParenthesisedExpression) expression).getSubexpression();
                }

                if (expression instanceof AndExpression ||
                        expression instanceof OrExpression ||
                        expression instanceof BetweenExpression ||
                        expression instanceof InExpression) {
                    multiClauseChecks ++;
                }
            }

            String data = "tables=" + tables
                        + ", columns=" + columns
                        + ", checks=" + checks
                        + ", multi_clause_checks=" + multiClauseChecks
                        + ", fks=" + fks
                        + ", multi_clause_fks=" + multiClauseFks
                        + ", nns=" + nns
                        + ", pks=" + pks
                        + ", multi_clause_pks=" + multiClausePks
                        + ", uniques=" + uniques
                        + ", multi_clause_uniques=" + multiClauseUniques;

            String sql = "UPDATE schemas SET " + data + " WHERE name='" + schemaName + "'";

            System.out.println(sql);

            resultsDatabase.executeInsert(sql);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        new PopulateSchemasTable().populate(args[0]);
    }

}
