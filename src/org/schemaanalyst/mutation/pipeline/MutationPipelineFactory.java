/*
 */
package org.schemaanalyst.mutation.pipeline;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class MutationPipelineFactory {

    public static <A> MutationPipeline<A> instantiate(String name, A artifact) throws  ClassNotFoundException,
                                                                            InstantiationException,
                                                                            IllegalAccessException,
                                                                            NoSuchMethodException,
                                                                            InvocationTargetException {
        if (!getPipelineChoices().contains(name)) {
            throw new RuntimeException("Pipeline \"" + name + "\" is "
                    + "unrecognised or not supported");
        }

        String className = "org.schemaanalyst.mutation.pipeline." + name + "Pipeline";
        Class<MutationPipeline<A>> pipelineClass = (Class<MutationPipeline<A>>) Class.forName(className);
        Constructor<MutationPipeline<A>> constructor = pipelineClass.getConstructor(artifact.getClass());
        return constructor.newInstance(artifact);
    }

    public static List<String> getPipelineChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("ICST2013");
        return choices;
    }
}
