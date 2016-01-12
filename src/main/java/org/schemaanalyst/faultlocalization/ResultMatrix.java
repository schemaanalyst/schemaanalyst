package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.ArrayList;

public class ResultMatrix {
	
	ArrayList<ResultMatrixRow> rmr;
	int indexOfMaxScore;
	Schema schema;
	
	public ResultMatrix(){
		
	}
	
	public ResultMatrix(Schema s, ArrayList<ResultMatrixRow> row){
		schema = s;
		rmr = row;
	}	
	
	public ArrayList<ResultMatrixRow> getRows() {
		return rmr;
	}


	public void setRmr(ArrayList<ResultMatrixRow> rmr) {
		this.rmr = rmr;
	}


	public int getIndexOfMaxScore() {
		return indexOfMaxScore;
	}


	public void setIndexOfMaxScore(int indexOfMaxScore) {
		this.indexOfMaxScore = indexOfMaxScore;
	}


	public void addRow(ResultMatrixRow row){
		rmr.add(row);
	}
	
	public ResultMatrixRow getRow(int index){
		return rmr.get(index);
	}
	
	public int size(){
		return rmr.size();
	}
	
	public ArrayList<ResultMatrixRow> toArrayList(){
		return rmr;
	}
	

}
