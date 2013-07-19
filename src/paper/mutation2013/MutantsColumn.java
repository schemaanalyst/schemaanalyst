/*
 */
package paper.mutation2013;

import org.schemaanalyst.mutation.mutators.ConstraintMutatorWithoutFK;

import org.schemaanalyst.sqlrepresentation.Schema;

import originalcasestudy.Cloc;
import originalcasestudy.JWhoisServer;
import originalcasestudy.NistDML182;
import originalcasestudy.NistDML183;
import originalcasestudy.RiskIt;
import originalcasestudy.UnixUsage;

/**
 *
 * @author chris
 */
public class MutantsColumn {

    public static Schema[] schemas = {
        new Cloc(),
        new JWhoisServer(),
        new NistDML182(),
        new NistDML183(),
        new RiskIt(),
        new UnixUsage(),};

    public static void main(String[] args) {
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        for (Schema schema : schemas) {
            System.out.println(schema.getName() + ": " + cm.produceMutants(schema).size());
        }
    }
}
