package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * UnixUsage schema.
 * Java code originally generated: 2013/08/15 10:52:20
 *
 */

@SuppressWarnings("serial")
public class UnixUsage extends Schema {

	public UnixUsage() {
		super("UnixUsage");

		Table tableDeptInfo = this.createTable("DEPT_INFO");
		tableDeptInfo.createColumn("DEPT_ID", new IntDataType());
		tableDeptInfo.createColumn("DEPT_NAME", new VarCharDataType(50));
		tableDeptInfo.createPrimaryKeyConstraint(tableDeptInfo.getColumn("DEPT_ID"));
		tableDeptInfo.createNotNullConstraint(tableDeptInfo.getColumn("DEPT_ID"));

		Table tableCourseInfo = this.createTable("COURSE_INFO");
		tableCourseInfo.createColumn("COURSE_ID", new IntDataType());
		tableCourseInfo.createColumn("COURSE_NAME", new VarCharDataType(50));
		tableCourseInfo.createColumn("OFFERED_DEPT", new IntDataType());
		tableCourseInfo.createColumn("GRADUATE_LEVEL", new SmallIntDataType());
		tableCourseInfo.createPrimaryKeyConstraint(tableCourseInfo.getColumn("COURSE_ID"));
		tableCourseInfo.createForeignKeyConstraint(tableCourseInfo.getColumn("OFFERED_DEPT"), tableDeptInfo, tableCourseInfo.getColumn("DEPT_ID"));
		tableCourseInfo.createNotNullConstraint(tableCourseInfo.getColumn("COURSE_ID"));

		Table tableOfficeInfo = this.createTable("OFFICE_INFO");
		tableOfficeInfo.createColumn("OFFICE_ID", new IntDataType());
		tableOfficeInfo.createColumn("OFFICE_NAME", new VarCharDataType(50));
		tableOfficeInfo.createColumn("HAS_PRINTER", new SmallIntDataType());
		tableOfficeInfo.createPrimaryKeyConstraint(tableOfficeInfo.getColumn("OFFICE_ID"));
		tableOfficeInfo.createNotNullConstraint(tableOfficeInfo.getColumn("OFFICE_ID"));

		Table tableRaceInfo = this.createTable("RACE_INFO");
		tableRaceInfo.createColumn("RACE_CODE", new IntDataType());
		tableRaceInfo.createColumn("RACE", new VarCharDataType(50));
		tableRaceInfo.createPrimaryKeyConstraint(tableRaceInfo.getColumn("RACE_CODE"));
		tableRaceInfo.createNotNullConstraint(tableRaceInfo.getColumn("RACE_CODE"));

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
		tableUserInfo.createPrimaryKeyConstraint(tableUserInfo.getColumn("USER_ID"));
		tableUserInfo.createForeignKeyConstraint(tableUserInfo.getColumn("DEPT_ID"), tableDeptInfo, tableUserInfo.getColumn("DEPT_ID"));
		tableUserInfo.createForeignKeyConstraint(tableUserInfo.getColumn("OFFICE_ID"), tableOfficeInfo, tableUserInfo.getColumn("OFFICE_ID"));
		tableUserInfo.createForeignKeyConstraint(tableUserInfo.getColumn("RACE"), tableRaceInfo, tableUserInfo.getColumn("RACE_CODE"));
		tableUserInfo.createNotNullConstraint(tableUserInfo.getColumn("USER_ID"));
		tableUserInfo.createNotNullConstraint(tableUserInfo.getColumn("PASSWORD"));

		Table tableTranscript = this.createTable("TRANSCRIPT");
		tableTranscript.createColumn("USER_ID", new VarCharDataType(50));
		tableTranscript.createColumn("COURSE_ID", new IntDataType());
		tableTranscript.createColumn("SCORE", new IntDataType());
		tableTranscript.createPrimaryKeyConstraint(tableTranscript.getColumn("USER_ID"), tableTranscript.getColumn("COURSE_ID"));
		tableTranscript.createForeignKeyConstraint(tableTranscript.getColumn("USER_ID"), tableUserInfo, tableTranscript.getColumn("USER_ID"));
		tableTranscript.createForeignKeyConstraint(tableTranscript.getColumn("COURSE_ID"), tableCourseInfo, tableTranscript.getColumn("COURSE_ID"));
		tableTranscript.createNotNullConstraint(tableTranscript.getColumn("USER_ID"));
		tableTranscript.createNotNullConstraint(tableTranscript.getColumn("COURSE_ID"));

		Table tableUnixCommand = this.createTable("UNIX_COMMAND");
		tableUnixCommand.createColumn("UNIX_COMMAND", new VarCharDataType(50));
		tableUnixCommand.createColumn("CATEGORY", new VarCharDataType(50));
		tableUnixCommand.createPrimaryKeyConstraint(tableUnixCommand.getColumn("UNIX_COMMAND"));
		tableUnixCommand.createNotNullConstraint(tableUnixCommand.getColumn("UNIX_COMMAND"));

		Table tableUsageHistory = this.createTable("USAGE_HISTORY");
		tableUsageHistory.createColumn("USER_ID", new VarCharDataType(50));
		tableUsageHistory.createColumn("SESSION_ID", new IntDataType());
		tableUsageHistory.createColumn("LINE_NO", new IntDataType());
		tableUsageHistory.createColumn("COMMAND_SEQ", new IntDataType());
		tableUsageHistory.createColumn("COMMAND", new VarCharDataType(50));
		tableUsageHistory.createForeignKeyConstraint(tableUsageHistory.getColumn("USER_ID"), tableUserInfo, tableUsageHistory.getColumn("USER_ID"));
		tableUsageHistory.createNotNullConstraint(tableUsageHistory.getColumn("USER_ID"));
	}
}

