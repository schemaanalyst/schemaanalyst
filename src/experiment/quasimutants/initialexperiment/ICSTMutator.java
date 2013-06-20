/*
 */
package experiment.quasimutants.initialexperiment;

import java.util.List;
import org.schemaanalyst.mutation.mutators.CheckConstraintMutator;
import org.schemaanalyst.mutation.mutators.ForeignKeyConstraintMutator;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.mutation.mutators.NotNullConstraintMutator;
import org.schemaanalyst.mutation.mutators.PrimaryKeyConstraintMutator;
import org.schemaanalyst.mutation.mutators.UniqueConstraintMutator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 *
 * @author chris
 */
public class ICSTMutator extends Mutator {

    private CheckConstraintMutator checkMutator;
    private ForeignKeyConstraintMutator foreignKeyMutator;
    private NotNullConstraintMutator notNullMutator;
    private PrimaryKeyConstraintMutator primaryKeyMutator;
    private UniqueConstraintMutator uniqueMutator;

    public ICSTMutator() {
        setup();
    }

    protected final void setup() {
        checkMutator = new CheckConstraintMutator();
        foreignKeyMutator = new ForeignKeyConstraintMutator();
        notNullMutator = new NotNullConstraintMutator();
        primaryKeyMutator = new PrimaryKeyConstraintMutator();
        uniqueMutator = new UniqueConstraintMutator();
    }

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        checkMutator.produceMutants(table, mutants);
        foreignKeyMutator.produceMutants(table, mutants);
        notNullMutator.produceMutants(table, mutants);
        primaryKeyMutator.produceMutants(table, mutants);
        uniqueMutator.produceMutants(table, mutants);
    }
}
