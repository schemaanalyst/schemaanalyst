package org.schemaanalyst.datageneration.domainspecific;

import org.schemaanalyst.datageneration.analyst.BetweenAnalyst;
import org.schemaanalyst.datageneration.analyst.ConstraintAnalyst;
import org.schemaanalyst.datageneration.analyst.InAnalyst;
import org.schemaanalyst.datageneration.analyst.NotNullAnalyst;
import org.schemaanalyst.datageneration.analyst.ReferenceAnalyst;
import org.schemaanalyst.datageneration.analyst.RelationalPredicateAnalyst;
import org.schemaanalyst.datageneration.analyst.UniqueAnalyst;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.representation.CheckConstraint;
import org.schemaanalyst.representation.Constraint;
import org.schemaanalyst.representation.ConstraintVisitor;
import org.schemaanalyst.representation.ForeignKeyConstraint;
import org.schemaanalyst.representation.NotNullConstraint;
import org.schemaanalyst.representation.PrimaryKeyConstraint;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.UniqueConstraint;
import org.schemaanalyst.representation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.representation.checkcondition.CheckConditionVisitor;
import org.schemaanalyst.representation.checkcondition.InCheckCondition;
import org.schemaanalyst.representation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.util.random.Random;

public class ConstraintHandlerFactory {

	protected Constraint constraint; 
	protected boolean goalIsToSatisfy;
	protected CellRandomizer cellRandomizer;
	protected Random random;
	
	public ConstraintHandlerFactory(Constraint constraint, 
									boolean goalIsToSatisfy,
									CellRandomizer cellRandomizer,
									Random random) {
		this.constraint = constraint;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.cellRandomizer = cellRandomizer;
		this.random = random;
	}
	
	public ConstraintHandler<? extends ConstraintAnalyst> create() {
		
		class ConstraintDispatcher implements ConstraintVisitor {
			
			ConstraintHandler<?> constraintHandler;
			
			ConstraintHandler<?> dispatch(Constraint constraint) {
				constraint.accept(this);
				return constraintHandler;
			}
			
			public void visit(CheckConstraint constraint) {
				constraintHandler = create(constraint);
			}

			public void visit(ForeignKeyConstraint constraint) {
				constraintHandler = create(constraint);
			}

			public void visit(NotNullConstraint constraint) {
				constraintHandler = create(constraint);
			}

			public void visit(PrimaryKeyConstraint constraint) {
				constraintHandler = create(constraint);
			}

			public void visit(UniqueConstraint constraint) {		
				constraintHandler = create(constraint);
			}
		}
				
		return new ConstraintDispatcher().dispatch(constraint);
	}
	
	protected ConstraintHandler<?> create(CheckConstraint checkConstraint) {
		
		class PredicateDispatcher implements CheckConditionVisitor {

			Table table;
			ConstraintHandler<?> constraintHandler;
			
			ConstraintHandler<?> dispatch(CheckConstraint checkConstraint) {
				table = checkConstraint.getTable();
				checkConstraint.getCheckCondition().accept(this);
				return constraintHandler;
			}
			
			public void visit(BetweenCheckCondition predicate) {
				constraintHandler = create(predicate, table);
			}
			
			public void visit(InCheckCondition predicate) {
				constraintHandler = create(predicate, table);
			}

			public void visit(RelationalCheckCondition predicate) {
				constraintHandler = create(predicate, table);
			}
		}		
		
		return new PredicateDispatcher().dispatch(checkConstraint);		
	}
	
	protected ConstraintHandler<?> create(BetweenCheckCondition betweenCheckPredicate, Table table) {
		boolean allowNull = goalIsToSatisfy;
		
		return new BetweenHandler(new BetweenAnalyst(betweenCheckPredicate, table, allowNull),
								  goalIsToSatisfy,
								  allowNull,
								  cellRandomizer,
								  random);
	}
	
	protected ConstraintHandler<?> create(InCheckCondition inCheckPredicate, Table table) {
		boolean allowNull = goalIsToSatisfy;
		
		return new InHandler(new InAnalyst(inCheckPredicate, table, allowNull),
				  		 	 goalIsToSatisfy,
				  		 	 allowNull,
				  		 	 cellRandomizer,
				  		 	 random);
	}
	
	protected ConstraintHandler<?> create(RelationalCheckCondition relationalCheckPredicate, Table table) {
		boolean allowNull = goalIsToSatisfy;
		
		return new RelationalPredicateHandler(new RelationalPredicateAnalyst(relationalCheckPredicate, 
																			 table, 
																			 allowNull),
											  goalIsToSatisfy,
											  allowNull,
											  cellRandomizer,
											  random);
	}
	
	protected ConstraintHandler<?> create(ForeignKeyConstraint foreignKeyConstraint) {
		boolean allowNull = goalIsToSatisfy;
	
		return new ReferenceHandler(new ReferenceAnalyst(foreignKeyConstraint.getColumns(), 
														 foreignKeyConstraint.getReferenceColumns(), 
														 allowNull),
														 goalIsToSatisfy,
									allowNull, 
									cellRandomizer, 
									random);
	}
	
	protected ConstraintHandler<?> create(NotNullConstraint notNullConstraint) {
		return new NotNullHandler(new NotNullAnalyst(notNullConstraint.getColumn()),
								  goalIsToSatisfy);
		
	}
	
	protected ConstraintHandler<?> create(PrimaryKeyConstraint primaryKeyConstraint) {
		boolean allowNull = !goalIsToSatisfy;
		
		return new UniqueHandler(new UniqueAnalyst(primaryKeyConstraint.getColumns(), 
												   allowNull),
								 goalIsToSatisfy,
								 allowNull, 						
								 cellRandomizer, 
								 random); 
	}
	
	protected ConstraintHandler<?> create(UniqueConstraint uniqueConstraint) {
		boolean allowNull = goalIsToSatisfy;
		
		return new UniqueHandler(new UniqueAnalyst(uniqueConstraint.getColumns(), 
												   allowNull),
								 goalIsToSatisfy,
								 allowNull, 						
								 cellRandomizer, 
								 random);				
	}	
}
