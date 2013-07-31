/*
 */
package paper.mutation2013;

import org.schemaanalyst.mutation.mutators.ConstraintMutatorWithoutFK;

import org.schemaanalyst.sqlrepresentation.Schema;

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
