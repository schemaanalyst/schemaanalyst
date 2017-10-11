package org.schemaanalyst.data.generation.cellvaluegeneration;

import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.util.random.Random;

public class MySelectorCellValueGenerator extends RandomCellValueGenerator {

	public MySelectorCellValueGenerator(Random random, ValueInitializationProfile profile, double nullProbability,
			ValueLibrary valueLibrary, double useLibraryProbability) {
		super(random, profile, nullProbability, valueLibrary, useLibraryProbability);
		// TODO Auto-generated constructor stub
	}

	public ValueLibrary getVL() {
		return this.valueLibrary;
	}
}
