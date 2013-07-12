package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

/*
 * NistWeather schema.
 * Java code originally generated: 2013/07/11 14:10:29
 *
 */
@SuppressWarnings("serial")
public class NistWeather extends Schema {

    public NistWeather() {
        super("NistWeather");

        Table tableStation = this.createTable("Station");
        tableStation.addColumn("ID", new IntDataType());
        tableStation.addColumn("CITY", new CharDataType(20));
        tableStation.addColumn("STATE", new CharDataType(2));
        tableStation.addColumn("LAT_N", new IntDataType());
        tableStation.addColumn("LONG_W", new IntDataType());
        tableStation.setPrimaryKeyConstraint(tableStation.getColumn("ID"));
        tableStation.addNotNullConstraint(tableStation.getColumn("LAT_N"));
        tableStation.addNotNullConstraint(tableStation.getColumn("LONG_W"));
        tableStation.addCheckConstraint(new BetweenExpression(new ColumnExpression(tableStation.getColumn("LAT_N")), new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(90)), false));
        tableStation.addCheckConstraint(new BetweenExpression(new ColumnExpression(tableStation.getColumn("LONG_W")), new ConstantExpression(new NumericValue(180)), new ConstantExpression(new NumericValue(-180)), false));

        Table tableStats = this.createTable("Stats");
        tableStats.addColumn("ID", new IntDataType());
        tableStats.addColumn("MONTH", new IntDataType());
        tableStats.addColumn("TEMP_F", new IntDataType());
        tableStats.addColumn("RAIN_I", new IntDataType());
        tableStats.setPrimaryKeyConstraint(tableStats.getColumn("ID"), tableStats.getColumn("MONTH"));
        tableStats.addForeignKeyConstraint(tableStats.getColumn("ID"), tableStation, tableStation.getColumn("ID"));
        tableStats.addNotNullConstraint(tableStats.getColumn("MONTH"));
        tableStats.addNotNullConstraint(tableStats.getColumn("TEMP_F"));
        tableStats.addNotNullConstraint(tableStats.getColumn("RAIN_I"));
        tableStats.addCheckConstraint(new BetweenExpression(new ColumnExpression(tableStats.getColumn("MONTH")), new ConstantExpression(new NumericValue(1)), new ConstantExpression(new NumericValue(12)), false));
        tableStats.addCheckConstraint(new BetweenExpression(new ColumnExpression(tableStats.getColumn("TEMP_F")), new ConstantExpression(new NumericValue(80)), new ConstantExpression(new NumericValue(150)), false));
        tableStats.addCheckConstraint(new BetweenExpression(new ColumnExpression(tableStats.getColumn("RAIN_I")), new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(100)), false));
    }
}
