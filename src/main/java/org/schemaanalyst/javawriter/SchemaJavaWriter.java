package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.util.IndentableStringBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.schemaanalyst.javawriter.MethodNameConstants.SCHEMA_CREATE_TABLE_METHOD;
import static org.schemaanalyst.javawriter.MethodNameConstants.TABLE_CREATE_COLUMN_METHOD;

public class SchemaJavaWriter {

    // schema we are writing Java for
    private Schema schema;
    // sub-writers
    private JavaWriter javaWriter;
    private ConstraintJavaWriter constraintJavaWriter;
    private DataTypeJavaWriter dataTypeJavaWriter;
    private ExpressionJavaWriter expressionJavaWriter;
    private ValueJavaWriter valueJavaWriter;
    // for nice indented output
    private IndentableStringBuilder code;

    public SchemaJavaWriter(Schema schema) {
        this.schema = schema;
    }

    public String writeSchema() {
        return writeSchema(null);
    }

    public String writeSchema(String packageName) {
        return writeSchema(packageName, true, true);
    }

    public String writeSchema(String packageName, boolean addComment, boolean suppressSerialWarning) {
        // initialise	
        javaWriter = new JavaWriter();
        dataTypeJavaWriter = new DataTypeJavaWriter(javaWriter);
        valueJavaWriter = new ValueJavaWriter(javaWriter);
        expressionJavaWriter = new ExpressionJavaWriter(javaWriter, valueJavaWriter);
        constraintJavaWriter = new ConstraintJavaWriter(javaWriter, expressionJavaWriter);

        code = new IndentableStringBuilder();

        // add when generated comment
        if (addComment) {
            String dateString =
                    (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(
                    Calendar.getInstance().getTime());
            code.appendln("/*");
            code.appendln(" * " + schema.getName() + " schema.");
            code.appendln(" * Java code originally generated: " + dateString);
            code.appendln(" *");
            code.appendln(" */");
            code.appendln();
        }

        // add suppress warnings annotation
        if (suppressSerialWarning) {
            code.appendln("@SuppressWarnings(\"serial\")");
        }

        // get schema info
        javaWriter.addImportFor(Schema.class);
        String schemaClassName = Schema.class.getSimpleName();
        String schemaName = schema.getName();

        // start class
        code.appendln("public class " + schemaName + " extends " + schemaClassName + " {");

        // start constructor
        code.appendln();
        code.appendln(1, "public " + schemaName + "() {");
        code.appendln(2, "super(\"" + schemaName + "\");");

        // write table statements
        List<Table> tables = schema.getTablesInOrder();
        for (Table table : tables) {
            appendTableCode(schema, table);
        }

        // end constructor		
        code.appendln(1, "}");

        // end class
        code.appendln(0, "}");

        // get final java code
        String preamble = javaWriter.writePackageStatement(packageName)
                + javaWriter.writeImportStatements();
        if (preamble != "") {
            preamble += System.lineSeparator();
        }

        return preamble + code.toString();
    }

    protected void appendTableCode(Schema schema, Table table) {
        // add table creation statement
        String tableCreation =
                javaWriter.writeAssignment(
                Table.class,
                javaWriter.getVariableName(table),
                javaWriter.writeMethodCall(
                "this",
                SCHEMA_CREATE_TABLE_METHOD,
                javaWriter.writeString(table.getName())));

        code.appendln();
        code.appendln(tableCreation + ";");

        // add columns
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            String columnAddition = javaWriter.writeTableMethodCall(
                    table,
                    TABLE_CREATE_COLUMN_METHOD,
                    javaWriter.writeString(column),
                    dataTypeJavaWriter.writeConstruction(column.getDataType()));

            code.appendln(columnAddition + ";");
        }

        // add constraints
        List<Constraint> constraints = schema.getConstraints(table);
        for (Constraint constraint : constraints) {
            code.appendln(constraintJavaWriter.writeAdditionToTable(constraint) + ";");
        }
    }
}
