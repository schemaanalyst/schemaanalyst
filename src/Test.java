
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;


/**
 *
 * @author Chris J. Wright
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ArtistSimilarity-AICC-avs-HyperSQL-1.testsuite"));
        TestSuite testSuite = (TestSuite) ois.readObject();
        System.out.println(testSuite + ":");
        for (TestCase testCase : testSuite.getTestCases()) {
            System.out.println("\t" + testCase + ": " + testCase.getData());
        }
    }
}
