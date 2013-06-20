package org.schemaanalyst.sqlrepresentation;

import java.util.ArrayList;
import java.util.List;

public class TableDependencyOrderer {

	protected List<Table> order;
	
	public List<Table> order(List<Table> tables) {
		
		order = new ArrayList<>();
		
		for (Table table : tables) {
			
			orderFrom(table, new ArrayList<Table>());
		}
		
		return order;
	}
	
	protected void orderFrom(Table currentTable, List<Table> runningList) {
		
		//System.out.println("\nOrdering from:" + currentTable);
		//System.out.println("-- current order is: "+order);
		//System.out.println("-- running list is: "+runningList);
		
		List<Table> references = currentTable.getConnectedTables();
		
		//System.out.println("-- references are : "+references);
		
		for (Table referencedTable : references) {

			if (!referencedTable.equals(currentTable) && !order.contains(referencedTable)) {
			
				//System.out.println("-- considering: "+referencedTable);
				
				if (runningList.contains(referencedTable)) {
					throw new SchemaConstructionException(
							"Cannot compute ordered table list due to a circular dependency between table " + 
							"\"" + currentTable + "\" and \"" + referencedTable + "\"");				
				}
				
				List<Table> appendedRunningList = new ArrayList<>();
				appendedRunningList.addAll(runningList);
				appendedRunningList.add(currentTable);
				
				orderFrom(referencedTable, appendedRunningList);
			}
		}
		
		if (!order.contains(currentTable)) {
			order.add(currentTable);
		}
	}
	
	/*
	List<Table> buggy = new ArrayList<>();
    
    buggy.add(customer);
    buggy.add(inventory);
    buggy.add(staff);
    buggy.add(address);
    buggy.add(store);
    buggy.add(film);
    buggy.add(city);
    buggy.add(language);
    buggy.add(country);
    
    
    buggy.add(rental);
	*/
}
