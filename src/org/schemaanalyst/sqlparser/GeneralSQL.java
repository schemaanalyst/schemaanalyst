/*
 */
package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.*;
import gudusoft.gsqlparser.nodes.*;
import gudusoft.gsqlparser.stmt.*;
import org.schemaanalyst.schema.*;

/**
 *
 * @author chris
 */
public class GeneralSQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        parse("/Users/phil/Projects/schemaanalyst/casestudies/schemas/Cloc.sql", EDbVendor.dbvmysql);
    }

    private static Schema parse(String filepath, EDbVendor vendor) {
        // Set up parser
        TGSqlParser parser = new TGSqlParser(vendor);
        parser.setSqlfilename(filepath);
        // Attempt parse
        if (parser.parse() != 0) {
            throw new RuntimeException(parser.getErrormessage());
        }
        // Apply visitor
        Visitor v = new Visitor();
        TStatementList stmts = parser.sqlstatements;
        for (int i = 0; i < stmts.size(); i++) {
            stmts.get(i).accept(v);
        }
        return null;
    }

    static class Visitor extends TParseTreeVisitor {

        Schema schema;
        Table table;

        public Visitor() {
            schema = new Schema("schema");
        }

        public Schema getSchema() {
            return schema;
        }

        @Override
        public void preVisit(TCreateTableSqlStatement ctx) {
            table = schema.createTable(ctx.getTableName().toString());
            System.out.println("table: " + ctx.getTableName().toString());
        }

        @Override
        public void postVisit(TColumnDefinition ctx) {
            //ColumnFactory.makeColumn(ctx, table);
            System.out.println("column: " + ctx.getColumnName().toString());
            System.out.println("\t" + ctx.getDatatype().getDataType());
        }

        @Override
        public void postVisit(TConstraint ctx) {
            System.out.println("constraint");
        }
    }
}
