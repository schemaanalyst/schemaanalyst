package org.schemaanalyst.datageneration.analyst;

import org.schemaanalyst.data.Data;

public abstract class ConstraintAnalyst {

    public abstract boolean isSatisfied(Data state, Data data);
}
