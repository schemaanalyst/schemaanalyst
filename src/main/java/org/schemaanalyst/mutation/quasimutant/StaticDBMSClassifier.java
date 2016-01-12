package org.schemaanalyst.mutation.quasimutant;

import java.util.Iterator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantType;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.DataCapturer;

/**
 *
 * @author Chris J. Wright
 */
public abstract class StaticDBMSClassifier extends StaticDBMSDetector {

    @Override
    public void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it) {
        DataCapturer.capture("classifiedmutants", "impaired", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
        mutant.setMutantType(MutantType.IMPAIRED);
    }

}
