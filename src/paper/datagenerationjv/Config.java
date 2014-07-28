package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import parsedcasestudy.BrowserCookies;

/**
 * Created by phil on 25/07/2014.
 */
public class Config {

    public static Schema[] SCHEMAS = {

            new BrowserCookies()


    };



    public static String[] COVERAGE_CRITERIA = {

            "apc",
            "icc",
            "aicc"

    };

}
