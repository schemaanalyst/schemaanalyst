package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessMatrix {
	public static Schema schema;
	public static ResultMatrix OchiaiRanked;
	public static ResultMatrix TarantulaRanked;
	public static ResultMatrix JaccardRanked;

	public static void Process(Schema s, ResultMatrix matrix) {
		schema = s;
		setMatrixScores(matrix);
		setRanks(matrix);
		computeScores(s);
	}

	public static void setMatrixScores(ResultMatrix matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			Calculator.calculateOchiai(matrix.getRow(i));
			Calculator.calculateTarantula(matrix.getRow(i));
			Calculator.calculateJaccard(matrix.getRow(i));
		}
	}

	public static void setRanks(ResultMatrix matrix) {
		ArrayList<ResultMatrixRow> matrix1 = new ArrayList<>();
		ArrayList<ResultMatrixRow> matrix2 = new ArrayList<>();
		ArrayList<ResultMatrixRow> matrix3 = new ArrayList<>();
		for (int i = 0; i < matrix.size(); i++) {
			matrix1.add(matrix.getRow(i));
		}
		for (int i = 0; i < matrix.size(); i++) {
			matrix2.add(matrix.getRow(i));
		}
		for (int i = 0; i < matrix.size(); i++) {
			matrix3.add(matrix.getRow(i));
		}

		Collections.sort(matrix1, new OchiaiComparator());
		OchiaiRanked = new ResultMatrix(schema, matrix1);

		Collections.sort(matrix2, new TarantulaComparator());
		TarantulaRanked = new ResultMatrix(schema, matrix2);

		Collections.sort(matrix3, new JaccardComparator());
		JaccardRanked = new ResultMatrix(schema, matrix3);

	}
	public static void computeScores(Schema s){
		ArrayList<ResultMatrixRow> oRows = OchiaiRanked.getRows();
		rankConstraints(s);
		int oConstraints = OchiaiRanked.size();
		for(int i= 0; i < oRows.size(); i++){
			if(oRows.get(i).isFault()){
				double rank = OchiaiRanked.getRow(i).getOchiaiRank();
				double score = (oConstraints - rank)/oConstraints;
				oRows.get(i).setScoreOchiai(score);
			}else{
				oRows.get(i).setScoreOchiai(0.0);
			}
		}
		
		ArrayList<ResultMatrixRow> tRows = TarantulaRanked.getRows();
		int tConstraints = TarantulaRanked.size();
		for(int i= 0; i < tRows.size(); i++){
			if(tRows.get(i).isFault()){
				double rank = TarantulaRanked.getRow(i).getTarantulaRank();
				double score = (tConstraints - rank)/tConstraints;
				tRows.get(i).setScoreTarantula(score);
			}else{
				tRows.get(i).setScoreTarantula(0.0);
			}
		}
		
		ArrayList<ResultMatrixRow> jRows = JaccardRanked.getRows();
		int jConstraints = JaccardRanked.size();
		for(int i= 0; i < jRows.size(); i++){
			if(jRows.get(i).isFault()){
				double rank = JaccardRanked.getRow(i).getJaccardRank();
				double score = (jConstraints - rank)/jConstraints;
				jRows.get(i).setScoreJaccard(score);
			}else{
				jRows.get(i).setScoreJaccard(0.0);
			}
		}
	}
	
	public static void rankConstraints(Schema s){
		
		List<Constraint> constraints = new ArrayList<>();
		for(int i =0; i < OchiaiRanked.size(); i++){
			if(constraints.contains(OchiaiRanked.getRow(i).getDBConstraint())){
				
			}else{
				constraints.add(OchiaiRanked.getRow(i).getDBConstraint());
			}
		}
		int ochiaiRank = 1;
		for(int i = 0; i < OchiaiRanked.size()-1; i++){
			Constraint current = OchiaiRanked.getRow(i).getDBConstraint();
			OchiaiRanked.getRow(i).setOchiaiRank(ochiaiRank);
			Constraint next = OchiaiRanked.getRow(i+1).getDBConstraint();
			if(!current.equals(next) && constraints.contains(next)){
				constraints.remove(current);
				ochiaiRank++;
				OchiaiRanked.getRow(i+1).setOchiaiRank(ochiaiRank);
			}else{
				OchiaiRanked.getRow(i).setOchiaiRank(ochiaiRank);
			}
		}

		int tarantulaRank = 1;
		for(int i = 0; i < TarantulaRanked.size()-1; i++){
			Constraint current = TarantulaRanked.getRow(i).getDBConstraint();
			TarantulaRanked.getRow(i).setTarantulaRank(tarantulaRank);
			Constraint next = TarantulaRanked.getRow(i+1).getDBConstraint();
			if(!current.equals(next) && constraints.contains(next)){
				constraints.remove(current);
				tarantulaRank++;
				TarantulaRanked.getRow(i+1).setTarantulaRank(tarantulaRank);
			}else{
				TarantulaRanked.getRow(i).setTarantulaRank(tarantulaRank);
			}
		}
		
		int jaccardRank = 1;
		for(int i = 0; i < JaccardRanked.size()-1; i++){
			Constraint current = JaccardRanked.getRow(i).getDBConstraint();
			JaccardRanked.getRow(i).setJaccardRank(jaccardRank);
			Constraint next = JaccardRanked.getRow(i+1).getDBConstraint();
			if(!current.equals(next) && constraints.contains(next)){
				constraints.remove(current);
				jaccardRank++;
				JaccardRanked.getRow(i+1).setJaccardRank(jaccardRank);
			}else{
				JaccardRanked.getRow(i).setJaccardRank(jaccardRank);
			}
		}
	}

}

class OchiaiComparator implements Comparator<ResultMatrixRow> {

	@Override
	public int compare(ResultMatrixRow o1, ResultMatrixRow o2) {
		return Double.valueOf(o2.getOchiaiScore()).compareTo(o1.getOchiaiScore());

	}
}

class TarantulaComparator implements Comparator<ResultMatrixRow> {

	@Override
	public int compare(ResultMatrixRow o1, ResultMatrixRow o2) {
		return Double.valueOf(o2.getTarantulaScore()).compareTo(o1.getTarantulaScore());

	}
}

class JaccardComparator implements Comparator<ResultMatrixRow> {

	@Override
	public int compare(ResultMatrixRow o1, ResultMatrixRow o2) {
		return Double.valueOf(o2.getJaccardScore()).compareTo(o1.getJaccardScore());

	}
}
