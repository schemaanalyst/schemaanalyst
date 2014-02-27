package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.data.Data;

/**
 * Created by phil on 26/02/2014.
 */
public abstract class Handler {

    public boolean check(Data data) {
        return handle(data, false);
    }

    public boolean fix(Data data) {
        return handle(data, true);
    }

    protected abstract boolean handle(Data data, boolean fix);
}
