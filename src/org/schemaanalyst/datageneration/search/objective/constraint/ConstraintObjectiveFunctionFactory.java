package org.schemaanalyst.datageneration.search.objective.constraint;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.sqlrepresentation.checkcondition.CheckConditionVisitor;
import org.schemaanalyst.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;

public class ConstraintObjectiveFunctionFactory {
	
	protected Constraint constraint;
	protected Data state;
	protected boolean goalIsToSatisfy, considerNull;	
	
	public ConstraintObjectiveFunctionFactory(Constraint constraint,
											  Data state,
											  boolean goalIsToSatisfy,
											  boolean considerNull) {
		this.constraint = constraint;
		this.state = state;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.considerNull = considerNull;
	}
	
	public ObjectiveFunction<Data> create() {
		
		class ConstraintDispatcher implements ConstraintVisitor {

			ObjectiveFunction<Data> objFun;
			
			ObjectiveFunction<Data> dispatch() {
				constraint.accept(this);
				return objFun;
			}
			
			public void visit(CheckConstraint constraint) {
				objFun = createForCheckConstraint(constraint);
			}

			public void visit(ForeignKeyConstraint constraint) {
				objFun = createForForeignKeyConstraint(constraint);
			}

			public void visit(NotNullConstraint constraint) {
				objFun = createForNotNullConstraint(constraint);
			}

			public void visit(PrimaryKeyConstraint constraint) {
				objFun = createForPrimaryKeyConstraint(constraint);
			}

			public void visit(UniqueConstraint constraint) {
				objFun = createForUniqueConstraint(constraint);
			}
		}
		
		return (new ConstraintDispatcher()).dispatch();
	}
	
	protected ObjectiveFunction<Data> createForCheckConstraint(CheckConstraint checkConstraint) {
		
		class PredicateDispatcher implements CheckConditionVisitor {

			ObjectiveFunction<Data> objFun;
			Table table;
			String description;
			boolean allowNull;
			
			ObjectiveFunction<Data> dispatch(CheckConstraint checkConstraint,
												 String description,
												 boolean allowNull) {
				table = checkConstraint.getTable();
				this.description = description;
				this.allowNull = allowNull;
				checkConstraint.getCheckCondition().accept(this);
				return objFun;
			}
			
			public void visit(BetweenCheckCondition predicate) {
				objFun = new BetweenCheckPredicateObjectiveFunction(
								predicate, table, state, description, goalIsToSatisfy, allowNull);
			}
			
			public void visit(InCheckCondition predicate) {
				objFun = new InCheckPredicateObjectiveFunction(
								predicate, table, state, description, goalIsToSatisfy, allowNull);
			}

			public void visit(RelationalCheckCondition predicate) {
				objFun = new RelationalCheckPredicateObjectiveFunction(
								predicate, table, state, description, goalIsToSatisfy, allowNull);
			}
		}		
		
		String description = makeDescription();
		boolean allowNull = considerNull && goalIsToSatisfy;

		return (new PredicateDispatcher()).dispatch(checkConstraint, description, allowNull);
	}
	
	protected ObjectiveFunction<Data> createForPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {

		boolean allowNull = false; //considerNull && !goalIsToSatisfy;
		
		return new UniqueObjectiveFunction(
							primaryKeyConstraint.getColumns(),
							state, makeDescription(),
							goalIsToSatisfy, 
							allowNull); 
	}	
	
	protected ObjectiveFunction<Data> createForForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {

		boolean allowNull = considerNull && goalIsToSatisfy; 

		return new ReferenceObjectiveFunction(
							foreignKeyConstraint.getColumns(), 
							foreignKeyConstraint.getReferenceColumns(),
							state, makeDescription(),
							goalIsToSatisfy,
							allowNull);	
	}	
	
	protected ObjectiveFunction<Data> createForNotNullConstraint(NotNullConstraint notNullConstraint) {
		
		return new NullColumnObjectiveFunction(
							notNullConstraint.getColumn(),
							makeDescription(), 
							!goalIsToSatisfy);
	}	
	
	protected ObjectiveFunction<Data> createForUniqueConstraint(UniqueConstraint uniqueConstraint) {

		boolean allowNull = considerNull && goalIsToSatisfy; 
		
		return new UniqueObjectiveFunction(
							uniqueConstraint.getColumns(),
							state, makeDescription(),
							goalIsToSatisfy, 
							allowNull);		
	}	
	
	protected String makeDescription() {
		return ((goalIsToSatisfy) ? "Satisfy" : "Violate")  + " " + constraint.toString();	
	}
	
}
