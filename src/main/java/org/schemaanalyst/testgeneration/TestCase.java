package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase implements Serializable {

    private static final long serialVersionUID = 7252118737699128109L;

    private TestRequirement testReqiurement;
    private Data data, state;
    private List<Boolean> dbmsResults;

    public TestCase(TestRequirement testRequirement, Data data, Data state) {
        this.testReqiurement = testRequirement;
        this.data = data;
        this.state = state;
        dbmsResults = new ArrayList<>();
    }

    public Data getData() {
        return data;
    }
    
	public void setData(Data newData) {
		this.data = newData;
	}
	
	public List<Cell> getAllValues() {
		List<Cell> allCells = new ArrayList<Cell>();
		allCells.addAll(this.getState().getCells());
		allCells.addAll(this.data.getCells());
		
		return allCells;
	}

    public Data getState() {
        return state;
    }

    public TestRequirement getTestRequirement() {
        return testReqiurement;
    }

    public void setDBMSResults(List<Boolean> dbmsResults) {
        this.dbmsResults = new ArrayList<>(dbmsResults);
    }

    public List<Boolean> getDBMSResults() {
        return new ArrayList<>(dbmsResults);
    }

    public Boolean getLastDBMSResult() {
        return dbmsResults.get(dbmsResults.size()-1);
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dbmsResults == null) ? 0 : dbmsResults.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testReqiurement == null) ? 0 : testReqiurement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dbmsResults == null) {
			if (other.dbmsResults != null)
				return false;
		} else if (!dbmsResults.equals(other.dbmsResults))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (testReqiurement == null) {
			if (other.testReqiurement != null)
				return false;
		} else if (!testReqiurement.equals(other.testReqiurement))
			return false;
		return true;
	}
}
