package paper.issre2014;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.paukov.combinatorics.CombinatoricsVector;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 *
 * @author Chris J. Wright
 */
public class TestCombinations {

    public static void main(String[] args) throws Exception {
        List<String> list = Arrays.asList(new String[]{
            "CCNullifier",
            "CCInExpressionRHSListExpressionElementR",
            "CCRelationalExpressionOperatorE",
            "FKCColumnPairA",
            "FKCColumnPairR",
            "FKCColumnPairE",
            "PKCColumnA",
            "PKCColumnR",
            "PKCColumnE",
            "NNCA",
            "NNCR",
            "UCColumnA",
            "UCColumnR",
            "UCColumnE"
        });
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("operatorgroups",false))) {
            Generator<String> generator = Factory.createSimpleCombinationGenerator(new CombinatoricsVector<>(list), 3);
            for (ICombinatoricsVector<String> vector : generator) {
                writer.append(StringUtils.join(vector,","));
                writer.newLine();
            }
        }
//        for (int i = 0; i <= list.size(); i++) {
//            System.out.println(i+" combinations: " + Factory.createSimpleCombinationGenerator(new CombinatoricsVector<>(list), i).getNumberOfGeneratedObjects());
//        }
    }

}
