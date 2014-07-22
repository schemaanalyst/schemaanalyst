package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import parsedcasestudy.BrowserCookies;

/**
 * Created by phil on 18/07/2014.
 */
public class Test {

    public static void main(String[] args) {
        BrowserCookies schema = new BrowserCookies();
        new CondAICC(schema).printRequirements();

    }
}
