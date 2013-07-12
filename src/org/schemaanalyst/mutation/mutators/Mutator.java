package org.schemaanalyst.mutation.mutators;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.dbms.postgres.Postgres;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;

public abstract class Mutator {

    public List<Schema> produceMutants(Schema schema) {
        List<Schema> mutants = new ArrayList<Schema>();
        produceMutants(schema, mutants);
        return mutants;
    }

    public void produceMutants(Schema schema, List<Schema> mutants) {
        for (Table table : schema.getTables()) {
            produceMutants(table, mutants);
        }
    }

    public List<Schema> produceMutants(Table table) {
        List<Schema> mutants = new ArrayList<Schema>();
        produceMutants(table, mutants);
        return mutants;
    }

    public abstract void produceMutants(Table table, List<Schema> mutants);

    protected void debug(Schema schema) {
        SQLWriter sqlWriter = (new Postgres()).getSQLWriter();

        List<String> statements = sqlWriter.writeDropTableStatements(schema, true);
        statements.addAll(sqlWriter.writeCreateTableStatements(schema));
        for (String statement : statements) {
            System.out.println(statement + ";");
        }
    }
}
