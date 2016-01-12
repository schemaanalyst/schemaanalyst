package org.schemaanalyst.mutation.analysis.executor.util;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.util.SchemaMerger;
import org.schemaanalyst.mutation.equivalence.ChangedConstraintFinder;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 * A collection of common utility functions used in mutation analysis
 * techniques</p>
 *
 * @author Chris J. Wright
 */
public class MutationAnalysisUtils {

    /**
     * Computes the name of the changed table in a schema mutant.
     *
     * @param original The original schema
     * @param mutant The mutant schema
     * @return The name of the table
     */
    public static String computeChangedTable(Schema original, Mutant<Schema> mutant) {
        Schema modifiedSchema = reapplyRemovers(mutant, original);

        // Find the changed table
        Table table = ChangedTableFinder.getDifferentTable(modifiedSchema, mutant.getMutatedArtefact());
        if (table != null) {
            return table.getName();
        } else {
            throw new RuntimeException("Could not find changed table for mutant (" + mutant.getMutatedArtefact().getName() + ": " + mutant.getDescription() + ")");
        }
    }

    public static Constraint computeChangedConstraint(Schema original, Mutant<Schema> mutant) {
        Schema modifiedSchema = reapplyRemovers(mutant, original);
        
        // Find the changed constraint
        Constraint constraint = ChangedConstraintFinder.getDifferentConstraint(modifiedSchema, mutant.getMutatedArtefact());
        if (constraint != null) {
            return constraint;
        } else {
            throw new RuntimeException("Could not find changed constraint for mutant (" + mutant.getMutatedArtefact().getName() + ": " + mutant.getDescription() + ")");
        }
    }
    
    private static Schema reapplyRemovers(Mutant<Schema> mutant, Schema original) throws RuntimeException {
        // Reapply removers if needed
        Schema modifiedSchema;
        if (mutant.getRemoversApplied().size() > 0) {
            Schema duplicateSchema = original.duplicate();
            List<Mutant<Schema>> list = Arrays.asList(new Mutant<>(duplicateSchema, ""));
            for (MutantRemover mutantRemover : mutant.getRemoversApplied()) {
                list = mutantRemover.removeMutants(list);
            }
            if (list.size() != 1) {
                throw new RuntimeException("Applying the MutantRemovers used for a "
                        + "mutant on the original schema did not produce only 1 "
                        + "schema (expected: 1, actual: " + list.size() + ")");
            }
            modifiedSchema = list.get(0).getMutatedArtefact();
        } else {
            modifiedSchema = original.duplicate();
        }
        return modifiedSchema;
    }

    /**
     * Adds the prefix 'mutant_ID' to each constraint in a mutant schema
     *
     * @param mutants The mutants to prefix
     */
    public static void renameMutantConstraints(List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            renameMutantConstraints("mutant_" + i + "_", mutants.get(i));
        }
    }

    /**
     * Adds the given prefix to each constraint in a mutant schema.
     *
     * @param prefix The prefix to use
     * @param mutant The mutant to prefix
     */
    public static void renameMutantConstraints(String prefix, Mutant<Schema> mutant) {
        Schema mutantSchema = mutant.getMutatedArtefact();
        for (Constraint constraint : mutantSchema.getConstraints()) {
            if (constraint.hasIdentifier() && constraint.getIdentifier().get() != null) {
                String name = constraint.getIdentifier().get();
                constraint.setName(prefix + name);
            }
        }
    }

    /**
     * Renames the changed table in each mutant, with the format
     * 'mutant_ID_NAME'.
     *
     * @param original The original schema
     * @param mutants The mutant schemas
     */
    public static void renameChangedTable(Schema original, List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            Mutant<Schema> mutant = mutants.get(i);
            String changedTableName = MutationAnalysisUtils.computeChangedTable(original, mutant);
            renameChangedTable(mutant, i, changedTableName);
        }
    }

    /**
     * Renames the changed table in a single mutant, with the format
     * 'mutant_ID_NAME'.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     * @param changedTableName The name of the changed table
     */
    public static void renameChangedTable(Mutant<Schema> mutant, int id, String changedTableName) {
        Schema mutantSchema = mutant.getMutatedArtefact();
        Table changedTable = mutantSchema.getTable(changedTableName);
        String newName = String.format("mutant_%d_%s", id, changedTableName);
        changedTable.setName(newName);
    }
    
    /**
     * Renames the constraints in the changed table in a single mutant, with the
     *  format 'mutant_ID_CONSTRAINT'.
     * 
     * @param mutant The mutant schema
     * @param id The mutant id
     * @param changedTableName The name of the changed table
     */
    public static void renameChangedTableConstraints(Mutant<Schema> mutant, int id, String changedTableName) {
        Schema mutantSchema = mutant.getMutatedArtefact();
        Table changedTable = mutantSchema.getTable(changedTableName);
        List<Constraint> constraints = mutantSchema.getConstraints(changedTable);
        for (Constraint constraint : constraints) {
            if (constraint.hasIdentifier()) {
                String newName = String.format("mutant_%d_%s", id, constraint.getName());
                constraint.setName(newName);
            }
        }
    }

    /**
     * Merges a schema and mutants together into a meta-mutant.
     *
     * @param original The original schema
     * @param mutants The mutant schemas
     * @return
     */
    public static Schema mergeMutants(Schema original, List<Mutant<Schema>> mutants) {
        Schema merge = original;
        for (Mutant<Schema> mutant : mutants) {
            merge = SchemaMerger.merge(merge, mutant.getMutatedArtefact());
        }
        return merge;
    }

    public static Schema mergeMutantsParallel(Schema original, List<Mutant<Schema>> mutants) {
        try {
            ForkJoinPool pool = new ForkJoinPool();
            LinkedList<Schema> schemas = new LinkedList<>();
            for (Mutant<Schema> mutant : mutants) {
                schemas.add(mutant.getMutatedArtefact());
            }
            ForkJoinTask<Schema> result = pool.submit(new MergeMutantsTask(original, schemas));
            pool.shutdown();
            return result.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class MergeMutantsTask extends RecursiveTask<Schema> {

        Schema schema;
        LinkedList<Schema> schemas;

        public MergeMutantsTask(Schema schema, LinkedList<Schema> schemas) {
            this.schema = schema;
            this.schemas = schemas;
        }

        @Override
        protected Schema compute() {
            if (schemas.isEmpty()) {
                return schema;
            } else if (schemas.size() <= 10) {
                Schema merge = schema;
                for (Schema s : schemas) {
                    merge = SchemaMerger.merge(merge, s);
                }
                return merge;
            } else {
                try {
                    MergeMutantsTask first = new MergeMutantsTask(schema, new LinkedList<>(schemas.subList(0, schemas.size()/2)));
                    MergeMutantsTask second = new MergeMutantsTask(schema, new LinkedList<>(schemas.subList(schemas.size()/2, schemas.size())));
                    first.fork();
                    second.fork();
                    return SchemaMerger.merge(schema, first.get(), second.get());
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        
    }

    /**
     * Merges a schema and mutants together into a meta-mutant, applying all
     * necessary renaming operations beforehand.
     *
     * @param original The original schema
     * @param mutants The mutant schemas
     * @return The meta-mutant schema
     */
    public static Schema renameAndMergeMutants(Schema original, List<Mutant<Schema>> mutants) {
        renameChangedTable(original, mutants);
        renameMutantConstraints(mutants);
        Schema schemata = mergeMutants(original, mutants);
        return schemata;
    }

}
