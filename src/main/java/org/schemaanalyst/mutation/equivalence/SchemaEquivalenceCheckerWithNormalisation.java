package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.mutation.normalisation.SchemaNormaliser;
import org.schemaanalyst.sqlrepresentation.Schema;


public class SchemaEquivalenceCheckerWithNormalisation extends SchemaEquivalenceChecker {

    private final SchemaNormaliser normaliser;
    
    public SchemaEquivalenceCheckerWithNormalisation(TableEquivalenceChecker tableEquivalenceChecker, ColumnEquivalenceChecker columnEquivalenceChecker, PrimaryKeyEquivalenceChecker primaryKeyEquivalenceChecker, ForeignKeyEquivalenceChecker foreignKeyEquivalenceChecker, UniqueEquivalenceChecker uniqueEquivalenceChecker, CheckEquivalenceChecker checkEquivalenceChecker, NotNullEquivalenceChecker notNullEquivalenceChecker, SchemaNormaliser schemaNormaliser) {
        super(tableEquivalenceChecker, columnEquivalenceChecker, primaryKeyEquivalenceChecker, foreignKeyEquivalenceChecker, uniqueEquivalenceChecker, checkEquivalenceChecker, notNullEquivalenceChecker);
        this.normaliser = schemaNormaliser;
    }
    
    public SchemaEquivalenceCheckerWithNormalisation(SchemaNormaliser schemaNormaliser) {
        super();
        this.normaliser = schemaNormaliser;
    }

    @Override
    public boolean areEquivalent(Schema a, Schema b) {
        Schema normA = normaliser.normalise(a);
        Schema normB = normaliser.normalise(b);
        return super.areEquivalent(normA, normB);
    }
    
}
