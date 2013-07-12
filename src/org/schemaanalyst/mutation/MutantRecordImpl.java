package org.schemaanalyst.mutation;

public interface MutantRecordImpl {

    public void killedMutant();

    public void sparedMutant();

    public boolean didKillMutant();
}