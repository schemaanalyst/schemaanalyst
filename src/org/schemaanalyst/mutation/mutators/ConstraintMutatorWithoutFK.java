package org.schemaanalyst.mutation.mutators;

import experiment.mutation2013.PostgresPrimaryKeyConstraintMutator;
import org.schemaanalyst.mutation.mutators.*;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class ConstraintMutatorWithoutFK extends Mutator {

    private CheckConstraintMutator checkMutator;
    private NotNullConstraintMutator notNullMutator;
    private PostgresPrimaryKeyConstraintMutator primaryKeyMutator;
    private UniqueConstraintMutator uniqueMutator;

    public ConstraintMutatorWithoutFK() {
        setup();
    }

    protected void setup() {
        checkMutator = new CheckConstraintMutator();
        notNullMutator = new NotNullConstraintMutator();
        primaryKeyMutator = new PostgresPrimaryKeyConstraintMutator();
        uniqueMutator = new UniqueConstraintMutator();
    }

    public void produceMutants(Table table, List<Schema> mutants) {
        checkMutator.produceMutants(table, mutants);
        notNullMutator.produceMutants(table, mutants);
        // NOTE: Postgres returns errors for all of these
        // even though SQLite seems to work correctly
        // this is due to the fact that you cannot have
        // a primary key in Postgres with two attributes
        // and then have a foreign key in another table
        // that only references part of the foreign key

        // NOTE: Now allowing these because of the fact that
        // we track the still-born mutants and then report
        // this information for later analysis.  Also, it
        // is important to note that HSQLDB will also call
        // these PK mutants as still-born.

        // NOTE: It seems that none of the other operators
        // produce any still-born mutants, based on some
        // limited testing with the bank account example.
        primaryKeyMutator.produceMutants(table, mutants);
        uniqueMutator.produceMutants(table, mutants);
    }
}
