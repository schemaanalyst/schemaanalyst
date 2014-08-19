package org.schemaanalyst.mutation.analysis.executor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlwriter.ExpressionSQLWriter;
import org.schemaanalyst.sqlwriter.SQLWriter;
import static org.schemaanalyst.sqlwriter.SQLWriter.quoteIdentifier;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerationReport;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.IndentableStringBuilder;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class MutationAnalysisAlters extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    /**
     * The coverage criterion to use to generate data.
     */
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion = "CondAICC";
    /**
     * The data generator to use.
     */
    @Parameter("The data generator to use.")
    protected String dataGenerator = "avsDefaults";
    /**
     * The maximum fitness evaluations when generating data.
     */
    @Parameter("The maximum fitness evaluations when generating data.")
    protected int maxevaluations = 100000;
    /**
     * The random seed.
     */
    @Parameter("The random seed.")
    protected long randomseed = 0;
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithRemovers";
    /**
     * Whether to print live mutants.
     */
    @Parameter("Whether to print live mutants.")
    protected boolean printLive = false;
    /**
     * Which mutation analysis technique to use.
     */
    @Parameter("Which mutation analysis technique to use.")
    protected String technique = "original";
    /**
     * Whether to use transactions with this technique (if possible).
     */
    @Parameter("Whether to use transactions with this technique (if possible).")
    protected boolean useTransactions = false;
    /**
     * The instantiated schema.
     */
    protected Schema schema;
    /**
     * The instantiated DBMS.
     */
    protected DBMS dbms;
    /**
     * The writer for the DBMS.
     */
    protected SQLWriter sqlWriter;
    /**
     * The interactor for the DBMS.
     */
    protected DatabaseInteractor databaseInteractor;
    /**
     * The report produced when generating the test suite.
     */
    private TestSuiteGenerationReport generationReport;

    @Override
    protected void task() {
        // Instantiate fields from parameters
        instantiateParameters();

        // Start timing
        StopWatch totalTime = new StopWatch();
        StopWatch testGenerationTime = new StopWatch();
        StopWatch mutantGenerationTime = new StopWatch();
        StopWatch originalResultsTime = new StopWatch();
        StopWatch mutationAnalysisTime = new StopWatch();
        totalTime.start();

        // Generate test suite and mutants, apply mutation analysis technique
        final TestSuite suite = timedTask(new Callable<TestSuite>() {
            @Override
            public TestSuite call() throws Exception {
                return generateTestSuite();
            }
        }, testGenerationTime);
        final List<Mutant<Schema>> mutants = timedTask(new Callable<List<Mutant<Schema>>>() {
            @Override
            public List<Mutant<Schema>> call() throws Exception {
                return generateMutants();
            }
        }, mutantGenerationTime);
        final TestSuiteResult originalResults = timedTask(new Callable<TestSuiteResult>() {
            @Override
            public TestSuiteResult call() throws Exception {
                return executeTestSuite(schema, suite);
            }
        }, originalResultsTime);

        final Technique mutTechnique = instantiateTechnique(schema, mutants, suite, dbms, databaseInteractor);
        AnalysisResult analysisResult = timedTask(new Callable<AnalysisResult>() {
            @Override
            public AnalysisResult call() throws Exception {
                return mutTechnique.analyse(originalResults);
            }
        }, mutationAnalysisTime);

        // Stop timing
        totalTime.stop();

        // Write results
        CSVResult result = new CSVResult();
        result.addValue("dbms", databaseConfiguration.getDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("criterion", criterion);
        result.addValue("datagenerator", dataGenerator);
        result.addValue("coverage", generationReport.coverage());
        //TODO: Include the coverage according to the comparison coverage criterion
        result.addValue("evaluations", generationReport.getNumDataEvaluations(false));
        result.addValue("tests", suite.getTestCases().size());
        //TODO: Include the number of insert statements
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("scorenumerator", analysisResult.getKilled().size());
        result.addValue("scoredenominator", mutants.size());
        result.addValue("technique", "alters_" + technique);
        result.addValue("transactions", useTransactions);
        result.addValue("testgenerationtime", testGenerationTime.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationTime.getTime());
        result.addValue("originalresultstime", originalResultsTime.getTime());
        result.addValue("mutationanalysistime", mutationAnalysisTime.getTime());
        result.addValue("timetaken", totalTime.getTime());

        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);

        if (printLive) {
            for (Mutant<Schema> mutant : analysisResult.getLive()) {
                System.out.println("Alive: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
            }
        }
    }

    private static <T> T timedTask(Callable<T> callable, StopWatch watch) {
        try {
            watch.start();
            T result = callable.call();
            watch.stop();
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Instantiates the DBMS class, SQL writer and interactor.
     */
    private void instantiateParameters() {
        // Get the required DBMS class, writer and interactor
        dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        sqlWriter = dbms.getSQLWriter();
        databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        // Get the required schema class
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Technique instantiateTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        return new AltersTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }

    /**
     * Generates the test suite according to the algorithm and criterion.
     *
     * @return The test suite
     */
    private TestSuite generateTestSuite() {
        // Initialise from factories
        final DataGenerator dataGen = DataGeneratorFactory.instantiate(dataGenerator, randomseed, 100000);
        final TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schema, dbms).generateRequirements();

        // Filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // Construct generator
        final TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                testRequirements,
                dbms.getValueFactory(),
                dataGen
        );

        // Generate suite
        final TestSuite testSuite = generator.generate();
        generationReport = generator.getTestSuiteGenerationReport();
        //TODO: Include the coverage according to the comparison coverage criterion

        return testSuite;
    }

    /**
     * Generates mutants of the instantiated schema using the named pipeline.
     *
     * @return The mutants
     */
    private List<Mutant<Schema>> generateMutants() {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    private TestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        ConstraintlessSQLWriter tableWriter = new ConstraintlessSQLWriter();
        TestSuiteResult result = new TestSuiteResult();

        // Add the tables
        List<String> createStmts = tableWriter.writeCreateTableStatements(schema);
        for (String stmt : createStmts) {
            Integer stmtResult = databaseInteractor.executeUpdate(stmt);
            if (stmtResult < 0) {
                throw new CreateStatementException("Failed, result was: " + stmtResult, stmt);
            }
        }

        for (TestCase testCase : suite.getTestCases()) {
            // Insert the test data
            executeInserts(testCase.getState());
            executeInserts(testCase.getData());

            // Add the constraints
            executeAlters(schema, testCase, result);

            // Drop the constraints
            executeDropAlters(schema);

            // Delete the test data
            executeDeletes(schema);
        }

        // Drop the tables
        List<String> dropStmts = tableWriter.writeDropTableStatements(schema, true);
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }

        return result;
    }

    private void executeInserts(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : schema.getTablesInOrder()) {
            if (stateTables.contains(table)) {
                List<Row> rows = data.getRows(table);
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    databaseInteractor.executeUpdate(statement);
                }
            }
        }
    }

    private void executeAlters(Schema schema, TestCase testCase, TestSuiteResult result) {
        ConstraintlessSQLWriter constraintWriter = new ConstraintlessSQLWriter();
        List<String> alterStmts = constraintWriter.writeAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            Integer res = databaseInteractor.executeUpdate(stmt);
            if (res != 0) {
                result.add(testCase, new TestCaseResult(new StatementException("Failed, result was: " + res, stmt)));
            } else {
                result.add(testCase, TestCaseResult.SuccessfulTestCaseResult);
            }
        }
    }

    private void executeDeletes(Schema schema) {
        for (String stmt : sqlWriter.writeDeleteFromTableStatements(schema)) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    private void executeDropAlters(Schema schema) {
        ConstraintlessSQLWriter constraintWriter = new ConstraintlessSQLWriter();
        List<String> alterStmts = constraintWriter.writeDropAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new MutationAnalysisAlters().run(args);
    }

    private class AltersTechnique extends Technique {

        public AltersTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
            super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
        }

        @Override
        public AnalysisResult analyse(TestSuiteResult originalResults) {
            AnalysisResult result = new AnalysisResult();
            ConstraintlessSQLWriter tableWriter = new ConstraintlessSQLWriter();

            // Setup the map of results
            Map<Mutant<Schema>, TestSuiteResult> resultMap = new HashMap<>();
            for (Mutant<Schema> mutant : mutants) {
                resultMap.put(mutant, new TestSuiteResult());
            }

            // Add the tables
            List<String> createStmts = tableWriter.writeCreateTableStatements(schema);
            for (String stmt : createStmts) {
                Integer stmtResult = databaseInteractor.executeUpdate(stmt);
                if (stmtResult < 0) {
                    throw new CreateStatementException("Failed, result was: " + stmtResult, stmt);
                }
            }

            // Insert the test data
            for (TestCase testCase : testSuite.getTestCases()) {
                // Insert the test data
                executeInserts(testCase.getState());
                executeInserts(testCase.getData());

                for (Mutant<Schema> mutant : mutants) {
                    // Add the constraints
                    executeAlters(mutant.getMutatedArtefact(), testCase, resultMap.get(mutant));

                    // Drop the constraints
                    executeDropAlters(mutant.getMutatedArtefact());
                }

                // Delete the test data
                executeDeletes(schema);
            }

            // Drop the tables
            List<String> dropStmts = tableWriter.writeDropTableStatements(schema, true);
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
            
            // Compare results
            for (Map.Entry<Mutant<Schema>, TestSuiteResult> entry : resultMap.entrySet()) {
                System.out.println("-----");
                System.out.println("Original:");
                System.out.println(originalResults);
                System.out.println("Mutant:");
                System.out.println(entry.getValue());
                if (entry.getValue().equals(originalResults)) {
                    result.addLive(entry.getKey());
                } else {
                    result.addKilled(entry.getKey());
                }
            }

            return result;
        }

    }

    /**
     * An SQLWriter that omits constraints, which can be separately produced as
     * ALTER TABLE statements.
     */
    private class ConstraintlessSQLWriter extends SQLWriter {

        AlterTableConstraintWriter constraintWriter = new AlterTableConstraintWriter();

        @Override
        public String writeCreateTableStatement(Schema schema, Table table) {
            IndentableStringBuilder sql = new IndentableStringBuilder();
            sql.append("CREATE TABLE ");
            sql.append(quoteIdentifier(table.getName()));
            sql.appendln(" (");

            boolean first = true;
            for (Column column : table.getColumns()) {
                if (first) {
                    first = false;
                } else {
                    sql.appendln(0, ",");
                }

                // write column name
                sql.append(1, quoteIdentifier(column.getName()));

                // write column type			
                sql.appendTabbed(dataTypeSQLWriter.writeDataType(column));
            }

            sql.appendln(0);
            sql.append(")");
            return sql.toString();
        }

        /**
         * Write the alter table statements for one table in a schema
         *
         * @param schema The schema
         * @param table The table
         * @return The alter statements
         */
        public List<String> writeAlterTableStatements(Schema schema, Table table) {
            List<String> stmts = new ArrayList<>();
            for (Constraint constraint : schema.getConstraints(table)) {
                stmts.add("ALTER TABLE " + table.getName() + " " + constraintWriter.writeConstraint(constraint));
            }
            return stmts;
        }

        /**
         * Write the alter table statements for all tables in a schema
         *
         * @param schema The schema
         * @return The alter statements
         */
        public List<String> writeAlterTableStatements(Schema schema) {
            List<String> stmts = new ArrayList<>();
            for (Table table : schema.getTables()) {
                stmts.addAll(writeAlterTableStatements(schema, table));
            }
            return stmts;
        }

        /**
         * Write the alter table statements for one table in a schema
         *
         * @param schema The schema
         * @param table The table
         * @return The alter statements
         */
        public List<String> writeDropAlterTableStatements(Schema schema, Table table) {
            List<String> stmts = new ArrayList<>();
            for (Constraint constraint : schema.getConstraints(table)) {
                for (String stmt : constraintWriter.writeDropConstraint(constraint)) {
                    stmts.add("ALTER TABLE " + table.getName() + " " + stmt);
                }
            }
            return stmts;
        }

        /**
         * Write the alter table statements for all tables in a schema
         *
         * @param schema The schema
         * @return The alter statements
         */
        public List<String> writeDropAlterTableStatements(Schema schema) {
            List<String> stmts = new ArrayList<>();
            for (Table table : schema.getTables()) {
                stmts.addAll(writeDropAlterTableStatements(schema, table));
            }
            return stmts;
        }
    }

    /**
     * A constraint writer that produces ALTER TABLE statements for each
     * constraint.
     */
    private class AlterTableConstraintWriter {

        protected ExpressionSQLWriter expressionSQLWriter = new ExpressionSQLWriter();
        MessageDigest instance = null;

        public String writeConstraint(Constraint constraint) {

            class ConstraintSQLWriterVisitor implements ConstraintVisitor {

                String sql;

                public String writeConstraint(Constraint constraint) {
                    sql = "";
                    constraint.accept(this);
                    return sql;
                }

                @Override
                public void visit(CheckConstraint constraint) {
                    sql = writeCheck(constraint);
                }

                @Override
                public void visit(ForeignKeyConstraint constraint) {
                    sql = writeForeignKey(constraint);
                }

                @Override
                public void visit(NotNullConstraint constraint) {
                    sql = writeNotNull(constraint);
                }

                @Override
                public void visit(PrimaryKeyConstraint constraint) {
                    sql = writePrimaryKey(constraint);
                }

                @Override
                public void visit(UniqueConstraint constraint) {
                    sql = writeUnique(constraint);
                }

            }
            return (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
        }

        public String writeCheck(CheckConstraint check) {
            String name = getConstraintName(check);
            return "ADD CONSTRAINT " + name + " CHECK (" + expressionSQLWriter.writeExpression(check.getExpression()) + ")";
        }

        public String writeForeignKey(ForeignKeyConstraint foreignKey) {
            String name = getConstraintName(foreignKey);
            String sql = "ADD CONSTRAINT " + name + " FOREIGN KEY (";
            sql += SQLWriter.writeColumnList(foreignKey.getColumns());
            sql += ") REFERENCES " + foreignKey.getReferenceTable().getName();
            sql += "(" + SQLWriter.writeColumnList(foreignKey.getReferenceColumns()) + ")";
            return sql;
        }

        public String writeNotNull(NotNullConstraint notNull) {
            return "ALTER " + notNull.getColumn().getName() + " SET NOT NULL";
        }

        public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
            String name = getConstraintName(primaryKey);
            return "ADD CONSTRAINT " + name + " PRIMARY KEY (" + SQLWriter.writeColumnList(primaryKey.getColumns()) + ")";
        }

        public String writeUnique(UniqueConstraint unique) {
            String name = getConstraintName(unique);
            return "ADD CONSTRAINT " + name + " UNIQUE (" + SQLWriter.writeColumnList(unique.getColumns()) + ")";
        }

        public List<String> writeDropConstraint(Constraint constraint) {

            class ConstraintSQLWriterVisitor implements ConstraintVisitor {

                List<String> sql;

                public List<String> writeConstraint(Constraint constraint) {
                    sql = new ArrayList<>();
                    constraint.accept(this);
                    return sql;
                }

                @Override
                public void visit(CheckConstraint constraint) {
                    sql.add(writeDropCheck(constraint));
                }

                @Override
                public void visit(ForeignKeyConstraint constraint) {
                    sql.add(writeDropForeignKey(constraint));
                }

                @Override
                public void visit(NotNullConstraint constraint) {
                    sql.add(writeDropNotNull(constraint));
                }

                @Override
                public void visit(PrimaryKeyConstraint constraint) {
                    sql.add(writeDropPrimaryKey(constraint));
                    for (Column column : constraint.getColumns()) {
                        sql.add(writeDropNotNull(column.getName()));
                    }
                    
                }

                @Override
                public void visit(UniqueConstraint constraint) {
                    sql.add(writeDropUnique(constraint));
                }

            }

            return (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
        }

        private String writeDropCheck(CheckConstraint constraint) {
            return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
        }

        private String writeDropForeignKey(ForeignKeyConstraint constraint) {
            return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
        }

        private String writeDropNotNull(NotNullConstraint constraint) {
            return writeDropNotNull(constraint.getColumn().getName());
        }
        
        private String writeDropNotNull(String column) {
            String sql = "ALTER COLUMN " + column;
            sql += " DROP NOT NULL";
            return sql;
        }

        private String writeDropPrimaryKey(PrimaryKeyConstraint constraint) {
            return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
        }

        private String writeDropUnique(UniqueConstraint constraint) {
            return "DROP CONSTRAINT IF EXISTS " + getConstraintName(constraint);
        }

        private String getConstraintName(Constraint constraint) {
            try {
                if (instance == null) {
                    instance = MessageDigest.getInstance("md5");
                }
                byte[] digest = instance.digest(constraint.toString().getBytes());
                return "constraint" + new BigInteger(1, digest).toString(16);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
