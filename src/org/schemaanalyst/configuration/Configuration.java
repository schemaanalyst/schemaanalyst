package org.schemaanalyst.configuration;

import org.schemaanalyst.util.ReflectiveToString;
import plume.Option;

public class Configuration {

    @Option("the class to execute")
    public static String executionclass = "org.schemaanalyst.SchemaAnalyst";
    @Option("the unique id for this experiment")
    public static int uniqueid = -1;
    @Option("the current trial of an experiment campaign")
    public static int trial = -1;
    @Option("display debugging information?")
    public static boolean debug = true;
    @Option("display help information?")
    public static boolean help = false;
    @Option("write the data generation and mutation analysis scripts?")
    public static boolean script = true;
    @Option("write a mutation report in serialized form?")
    public static boolean wantmutationreport_mrp = true;
    @Option("write a mutation report in text form?")
    public static boolean wantmutationreport_txt = true;
    @Option("only write a summary of the mutation reports?")
    public static boolean onlymutationsummary = false;
    @Option("write a log created by P6spy?")
    public static boolean spy = false;
    @Option("set the root of the SchemaAnalyst project")
    public static String project = "";
    @Option("enforce the foreign keys (SQLite only)")
    public static boolean foreignkeys = true;
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
    @Option("set the name of the CREATE TABLE statement(s) used for testing purposes")
    public static String createtables = "casestudy.BankAccount.sql";
    @Option("set the name of the data generation script file")
    public static String scriptfile = "casestudy.BankAccount.sql";
    @Option("set the name of the mutation analysis script file")
    public static String mutantscriptfile = "casestudy.BankAccount.mutant.sql";
    @Option("set the name of the mutation report (serialized format)")
    public static String mutationreport_mrp = "casestudy.BankAccount.mrp";
    @Option("set the name of the mutation report (text format)")
    public static String mutationreport_txt = "casestudy.BankAccount.txt";
    @Option("set the name of the search")
    public static String datagenerator = "alternatingvalue_defaults";
    @Option("set the random profile")
    public static String randomprofile = "small";
    @Option("set the random seed")
    public static long randomseed = 0;
    @Option("set the maximum number of evaluations for the search")
    public static int maxevaluations = 100000;
    @Option("set the number of desired initial satisfying rows in a table -- (should be >= 2, so that primary keys and unique constraints can be properly tested)")
    public static int satisfyrows = 2;
    @Option("set the number of desired negating rows in a table")
    public static int negaterows = 1;
    @Option("set the number of desired rows in the table for naive random data generation")
    public static int naiverandom_rowspertable = 50;
    @Option("set the max number of tries for naive random data generation")
    public static int naiverandom_maxtriespertable = 1000;
    @Option("do you want to run SchemaAnalyst in stand-alone mode without running experiments?")
    public static boolean standalone = true;
    @Option("do you want to run SchemaAnalyst in stand-alone mode without running experiments?")
    public static int trialstart = 1;
    /**
     * only for DBMonster -- later, find a way to place in another Configuration
     * class!
     */
    @Option("set the name of the P6Spy log file that monitors DBMonster interactions")
    public static String spylog = "SpyLogs/dbmonster-spy.log";
    @Option("do you want to save the DBMonster insert statements for constraint coverage calculation?")
    public static boolean constraintcoverage = true;
    @Option("do you want transform empty strings to null values for DBMonster?")
    public static boolean transformemptystrings = false;
    /**
     * Post-Mutation2013 Experiment *
     */
    @Option("thread pool size")
    public static int threadpoolsize = 8;

    @Override
    public String toString() {
        return ReflectiveToString.toString(this);
    }
}