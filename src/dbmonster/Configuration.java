package dbmonster;

import plume.Option;

public class Configuration {

	@Option("display debugging information?")
	public static boolean debug = true;

	@Option("display help information?")
	public static boolean help = false;

	@Option("set the root of the DBATDG project")
	public static String project = "";

	@Option("set the name of the database server")
	public static String host = "localhost";

	@Option("set the port of the database server")
	public static int port = 5432;

	@Option("set the user name")
	public static String user = "gkapfham";

	@Option("set the password")
	public static String password = "postgres";

	@Option("set the type of the database")
        public static String type = "org.schemaanalyst.database.postgres.Postgres";

	@Option("set the name of the database")
	public static String database = "casestudy.BankAccount";

	@Option("pick the action to be performed during manipulation")
	public static String action = "dropalltables";
}