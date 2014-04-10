package paper.datagenerationjv;

import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

/**
 * Created by phil on 26/03/2014.
 */
public class GenerateSeeds {

    public static void main(String... args) {

        final int NUM_REQUIRED = 30;

        Random random = new SimpleRandom(System.currentTimeMillis());
        System.out.println();

        for (int i=0; i < NUM_REQUIRED; i++) {
            System.out.print("\t\t" + random.nextLong() + "L");
            if (i < NUM_REQUIRED-1) {
                System.out.println(",");
            }
        }
    }

}
