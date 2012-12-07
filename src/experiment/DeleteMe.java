/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import casestudy.Cloc;
import casestudy.JWhoisServer;
import casestudy.NistDML182;
import casestudy.NistDML183;
import casestudy.RiskIt;
import casestudy.UnixUsage;
import experiment.mutation2013.ConstraintMutatorWithoutFK;
import org.schemaanalyst.schema.Schema;

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
        System.out.println(schema.getName()+": "+cm.produceMutants(schema).size());
    }
}
