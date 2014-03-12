package org.schemaanalyst.mutation.analysis.executor.util;

import java.util.Arrays;
import java.util.List;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.util.SchemaMerger;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

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
        // Reapply removers if needed
        Schema modifiedSchema;
        if (mutant.getRemoversApplied().size() > 1) {
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

        // Find the changed table
        Table table = ChangedTableFinder.getDifferentTable(modifiedSchema, mutant.getMutatedArtefact());
        if (table != null) {
            return table.getName();
        } else {
            throw new RuntimeException("Could not find changed table for mutant (" + mutant.getMutatedArtefact().getName() + ": " + mutant.getDescription() + ")");
        }
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
            Schema mutantSchema = mutant.getMutatedArtefact();
            String changedTableName = MutationAnalysisUtils.computeChangedTable(original, mutant);
            Table changedTable = mutantSchema.getTable(changedTableName);
            String newName = String.format("mutant_%d_%s", i, changedTableName);
            changedTable.setName(newName);
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
        return mergeMutants(original, mutants);
    }

}
