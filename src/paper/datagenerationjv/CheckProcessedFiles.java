package paper.datagenerationjv;

import org.schemaanalyst.testgeneration.TestSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by phil on 23/08/2014.
 */
public class CheckProcessedFiles {

    public CheckProcessedFiles(String resultsDatabaseFileName, String searchName) {

        ResultsDatabase resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);
        List<String> fileNames = resultsDatabase.getTestSuiteFileNames(searchName);
        boolean allOk = true;
        int no = 1;
        for (String fileName : fileNames) {
            System.out.println("Processing " + no + "/" + fileNames.size());
            File file = new File("test-suites/" + searchName + "/" + fileName + ".testsuite");

            if (file.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    TestSuite testSuite = (TestSuite) in.readObject();
                    in.close();
                }
                catch(IOException | ClassNotFoundException e) {
                    System.out.println("Could not deserialize " + fileName);
                    throw new RuntimeException(e);
                }

            } else {
                System.out.println(fileName + " was executed but test suite does not exist");
                allOk = false;
            }

            no++;
        }
        System.out.println("Num file names was " + fileNames.size());
        if (allOk) {
            System.out.println("All test suites seem correct and present");
        }
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("No results database file name provided and search name");
            System.exit(1);
        }

        new CheckProcessedFiles(args[0], args[1]);
    }
}
