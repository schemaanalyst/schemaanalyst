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
 * Java code originally generated: 2013/08/17 00:30:55
 *
 */

@SuppressWarnings("serial")
public class NistWeather extends Schema {

	public NistWeather() {
		super("NistWeather");

		Table tableStation = this.createTable("Station");
		tableStation.createColumn("ID", new IntDataType());
		tableStation.createColumn("CITY", new CharDataType(20));
		tableStation.createColumn("STATE", new CharDataType(2));
		tableStation.createColumn("LAT_N", new IntDataType());
		tableStation.createColumn("LONG_W", new IntDataType());
		this.createPrimaryKeyConstraint(tableStation, tableStation.getColumn("ID"));
		this.createCheckConstraint(tableStation, new BetweenExpression(new ColumnExpression(tableStation, tableStation.getColumn("LAT_N")), new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(90)), false, false));
		this.createCheckConstraint(tableStation, new BetweenExpression(new ColumnExpression(tableStation, tableStation.getColumn("LONG_W")), new ConstantExpression(new NumericValue(-180)), new ConstantExpression(new NumericValue(180)), false, false));
		this.createNotNullConstraint(tableStation, tableStation.getColumn("LAT_N"));
		this.createNotNullConstraint(tableStation, tableStation.getColumn("LONG_W"));

		Table tableStats = this.createTable("Stats");
		tableStats.createColumn("ID", new IntDataType());
		tableStats.createColumn("MONTH", new IntDataType());
		tableStats.createColumn("TEMP_F", new IntDataType());
		tableStats.createColumn("RAIN_I", new IntDataType());
		this.createPrimaryKeyConstraint(tableStats, tableStats.getColumn("ID"), tableStats.getColumn("MONTH"));
		this.createCheckConstraint(tableStats, new BetweenExpression(new ColumnExpression(tableStats, tableStats.getColumn("MONTH")), new ConstantExpression(new NumericValue(1)), new ConstantExpression(new NumericValue(12)), false, false));
		this.createCheckConstraint(tableStats, new BetweenExpression(new ColumnExpression(tableStats, tableStats.getColumn("TEMP_F")), new ConstantExpression(new NumericValue(80)), new ConstantExpression(new NumericValue(150)), false, false));
		this.createCheckConstraint(tableStats, new BetweenExpression(new ColumnExpression(tableStats, tableStats.getColumn("RAIN_I")), new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(100)), false, false));
		this.createForeignKeyConstraint(tableStats, tableStats.getColumn("ID"), tableStation, tableStation.getColumn("ID"));
		this.createNotNullConstraint(tableStats, tableStats.getColumn("MONTH"));
		this.createNotNullConstraint(tableStats, tableStats.getColumn("TEMP_F"));
		this.createNotNullConstraint(tableStats, tableStats.getColumn("RAIN_I"));
	}
}

