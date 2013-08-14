/*
 */
package paper.mutation2013;

import org.schemaanalyst.sqlrepresentation.Schema;

import deprecated.mutation.mutators.ConstraintMutatorWithoutFK;

/**
 *
 * @author chris
 */
public class MutantsColumn {

    public static Schema[] schemas = {
        // schemas removed as original case studies deleted!   
    };

    public static void main(String[] args) {
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        for (Schema schema : schemas) {
            System.out.println(schema.getName() + ": " + cm.produceMutants(schema).size());
        }
    }
}
