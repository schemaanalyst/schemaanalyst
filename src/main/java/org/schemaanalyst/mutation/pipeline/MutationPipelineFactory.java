/*
 */
package org.schemaanalyst.mutation.pipeline;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
     * @param dbms The DBMS being used
     * @return The instantiated pipeline
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static <A> MutationPipeline<A> instantiate(String name, A artifact, String dbms) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        if (!getPipelineChoices().contains(name) && !name.startsWith("Programmatic")) {
            throw new RuntimeException("Pipeline \"" + name + "\" is "
                    + "unrecognised or not supported");
        }
        MutationPipeline<A> newInstance;
        Class<MutationPipeline<A>> pipelineClass;
        if (name.startsWith("Programmatic")) {
            String[] splitName = name.split(":");
            String className = "org.schemaanalyst.mutation.pipeline." + splitName[0] + "Pipeline";
            String producers = splitName[1];
            pipelineClass = (Class<MutationPipeline<A>>) Class.forName(className);
            Constructor<MutationPipeline<A>> constructor = ConstructorUtils.getMatchingAccessibleConstructor(pipelineClass, artifact.getClass(), String.class);
            newInstance = constructor.newInstance(artifact, producers);
        } else {
            String className = "org.schemaanalyst.mutation.pipeline." + name + "Pipeline";
            pipelineClass = (Class<MutationPipeline<A>>) Class.forName(className);
            Constructor<MutationPipeline<A>> constructor = ConstructorUtils.getMatchingAccessibleConstructor(pipelineClass, artifact.getClass());
            newInstance = constructor.newInstance(artifact);
        }
        Method dbmsSpecific = MethodUtils.getAccessibleMethod(pipelineClass, "addDBMSSpecificRemovers", String.class);
        if (dbmsSpecific != null) {
            dbmsSpecific.invoke(newInstance, dbms);
        }
        return newInstance;
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
        choices.add("ICST2013NewSchema");
        choices.add("ISSREPostgresDBMSRemovers");
        choices.add("ISSRERetainEquivalentMutants");
        choices.add("ISSRERetainRedundantMutants");
        choices.add("AllOperatorsWithRemovers");
        choices.add("AllOperatorsNormalisedWithRemovers");
        choices.add("AllOperatorsNoFKANormalisedWithRemovers");
        choices.add("AllOperatorsNoFKANormalisedWithRemoversDBMSRemovers");
        choices.add("AllOperatorsNoFKANormalisedWithRemoversTransactedDBMSRemovers");
        choices.add("AllOperatorsNormalisedWithClassifiers");
        choices.add("AllOperatorsNoFKANormalisedWithClassifiers");
        choices.add("AllOperatorsWithClassifiers");
        choices.add("AllOperatorsWithQMRemovers");
        choices.add("AllOperatorsWithImpaired");
        choices.add("AllOperatorsNoFKAWithImpaired");
        choices.add("Mutation2013");
        choices.add("Programmatic");
        choices.add("ProgrammaticDBMSRemovers");
        choices.add("ProgrammaticDBMSRemovers10");
        choices.add("ProgrammaticDBMSRemovers25");
        choices.add("ProgrammaticDBMSRemovers50");
        choices.add("ProgrammaticNoRemovers");
        choices.add("QSICDBMSRemovers");
        choices.add("QSICDBMSTransactedRemovers");
        return choices;
    }
}
