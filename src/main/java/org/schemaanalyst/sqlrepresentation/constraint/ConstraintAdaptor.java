package org.schemaanalyst.sqlrepresentation.constraint;

/**
 * Created by phil on 05/09/2014.
 */
public class ConstraintAdaptor implements ConstraintVisitor {
    @Override
    public void visit(CheckConstraint constraint) {

    }

    @Override
    public void visit(ForeignKeyConstraint constraint) {

    }

    @Override
    public void visit(NotNullConstraint constraint) {

    }

    @Override
    public void visit(PrimaryKeyConstraint constraint) {

    }

    @Override
    public void visit(UniqueConstraint constraint) {

    }
}
