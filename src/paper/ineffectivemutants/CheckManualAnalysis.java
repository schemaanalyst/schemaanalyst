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
            int mutantIdenitifier = Integer.parseInt(parts.get(parts.size() - 2));
            String dbms = parts.get(parts.size() - 3);

            String schema = parts.get(0);
            if (parts.size() > 4) {
                for (int i=1; i < parts.size()-4; i++) {
                    schema += "_" + parts.get(i);
                }
            }

            List<Mutant<Schema>> mutants = generateMutants(instantiateSchema(schema), dbms);
            int mutantIndex = mutantIdenitifier-1;
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

            if (ourClassification != mutant.getMutantType()) {
                String output = schema + " " + dbms + " " + mutantIdenitifier + " IS ";
                output += "**** NOT ****";
                output += " " + classification;
                System.out.println(output);
                System.out.println("(It's " + mutant.getMutantType()+")");
                System.out.println(mutant);
                System.exit(1);
            }
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
            pipeline = MutationPipelineFactory.instantiate(GenerateTestSuites.MUTATION_PIPELINE, schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }
}
