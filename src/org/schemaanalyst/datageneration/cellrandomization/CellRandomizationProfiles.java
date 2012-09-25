package org.schemaanalyst.datageneration.cellrandomization;

import java.math.BigDecimal;

import org.schemaanalyst.util.random.Random;

public class CellRandomizationProfiles {

	public static CellRandomizer small(Random random) {		
		return new CellRandomizer(
				random,
				0.1,     				// nullProbability
				  
				1990,					// yearMin
				2020,					// yearMax 
				  
				1,						// monthMin
				12,						// monthMax, 
				  
				1,						// dayMin
				31,						// dayMax,
				  
				0,						// hourMin
				23,						// hourMax
				  	
				0,						// minuteMin
				59,						// minuteMax
				  
				0,						// secondMin
				59,						// secondMax
				  
				new BigDecimal(-100),	// numericMin
				new BigDecimal(100),	// numericMax
				  
				10,						// stringLengthMax
				97,						// characterMin
				122,					// characterMax
				  
				631152000,				// timestampMin
				1577836800				// timestampMax		
		);
	}
	
	
	public static CellRandomizer large(Random random) {
		return new CellRandomizer(
				random,
				0.1,     				// nullProbability
				  
				0,						// yearMin
				5000,					// yearMax 
				  
				1,						// monthMin
				12,						// monthMax, 
				  
				1,						// dayMin
				31,						// dayMax,
				  
				0,						// hourMin
				23,						// hourMax
				  	
				0,						// minuteMin
				59,						// minuteMax
				  
				0,						// secondMin
				59,						// secondMax
				  
				new BigDecimal(-10000),	// numericMin
				new BigDecimal(10000),	// numericMax
				  
				20,						// stringLengthMax
				32,						// characterMin
				126,					// characterMax
				  
				-2147483648,			// timestampMin
				2147483647				// timestampMax		
		);
	}
	
	
}
