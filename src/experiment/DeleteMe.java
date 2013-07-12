/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import experiment.mutation2013.ConstraintMutatorWithoutFK;

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
public class DeleteMe {

    public static void main(String[] args) throws Exception {
        mutate(new Cloc());
        mutate(new JWhoisServer());
        mutate(new RiskIt());
        mutate(new UnixUsage());
        mutate(new NistDML183());
        mutate(new NistDML182());
    }

    public static void mutate(Schema schema) {
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        System.out.println(schema.getName() + ": " + cm.produceMutants(schema).size());
    }
}
