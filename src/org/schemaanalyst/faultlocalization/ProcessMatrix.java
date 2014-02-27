package org.schemaanalyst.faultlocalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProcessMatrix {
	public static ResultMatrix OchiaiRanked;
	public static ResultMatrix TarantulaRanked;
	public static ResultMatrix JaccardRanked;

	public static void Process(ResultMatrix matrix) {
		setScores(matrix);
		setRanks(matrix);
	}

	public static void setScores(ResultMatrix matrix) {
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
		OchiaiRanked = new ResultMatrix(matrix1);

		Collections.sort(matrix2, new TarantulaComparator());
		TarantulaRanked = new ResultMatrix(matrix2);

		Collections.sort(matrix3, new JaccardComparator());
		JaccardRanked = new ResultMatrix(matrix3);

	}

	public static void writeToCSV() {

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
