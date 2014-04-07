package paper.datagenerationjv;

import java.util.Random;

/**
 * Created by phil on 07/04/2014.
 */
public class GenerateSeeds {

    public static void main(String[] args) {

        Random random = new Random(System.currentTimeMillis());

        for (int i=1; i<=30; i++) {

            System.out.println(random.nextLong() + "L,");

        }
    }
}
