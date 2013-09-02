/*
 */
package org.schemaanalyst.mutation.pipeline;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 *
 * @author Chris J. Wright
 */
public class SchemaPipelineFactory {

    public static MutationPipeline<Schema> instantiate(String name) throws  ClassNotFoundException,
                                                                            InstantiationException,
                                                                            IllegalAccessException {
        if (!getSchemaPipelineChoices().contains(name)) {
            throw new RuntimeException("Pipeline \"" + name + "\" is "
                    + "unrecognised or not supported");
        }

        String className = "org.schemaanalyst.mutation.pipeline." + name + "Pipeline";
        Class<?> pipelineClass = Class.forName(className);
        MutationPipeline<Schema> pipeline = (MutationPipeline<Schema>) pipelineClass.newInstance();
        return pipeline;
    }

    public static List<String> getSchemaPipelineChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("ICST2013");
        return choices;
    }
}
