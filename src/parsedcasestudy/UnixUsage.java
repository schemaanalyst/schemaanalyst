package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * UnixUsage schema.
 * Java code originally generated: 2013/07/11 14:11:20
 *
 */

@SuppressWarnings("serial")
public class UnixUsage extends Schema {

	public UnixUsage() {
		super("UnixUsage");

		Table tableDeptInfo = this.createTable("DEPT_INFO");
		tableDeptInfo.addColumn("DEPT_ID", new IntDataType());
		tableDeptInfo.addColumn("DEPT_NAME", new VarCharDataType(50));
		tableDeptInfo.setPrimaryKeyConstraint(tableDeptInfo.getColumn("DEPT_ID"));
		tableDeptInfo.addNotNullConstraint(tableDeptInfo.getColumn("DEPT_ID"));

		Table tableCourseInfo = this.createTable("COURSE_INFO");
		tableCourseInfo.addColumn("COURSE_ID", new IntDataType());
		tableCourseInfo.addColumn("COURSE_NAME", new VarCharDataType(50));
		tableCourseInfo.addColumn("OFFERED_DEPT", new IntDataType());
		tableCourseInfo.addColumn("GRADUATE_LEVEL", new SmallIntDataType());
		tableCourseInfo.setPrimaryKeyConstraint(tableCourseInfo.getColumn("COURSE_ID"));
		tableCourseInfo.addForeignKeyConstraint(tableCourseInfo.getColumn("OFFERED_DEPT"), tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));
		tableCourseInfo.addNotNullConstraint(tableCourseInfo.getColumn("COURSE_ID"));

		Table tableOfficeInfo = this.createTable("OFFICE_INFO");
		tableOfficeInfo.addColumn("OFFICE_ID", new IntDataType());
		tableOfficeInfo.addColumn("OFFICE_NAME", new VarCharDataType(50));
		tableOfficeInfo.addColumn("HAS_PRINTER", new SmallIntDataType());
		tableOfficeInfo.setPrimaryKeyConstraint(tableOfficeInfo.getColumn("OFFICE_ID"));
		tableOfficeInfo.addNotNullConstraint(tableOfficeInfo.getColumn("OFFICE_ID"));

		Table tableRaceInfo = this.createTable("RACE_INFO");
		tableRaceInfo.addColumn("RACE_CODE", new IntDataType());
		tableRaceInfo.addColumn("RACE", new VarCharDataType(50));
		tableRaceInfo.setPrimaryKeyConstraint(tableRaceInfo.getColumn("RACE_CODE"));
		tableRaceInfo.addNotNullConstraint(tableRaceInfo.getColumn("RACE_CODE"));

		Table tableUserInfo = this.createTable("USER_INFO");
		tableUserInfo.addColumn("USER_ID", new VarCharDataType(50));
		tableUserInfo.addColumn("FIRST_NAME", new VarCharDataType(50));
		tableUserInfo.addColumn("LAST_NAME", new VarCharDataType(50));
		tableUserInfo.addColumn("SEX", new VarCharDataType(1));
		tableUserInfo.addColumn("DEPT_ID", new IntDataType());
		tableUserInfo.addColumn("OFFICE_ID", new IntDataType());
		tableUserInfo.addColumn("GRADUATE", new SmallIntDataType());
		tableUserInfo.addColumn("RACE", new IntDataType());
		tableUserInfo.addColumn("PASSWORD", new VarCharDataType(50));
		tableUserInfo.addColumn("YEARS_USING_UNIX", new IntDataType());
		tableUserInfo.addColumn("ENROLL_DATE", new DateDataType());
		tableUserInfo.setPrimaryKeyConstraint(tableUserInfo.getColumn("USER_ID"));
		tableUserInfo.addForeignKeyConstraint(tableUserInfo.getColumn("DEPT_ID"), tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));
		tableUserInfo.addForeignKeyConstraint(tableUserInfo.getColumn("OFFICE_ID"), tableOfficeInfo, tableOfficeInfo.getColumn("OFFICE_ID"));
		tableUserInfo.addForeignKeyConstraint(tableUserInfo.getColumn("RACE"), tableRaceInfo, tableRaceInfo.getColumn("RACE_CODE"));
		tableUserInfo.addNotNullConstraint(tableUserInfo.getColumn("USER_ID"));
		tableUserInfo.addNotNullConstraint(tableUserInfo.getColumn("PASSWORD"));

		Table tableTranscript = this.createTable("TRANSCRIPT");
		tableTranscript.addColumn("USER_ID", new VarCharDataType(50));
		tableTranscript.addColumn("COURSE_ID", new IntDataType());
		tableTranscript.addColumn("SCORE", new IntDataType());
		tableTranscript.setPrimaryKeyConstraint(tableTranscript.getColumn("USER_ID"), tableTranscript.getColumn("COURSE_ID"));
		tableTranscript.addForeignKeyConstraint(tableTranscript.getColumn("USER_ID"), tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		tableTranscript.addForeignKeyConstraint(tableTranscript.getColumn("COURSE_ID"), tableCourseInfo, tableCourseInfo.getColumn("COURSE_ID"));
		tableTranscript.addNotNullConstraint(tableTranscript.getColumn("USER_ID"));
		tableTranscript.addNotNullConstraint(tableTranscript.getColumn("COURSE_ID"));

		Table tableUnixCommand = this.createTable("UNIX_COMMAND");
		tableUnixCommand.addColumn("UNIX_COMMAND", new VarCharDataType(50));
		tableUnixCommand.addColumn("CATEGORY", new VarCharDataType(50));
		tableUnixCommand.setPrimaryKeyConstraint(tableUnixCommand.getColumn("UNIX_COMMAND"));
		tableUnixCommand.addNotNullConstraint(tableUnixCommand.getColumn("UNIX_COMMAND"));

		Table tableUsageHistory = this.createTable("USAGE_HISTORY");
		tableUsageHistory.addColumn("USER_ID", new VarCharDataType(50));
		tableUsageHistory.addColumn("SESSION_ID", new IntDataType());
		tableUsageHistory.addColumn("LINE_NO", new IntDataType());
		tableUsageHistory.addColumn("COMMAND_SEQ", new IntDataType());
		tableUsageHistory.addColumn("COMMAND", new VarCharDataType(50));
		tableUsageHistory.addForeignKeyConstraint(tableUsageHistory.getColumn("USER_ID"), tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		tableUsageHistory.addNotNullConstraint(tableUsageHistory.getColumn("USER_ID"));
	}
}

