package org.schemaanalyst.faultlocalization;

import java.util.ArrayList;

public class OchiaiResultMatrix {
	
	public ArrayList<OchiaiResultMatrixRow> orm;
	
	public OchiaiResultMatrix(ArrayList<OchiaiResultMatrixRow> m){
		this.orm = m;
	}

	public ArrayList<OchiaiResultMatrixRow> getOchiaiResultMatrix() {
		return orm;
	}

	public void setOchiaiResultMatrix(ArrayList<OchiaiResultMatrixRow> orm) {
		this.orm = orm;
	}
	
	
	/*Ideas for this class:
	 *get max score
	 *get (by constraint)
	 *getRow (by index)
	 */
	
}
