/*
 */
package org.schemaanalyst.mutation.pipeline;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * <p>
 * A factory for creating instances of {@link MutationPipeline} classes.
 * </p>
 *
 * @author Chris J. Wright
 */
public class MutationPipelineFactory {

    /**
     * Instantiate a pipeline class with the given name, with a constructor 
     * accepting the artefact instance to mutate.
     * 
     * @param <A> The type of artefact to be mutated
     * @param name The fully qualified pipeline class name
     * @param artifact The artefact to be mutated
     * @return The instantiated pipeline
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException 
     */
    public static <A> MutationPipeline<A> instantiate(String name, A artifact) throws ClassNotFoundException,
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
        Constructor<MutationPipeline<A>> constructor = ConstructorUtils.getMatchingAccessibleConstructor(pipelineClass, artifact.getClass());
        return constructor.newInstance(artifact);
    }

    /**
     * Get a list of the permitted pipeline choices.
     * 
     * @return The list of permitted pipeline choices
     */
    public static List<String> getPipelineChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("ICST2013");
        choices.add("ICST2013NoRemovers");
        choices.add("Mutation2013");
        return choices;
    }
}
