package paper.ineffectivemutants;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantType;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 18/12/2015.
 */
public class CheckManualAnalysis {

    public static void main(String[] args) {
        File dir = new File(GenerateTestSuites.BASE_DIR_NAME + "complete/");
        List<File> files = new ArrayList<>(Arrays.asList(dir.listFiles()));

        for (File file : files) {
            String name = file.getName();
            if (!name.endsWith(".java")) {
                continue;
            }

            name = name.substring(0, name.length() - ".java".length());
            List<String> parts = Arrays.asList(name.split("_"));

            String classification = parts.get(parts.size() - 1);
            String mutantIdentifier = parts.get(parts.size() - 2);
            int mutant1ID = -1;
            int mutant2ID = -1;
            if (mutantIdentifier.contains("w")) {
                List<String> mutantIDParts = Arrays.asList(mutantIdentifier.split("w"));
                mutant1ID = Integer.parseInt(mutantIDParts.get(0));
                mutant2ID = Integer.parseInt(mutantIDParts.get(1));
            } else {
                mutant1ID = Integer.parseInt(mutantIdentifier);
            }


            String dbms = parts.get(parts.size() - 3);

            String schema = parts.get(0);
            if (parts.size() > 4) {
                System.out.println(parts.size());
                for (int i=1; i <= parts.size()-4; i++) {
                    System.out.println(i);
                    schema += "_" + parts.get(i);

                    System.out.println("schema name is: " + schema);
                }
            }

            List<Mutant<Schema>> mutants = generateMutants(instantiateSchema(schema), dbms);
            int mutantIndex = mutant1ID - 1 ;
            Mutant<Schema> mutant = mutants.get(mutantIndex);

            MutantType ourClassification = MutantType.NORMAL;
            if (classification.equals("EQUIVALENT")) {
                ourClassification = MutantType.EQUIVALENT;
            }
            if (classification.equals("IMPAIRED")) {
                ourClassification = MutantType.IMPAIRED;
            }
            if (classification.equals("REDUNDANT")) {
                ourClassification = MutantType.DUPLICATE;
            }

            String output = schema + " " + dbms + " " + mutantIdentifier + " IS ";
            if (ourClassification != mutant.getMutantType()) {
                boolean isOk = false;
                if (classification.equals("REDUNDANT")) {
                    int mutant2Index = mutant2ID - 1 ;
                    Mutant<Schema> mutant2 = mutants.get(mutant2Index);
                    if (ourClassification == mutant2.getMutantType()) {
                        isOk = true;
                        mutant = mutant2;
                    }
                }
                if (!isOk) {
                    output += "**** NOT ****";
                }
            }
            output += " " + classification;
            System.out.println(output);
            if (ourClassification != mutant.getMutantType()) {
                System.out.println("(It's " + mutant.getMutantType() + ")");
                System.exit(1);
            }
            System.out.println(mutant);
        }
    }

    private static Schema instantiateSchema(String name) {
        // Get the required schema class
        try {
            return (Schema) Class.forName("parsedcasestudy." + name).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
    private static List<Mutant<Schema>> generateMutants(Schema schema, String dbms) {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.instantiate("AllOperatorsNormalisedWithClassifiers", schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }
}
