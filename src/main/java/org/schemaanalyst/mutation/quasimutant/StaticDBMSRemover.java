package org.schemaanalyst.mutation.quasimutant;

import java.util.Iterator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.DataCapturer;

/**
 *
 * @author Chris J. Wright
 */
public abstract class StaticDBMSRemover extends StaticDBMSDetector {

    @Override
    public void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it) {
        DataCapturer.capture("removedmutants", "quasimutant", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
        it.remove();
    }

}
