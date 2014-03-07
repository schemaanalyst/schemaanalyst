
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The result of running a mutation analysis {@link Technique}</p>
 * 
 * @author Chris J. Wright
 */
public class AnalysisResult {
    private final List<Mutant<Schema>> live;
    private final List<Mutant<Schema>> killed;

    public AnalysisResult() {
        live = new ArrayList<>();
        killed = new ArrayList<>();
    }
    
    public void addKilled(Mutant<Schema> mutant) {
        killed.add(mutant);
    }
    
    public void addLive(Mutant<Schema> mutant) {
        live.add(mutant);
    }

    public List<Mutant<Schema>> getKilled() {
        return killed;
    }

    public List<Mutant<Schema>> getLive() {
        return live;
    }
}
