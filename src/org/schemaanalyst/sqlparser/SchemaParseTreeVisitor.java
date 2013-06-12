package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TParseTreeVisitor;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import org.schemaanalyst.schema.Schema;

class SchemaParseTreeVisitor extends TParseTreeVisitor {

	Schema schema;
	
	SchemaParseTreeVisitor(Schema schema) {
		this.schema = schema;
	}
	
    @Override
    public void preVisit(TCreateTableSqlStatement ctx) {
        System.out.println("table: " + ctx.getTableName().toString());
    }

    @Override
    public void postVisit(TColumnDefinition ctx) {
        System.out.println("column: " + ctx.getColumnName().toString());
        System.out.println("\t" + ctx.getDatatype().getDataType());
    }

    @Override
    public void postVisit(TConstraint ctx) {
        System.out.println("constraint");
    }	
	
}
