package org.schemaanalyst.sqlrepresentation.constraint;


/**
 * A class for visiting the different subclasses of constraint.
 *
 * @author Phil McMinn
 *
 */
public interface ConstraintVisitor {

    /**
     * Visits check constraints.
     *
     * @param constraint The check constraint to visit.
     */
    public void visit(CheckConstraint constraint);

    /**
     * Visits foreign key constraints.
     *
     * @param constraint The foreign key constraint to visit.
     */
    public void visit(ForeignKeyConstraint constraint);

    /**
     * Visits not null constraints.
     *
     * @param constraint The not null constraint to visit.
     */
    public void visit(NotNullConstraint constraint);

    /**
     * Visits primary key constraints.
     *
     * @param constraint The primary key constraint to visit.
     */
    public void visit(PrimaryKeyConstraint constraint);

    /**
     * Visits unique constraints.
     *
     * @param constraint The unique constraint to visit.
     */
    public void visit(UniqueConstraint constraint);
}
