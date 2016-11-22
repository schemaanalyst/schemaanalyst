package org.schemaanalyst.util;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.DataGenerationResult;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestGenerationException;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.data.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.derby.impl.sql.execute.ConstraintConstantAction;
import org.dbunit.dataset.Columns;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import parsedcasestudy.*;

public class Selector {
	public static void main(String[] args) {
		/*
		MetaDataGenerator m = new MetaDataGenerator();
		m.connectDB();
		m.selectTableAndColNames();
		m.selectARecordAndShuffle();
		*/
		
		// intial


        JCommanderParams jcp = new JCommanderParams();

        String criterion = jcp.criterion;

        String datagenerator = jcp.generator;

        String dbms = jcp.dbms;
		
		// End intail
		Schema bank = new ArtistTerm();
		
		
        System.out.println("===================================Data================================");
		DataMapper mapper = new DataMapper();
		mapper.connectDB(bank);
		//mapper.mapData();
		System.out.println(bank.getConnectedTables(bank.getTable("artist_mbtag")));
		System.out.println(mapper.mapStateData(bank.getTable("artist_mbtag")));
        System.out.println("=======================================================================");
		System.out.println("DONE");
		
		/*
		for(int i = 0; i < 10; i++) {
	        System.out.println("===========================Data "+ i +"================================");
			DataMapper mapper = new DataMapper();
			mapper.connectDB(bank);
			mapper.mapData();

			System.out.println(mapper.getData());
	        System.out.println("=======================================================================");
			System.out.println("DONE");
		}
		*/
		/*
		Data oldData = mapper.getData();
		Schema schema = mapper.getSchema();
		Data newData = new Data();
		
		
		/*
		int runs = 1000;
		
		//Random random = new Random();
		//Long nullProbability = -0L;
		for (Table table : schema.getTablesInOrder()) {
			List<Row> new_rows = new ArrayList<Row>();
			for (int i = 0; i < runs; i++) {
				List<Cell> new_cells = new ArrayList<Cell>();
				for (Column col : table.getColumns()) {
					
					List<Cell> cells = oldData.getCells(table, col);
					int index = ThreadLocalRandom.current().nextInt(cells.size());
					Cell old_cell = cells.get(index);
					Cell new_cell = new Cell(col, old_cell.getValue());
	
					
					boolean setToNull = new Random().nextInt(5)==0;;
					if (setToNull) {
						new_cell.setNull(true);
						new_cells.add(new_cell);
					} else {
						new_cells.add(new_cell);
					}
				}
				new_rows.add(new Row(table, new_cells));
			}
			
			newData.addRows(table, new_rows);
		}
		
		System.out.println(newData.toString());
		
		
		/*
		ObjectiveFunction<Data> objectiveFunction;
		
		
        DBMS dbmsObject = DBMSFactory.instantiate(dbms);
        TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, mapper.getSchema(), dbmsObject).generateRequirements();
        System.out.println(testRequirements.size());
        for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {

            Predicate predicate = testRequirement.getPredicate();
            Table table = getTestRequirementTable(testRequirement);
        	
            System.out.println("\nGENERATING TEST CASE");
            for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
            	System.out.println(testRequirementDescriptor.toString());
            }
                        
            System.out.println("--- Predicate is " + predicate);

            Data state = mapper.getData();
            Data data = new Data();	
        }
		
		/*
		System.out.println(mapper.getNumberOfTables());
		System.out.println(mapper.getTableRows(0).toString());
		mapper.returnNotNullColumns(0);
		mapper.returnUniqueColumns(1);
		Table tbl = mapper.getTable("Account");
		Column col = mapper.getColumn(tbl, "id");
		List<Row> virtical_rows = mapper.getTableColumnData(tbl, col);
		for (Row row : virtical_rows)
			System.out.println(row.getCell(col));
			*/
	}

}
