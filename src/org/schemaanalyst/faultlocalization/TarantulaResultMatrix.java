package org.schemaanalyst.faultlocalization;

import java.util.ArrayList;

public class TarantulaResultMatrix {
	
	public ArrayList<TarantulaResultMatrix> trm;
	
	public TarantulaResultMatrix(ArrayList<TarantulaResultMatrix> m){
		this.trm = m;
	}

	public ArrayList<TarantulaResultMatrix> getTarantulaResultMatrix() {
		return trm;
	}

	public void setTarantulaResultMatrix(ArrayList<TarantulaResultMatrix> m) {
		this.trm = m;
	}
	
	
	/*Ideas for this class:
	 *get max score
	 *get (by constraint)
	 *get (by index)
	 */
	
}
