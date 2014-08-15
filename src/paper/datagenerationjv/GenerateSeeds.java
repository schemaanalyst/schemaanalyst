package paper.datagenerationjv;

import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

/**
 * Created by phil on 15/08/2014.
 */
public class GenerateSeeds {

    public static void main(String[] args) {
        Random random = new SimpleRandom(System.currentTimeMillis());
        for (int i=1; i <= 30; i++) {
            System.out.println(i + ", " + random.nextLong());
        }
    }

}
