package org.schemaanalyst.testgeneration.criterion;

import org.schemaanalyst.testgeneration.criterion.integrityconstraint.*;
import parsedcasestudy.BrowserCookies;

/**
 * Created by phil on 23/07/2014.
 */
public class Test {

    public static void main(String[] args) {

        CoverageCriterion criterion = new CondAICC(new BrowserCookies(), new TestRequirementIDGeneratorUsingTable());

        TestRequirements tr = criterion.generateRequirements();

        for (TestRequirement req : tr.getTestRequirements()) {
            System.out.println(req);
        }
        System.out.println("Num requirements: " + tr.size());

    }
}
