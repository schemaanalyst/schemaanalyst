package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * UnixUsage schema.
 * Java code originally generated: 2013/08/17 00:31:06
 *
 */

@SuppressWarnings("serial")
public class UnixUsage extends Schema {

	public UnixUsage() {
		super("UnixUsage");

		Table tableDeptInfo = this.createTable("DEPT_INFO");
		tableDeptInfo.createColumn("DEPT_ID", new IntDataType());
		tableDeptInfo.createColumn("DEPT_NAME", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));
		this.createNotNullConstraint(tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));

		Table tableCourseInfo = this.createTable("COURSE_INFO");
		tableCourseInfo.createColumn("COURSE_ID", new IntDataType());
		tableCourseInfo.createColumn("COURSE_NAME", new VarCharDataType(50));
		tableCourseInfo.createColumn("OFFERED_DEPT", new IntDataType());
		tableCourseInfo.createColumn("GRADUATE_LEVEL", new SmallIntDataType());
		this.createPrimaryKeyConstraint(tableCourseInfo, tableCourseInfo.getColumn("COURSE_ID"));
		this.createForeignKeyConstraint(tableCourseInfo, tableCourseInfo.getColumn("OFFERED_DEPT"), tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));
		this.createNotNullConstraint(tableCourseInfo, tableCourseInfo.getColumn("COURSE_ID"));

		Table tableOfficeInfo = this.createTable("OFFICE_INFO");
		tableOfficeInfo.createColumn("OFFICE_ID", new IntDataType());
		tableOfficeInfo.createColumn("OFFICE_NAME", new VarCharDataType(50));
		tableOfficeInfo.createColumn("HAS_PRINTER", new SmallIntDataType());
		this.createPrimaryKeyConstraint(tableOfficeInfo, tableOfficeInfo.getColumn("OFFICE_ID"));
		this.createNotNullConstraint(tableOfficeInfo, tableOfficeInfo.getColumn("OFFICE_ID"));

		Table tableRaceInfo = this.createTable("RACE_INFO");
		tableRaceInfo.createColumn("RACE_CODE", new IntDataType());
		tableRaceInfo.createColumn("RACE", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableRaceInfo, tableRaceInfo.getColumn("RACE_CODE"));
		this.createNotNullConstraint(tableRaceInfo, tableRaceInfo.getColumn("RACE_CODE"));

		Table tableUserInfo = this.createTable("USER_INFO");
		tableUserInfo.createColumn("USER_ID", new VarCharDataType(50));
		tableUserInfo.createColumn("FIRST_NAME", new VarCharDataType(50));
		tableUserInfo.createColumn("LAST_NAME", new VarCharDataType(50));
		tableUserInfo.createColumn("SEX", new VarCharDataType(1));
		tableUserInfo.createColumn("DEPT_ID", new IntDataType());
		tableUserInfo.createColumn("OFFICE_ID", new IntDataType());
		tableUserInfo.createColumn("GRADUATE", new SmallIntDataType());
		tableUserInfo.createColumn("RACE", new IntDataType());
		tableUserInfo.createColumn("PASSWORD", new VarCharDataType(50));
		tableUserInfo.createColumn("YEARS_USING_UNIX", new IntDataType());
		tableUserInfo.createColumn("ENROLL_DATE", new DateDataType());
		this.createPrimaryKeyConstraint(tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		this.createForeignKeyConstraint(tableUserInfo, tableUserInfo.getColumn("DEPT_ID"), tableDeptInfo, tableDeptInfo.getColumn("DEPT_ID"));
		this.createForeignKeyConstraint(tableUserInfo, tableUserInfo.getColumn("OFFICE_ID"), tableOfficeInfo, tableOfficeInfo.getColumn("OFFICE_ID"));
		this.createForeignKeyConstraint(tableUserInfo, tableUserInfo.getColumn("RACE"), tableRaceInfo, tableRaceInfo.getColumn("RACE_CODE"));
		this.createNotNullConstraint(tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		this.createNotNullConstraint(tableUserInfo, tableUserInfo.getColumn("PASSWORD"));

		Table tableTranscript = this.createTable("TRANSCRIPT");
		tableTranscript.createColumn("USER_ID", new VarCharDataType(50));
		tableTranscript.createColumn("COURSE_ID", new IntDataType());
		tableTranscript.createColumn("SCORE", new IntDataType());
		this.createPrimaryKeyConstraint(tableTranscript, tableTranscript.getColumn("USER_ID"), tableTranscript.getColumn("COURSE_ID"));
		this.createForeignKeyConstraint(tableTranscript, tableTranscript.getColumn("USER_ID"), tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		this.createForeignKeyConstraint(tableTranscript, tableTranscript.getColumn("COURSE_ID"), tableCourseInfo, tableCourseInfo.getColumn("COURSE_ID"));
		this.createNotNullConstraint(tableTranscript, tableTranscript.getColumn("USER_ID"));
		this.createNotNullConstraint(tableTranscript, tableTranscript.getColumn("COURSE_ID"));

		Table tableUnixCommand = this.createTable("UNIX_COMMAND");
		tableUnixCommand.createColumn("UNIX_COMMAND", new VarCharDataType(50));
		tableUnixCommand.createColumn("CATEGORY", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableUnixCommand, tableUnixCommand.getColumn("UNIX_COMMAND"));
		this.createNotNullConstraint(tableUnixCommand, tableUnixCommand.getColumn("UNIX_COMMAND"));

		Table tableUsageHistory = this.createTable("USAGE_HISTORY");
		tableUsageHistory.createColumn("USER_ID", new VarCharDataType(50));
		tableUsageHistory.createColumn("SESSION_ID", new IntDataType());
		tableUsageHistory.createColumn("LINE_NO", new IntDataType());
		tableUsageHistory.createColumn("COMMAND_SEQ", new IntDataType());
		tableUsageHistory.createColumn("COMMAND", new VarCharDataType(50));
		this.createForeignKeyConstraint(tableUsageHistory, tableUsageHistory.getColumn("USER_ID"), tableUserInfo, tableUserInfo.getColumn("USER_ID"));
		this.createNotNullConstraint(tableUsageHistory, tableUsageHistory.getColumn("USER_ID"));
	}
}

