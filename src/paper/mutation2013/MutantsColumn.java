/*
 */
package paper.mutation2013;

import casestudy.Cloc;
import casestudy.JWhoisServer;
import casestudy.NistDML183;
import casestudy.NistDML182;
import casestudy.RiskIt;
import casestudy.UnixUsage;
import casestudy.runner.Mutate;
import experiment.mutation2013.ConstraintMutatorWithoutFK;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;

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
		new UnixUsage(),
	};
    
    public static void main(String[] args) {
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        for (Schema schema : schemas) {
            System.out.println(schema.getName()+": "+cm.produceMutants(schema).size());
        }
    }
}
