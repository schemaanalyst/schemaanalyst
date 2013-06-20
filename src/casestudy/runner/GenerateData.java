package casestudy.runner;

import org.schemaanalyst.SchemaAnalyst;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;

public class GenerateData {

	public static void main(String[] args) throws Exception {
		for (Schema schema : InstantiateSchema.instantiateSchemas(args)) {			
			
			//Schema schema = new PagilaPrime();
		
			if (!schema.getName().equals("Pagila'")) {
			
				DataGenerator generator = SchemaAnalyst.constructDataGenerator(
						"alternatingvalue", schema, new ValueFactory());
			
				CoverageReport report = generator.generate();
			
				System.out.println(report);
			}
		}
	}		
}
     