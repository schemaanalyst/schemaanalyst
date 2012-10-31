package casestudy.runner;

import java.util.List;

import org.schemaanalyst.mutation.mutators.ConstraintMutator;
import org.schemaanalyst.schema.Schema;

import casestudy.Flights;

public class Mutate {

	public static void main(String[] args) throws Exception {
		//for (Schema schema : InstantiateSchema.instantiateSchemas(args)) {
			Schema schema = new Flights();
			System.out.println("-- MUTATING " + schema.getName());
			mutate(schema);
		//}
	}
	
	public static void mutate(Schema schema) {
		ConstraintMutator cm = new ConstraintMutator();
		
		System.out.println("Original schema");
		InstantiateSchema.printSchema(schema);
		
		List<Schema> mutants = cm.produceMutants(schema);
		int i = 1;
		for (Schema mutant : mutants) {
			System.out.println("\nMutant "+i);
			InstantiateSchema.printSchema(mutant);
			i++;
		}
	}	
}
