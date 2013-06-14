package org.schemaanalyst.datageneration.analyst;

import org.schemaanalyst.representation.CheckConstraint;
import org.schemaanalyst.representation.Constraint;
import org.schemaanalyst.representation.ConstraintVisitor;
import org.schemaanalyst.representation.ForeignKeyConstraint;
import org.schemaanalyst.representation.NotNullConstraint;
import org.schemaanalyst.representation.PrimaryKeyConstraint;
import org.schemaanalyst.representation.UniqueConstraint;

public class ConstraintAnalystFactory {

	public ConstraintAnalyst create(Constraint constraint) {
		return create(constraint, false);
	}
	
	public ConstraintAnalyst create(Constraint constraint, 
			 						boolean considerNull) {
	
		class ConstraintAnalystCreator implements ConstraintVisitor {
	
			ConstraintAnalyst analyst;
			boolean considerNull;
			
			public ConstraintAnalyst createFor(Constraint constraint,
											   boolean considerNull) {
				this.considerNull = considerNull;
				constraint.accept(this);
				return analyst;
			}
			
			public void visit(CheckConstraint constraint) {
			}
	
			public void visit(ForeignKeyConstraint foreignKey) {
				analyst = new ReferenceAnalyst(foreignKey.getColumns(),
											   foreignKey.getReferenceColumns(),
											   considerNull);
			}
	
			public void visit(NotNullConstraint notNull) {
				analyst = new NotNullAnalyst(notNull.getColumn());
			}
	
			public void visit(PrimaryKeyConstraint primaryKey) {
				// NULL should never satisfy a primary key constraint
				// hence can never consider NULL.
				
				analyst = new UniqueAnalyst(primaryKey.getColumns(), 
											false);
			}
	
			public void visit(UniqueConstraint unique) {
				analyst = new UniqueAnalyst(unique.getColumns(),
										    considerNull);
			}		
		}
		
		return new ConstraintAnalystCreator().createFor(constraint, considerNull);
	}	
}
