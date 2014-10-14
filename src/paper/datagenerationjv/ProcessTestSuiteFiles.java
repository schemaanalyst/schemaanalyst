package paper.datagenerationjv;

import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.io.File;
import java.util.*;

import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 22/08/2014.
 */
public class ProcessTestSuiteFiles {

    public static final String extension = ".testsuite";

    private Map<String, Integer> runs;

    public ProcessTestSuiteFiles(boolean copyFiles) {
        runs = new HashMap<>();

        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        String resultsDir = locationsConfiguration.getResultsDir();

        File[] files = new File(resultsDir).listFiles();
        List<String> resultsFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && !file.getName().equals(".DS_Store") && file.getName().endsWith(".testsuite")) {
                resultsFiles.add(file.getName());
            }
        }

        if (copyFiles) {
            System.out.println("#!/bin/bash");
        }

        for (String fileName : resultsFiles) {
            processFile(fileName, copyFiles);
        }

        if (!copyFiles) {
            List<String> expts = new ArrayList<>(runs.keySet());
            Collections.sort(expts);

            for (String key : expts) {
                int numRuns = runs.get(key);
                if (numRuns < 30) {
                    System.out.println(key + " (" + numRuns + ")");
                }
            }
        }
    }

    public void processFile(String fileName, boolean copyFile) {
        String name = fileName.substring(0, fileName.length() - extension.length());
        String[] parts = name.split("-");

        String schemaName = parts[0];
        String coverageCriterionName = parts[1];
        String dataGeneratorName = parts[2];
        String dbmsName = parts[3];

        String id = schemaName + "-" + coverageCriterionName + "-" + dataGeneratorName + "-" + dbmsName;
        Integer numRuns = runs.get(id);
        if (numRuns == null) {
            numRuns = 1;
        } else {
            numRuns ++;
        }
        runs.put(id, numRuns);

        if (copyFile) {
            if (exptNotNeeded(schemaName, coverageCriterionName)) {
                System.out.println("cp results/" + fileName + " test-suites/__not-needed/" + fileName);
            } else {
                if (dataGeneratorName.equals("random")) {
                    System.out.println("cp results/" + fileName + " test-suites/random/" + fileName);
                } else if (dataGeneratorName.equals("randomDefaults")) {
                    System.out.println("cp results/" + fileName + " test-suites/randomDefaults/" + fileName);
                } else if (dataGeneratorName.equals("avs")) {
                    System.out.println("cp results/" + fileName + " test-suites/avs/" + fileName);
                } else if (dataGeneratorName.equals("avsDefaults")) {
                    System.out.println("cp results/" + fileName + " test-suites/avsDefaults/" + fileName);
                }
            }
        }
    }

    public static boolean exptNotNeeded(String schemaName, String coverageCriterionName) {
        if (coverageCriterionName.equals("ClauseAICC")) {
            Schema schema = instantiateSchema(schemaName);

            for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
                if (pk.getColumns().size() > 1) {
                    return false;
                }
            }

            for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints()) {
                if (fk.getColumns().size() > 1) {
                    return false;
                }
            }

            for (UniqueConstraint uc : schema.getUniqueConstraints()) {
                if (uc.getColumns().size() > 1) {
                    return false;
                }
            }

            for (CheckConstraint check : schema.getCheckConstraints()) {
                Expression expression = check.getExpression();
                if (expression instanceof ParenthesisedExpression) {
                    expression = ((ParenthesisedExpression) expression).getSubexpression();
                }
                if (expression instanceof AndExpression ||
                        expression instanceof OrExpression ||
                        expression instanceof BetweenExpression ||
                        expression instanceof InExpression) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new ProcessTestSuiteFiles(true);
    }
}
