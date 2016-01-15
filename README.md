# SchemaAnalyst Manual

# Description
There has been little work that has sought to test that a relational
database's schema has correctly specified integrity constraints
[\[1\]](#one).
Testing a database's schema verifies that _all_ integrity constraints are
satisfied, confirming the integrity and security of a database.
Early testing not only verifies that integrity constraints are
satisfied, but it can reduce implementation and maintenance
costs associated with managing a database.

SchemaAnalyst uses a search-based approach
to test the complex relationships of integrity constraints in relational
databases. Other schema-analyzing tools test a
database's schema in a less efficient method but, more importantly,
use a less effective technique. A study in
[this paper](http://www.cs.allegheny.edu/~gkapfham/research/publish/kapfhammer-icst2013-schemaanalyst.pdf) finds that, for all of the case studies,
SchemaAnalyst obtains higher constraint coverage than
a similar schema-analyzing tool while reaching 100% coverage on two schemas
for which the competing tool covers less than 10% of the constraints.
SchemaAnalyst achieves these results with generated data
sets that are substantially smaller than the competing tool and in
an amount of execution time that is competitive or faster [\[2\]](#two).

# Table of Contents <a name="table-of-contents"></a>
+ [Overview](#overview)
+ [Getting Started](#getting-started)
	- [Downloading](#downloading)
	- [Dependencies](#dependencies)
	- [Configuring](#configuring)
	- [Compiling](#compiling)
	- [Testing](#testing)
+ [Tutorial](#tutorial)
	- [Asciicinema Recording](#asciicinema)
	- [Help Menu](#help)
	- [Mutation Analysis](#mutation-analysis)
		* [Syntax](#mutation-analysis-syntax)
		* [Parameters](#mutation-analysis-parameters)
		* [Output](#mutation-analysis-output)
		* [Interpretation](#mutation-analysis-interpretation)
		* [Examples](#mutation-analysis-examples)
	- [Mutant Analysis](#mutant-analysis)
		* [Syntax](#mutant-analysis-syntax)
		* [Parameters](#mutant-analysis-parameters)
		* [Output](#mutant-analysis-output)
		* [Example and Interpretation](#mutant-analysis-example)
	- [Test Data Generation](#test-data-generation)
		* [Syntax](#test-data-generation-syntax)
		* [Parameters](#test-data-generation-parameters)
		* [Output](#test-data-generation-output)
		* [Example](#test-data-generation-example)
+ [Building and Execution Environment](#environment)
+ [Publications](#publications)
+ [General SQL Parser](#sqlparser)
+ [License](#license)

# Overview <a name="overview"></a>
A database schema acts as the cornerstone for any application that relies on a RDBMS.  It specifies the types of allowed data, as well as the organization and relationship between the data.  Any oversight at this fundamental level can easily propagate errors toward future development stages.  Such oversights might include incomplete foreign or primary key declarations, or incorrect use or omission of the `UNIQUE`, `NOT NULL`, and `CHECK` integrity constraints.  Such seemingly small mistakes at this stage can prove costly to correct, thus SchemaAnalyst was created to allow for early detection of such problems prior to integration of a schema with an application.  Ultimately, SchemaAnalyst meticulously tests the correctness of a schema: it ensures that valid data is permitted entry into a database and that erroneous data is rejected.

To do this, various "mutants" are created from a given schema using a defined set of mutation operators.  These operators change the schema's integrity constraints in different ways.  For instance, a mutant may be created by removing a column from a primary key, or from removing a `NOT_NULL` constraint from a column, among many other possibilities.  These schemas are then evaluated through a process known as mutation analysis.  Using a search-based technique described in [this paper](http://www.cs.allegheny.edu/sites/gkapfham/download/research/papers/icst2013-kapfhammer-mcminn-wright.pdf), test suites are created that execute `INSERT` statements into tables for both the original schema and the mutant schema.  If the `INSERT` statement is accepted by the original schema but rejected by the mutant schema, then it shows that the inserted data adheres to the integrity constraints of the original schema, and the test suite is able to detect and respond appropriately to the change.  This is said to "kill" the mutant, and after all mutants have been analyzed in this fashion, a mutation score is generated as follows: mutation score = number of killed mutants / number of mutants.  In general, the higher this score the better the robustness of the schema being tested; i.e. it is more likely to only accept valid data and reject invalid data.

###### Example <a name="overview-example"></a>
The following presents a specific example of mutation analysis using the open-source [`UnixUsage`](http://sourceforge.net/projects/se549unixusage/) schema, and further described in [this paper](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/paper-mutation2013/).  Consider this section of the schema:

```
CREATE TABLE USER_INFO (
USER_ID VARCHAR(50) NOT NULL PRIMARY KEY,
...
);
CREATE TABLE USAGE_HISTORY (
USER_ID VARCHAR(50) NOT NULL,
SESSION_ID INTEGER,
LINE_NO INTEGER,
COMMAND_SEQ INTEGER,
COMMAND VARCHAR(50),
*PRIMARY KEY (USER_ID)*
FOREIGN KEY (USER_ID)
REFERENCES USER_INFO (USER_ID)
);
CREATE TABLE UNIX_COMMAND (...);
CREATE TABLE TRANSCRIPT (...);
CREATE TABLE RACE_INFO (...);
CREATE TABLE OFFICE_INFO (...);
CREATE TABLE DEPT_INFO (...);
CREATE TABLE COURSE_INFO (...);
```

Note that, in this example, the `USAGE_HISTORY` table has been mutated by adding in a new primary key integrity constraint, emphasized here using asterisks ("*").  This mutation of the schema requires all entries for the `USAGE_HISTORY` table to have a unique `USER_ID` field.  SchemaAnalyst can then create a test suite to generate `INSERT` statements for the mutant and original schema.  For instance:

```
INSERT INTO USER_INFO
VALUES ('laura', ...)
INSERT INTO USAGE_HISTORY
VALUES ('laura', 1, 10, 1, 'awk')
INSERT INTO USAGE_HISTORY
VALUES ('laura', 2, 10, 2, 'grep')
```

This test suite would successfully kill the particular mutant described earlier.  All of the values would be successfully inserted into the original schema, but the mutated schema would reject the second `INSERT` into `USAGE_HISTORY`, since the extra `USER_ID` primary key would prevent a duplicate entry of 'laura.'

See the linked papers in the [Publications](#publications) section for detailed descriptions of the methods, empirical studies and results for this system and comparisons to similar systems.

[^^^ To Top ^^^](#table-of-contents)

---

# Getting Started <a name="getting-started"></a>

### Downloading <a name="downloading"></a>
The source code is hosted in a [GitHub repository] (https://github.com/schemaanalyst-team/schemaanalyst). To obtain SchemaAnalyst, simply clone this repository on your machine using the following command:

`git clone git@github.com:schemaanalyst-team/schemaanalyst.git`

Alternatively, you may download a compressed copy of this repository by clicking [here](https://github.com/schemaanalyst-team/schemaanalyst/archive/master.zip).  Once downloaded, simply extract the contents to a location of your choice.

*Note: The repository may take a short time to download due to the size of the libraries included.*

[^^^ To Top ^^^](#table-of-contents)

### Dependencies <a name="dependencies"></a>
To use SchemaAnalyst, Java 1.7 JDK (or higher) must be installed to run any of the Java programs, along with Apache Ant to compile the system before actual use.  See the table below for a full description of the required and optional dependencies.

| Software | Required? | Purpose |
|:--------:|:---------:|:-------:|
| [Java 1.7 JDK (or higher)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) | X | Running the system |
| [Apache Ant](http://ant.apache.org) | X | Compiling the system |
| [JUnit](https://github.com/junit-team/junit/wiki/Download-and-Install) |  | Testing the system |
| [PostgreSQL](http://www.postgresql.org/download) |  | Using Postgres with selected schema |
| [SQLite](http://www.sqlite.org/download.html) |  | Using SQLite with selected schema |
| [HSQLDB](http://hsqldb.org/) |  | Using HyperSQL with selected schema |

Additional `.jar` library files should also have been included with SchemaAnalyst, found in the `lib` directory.

[^^^ To Top ^^^](#table-of-contents)

### Configuring <a name="configuring"></a>

###### Properties <a name="properties"></a>

SchemaAnalyst uses a number of _properties_ files to specify some configuration options. These are located in the `config` directory. These files are structured as follows:

* `database.properties`: contains properties relating to database connections, such as usernames and passwords. The `dbms` property at the top of this file specifies which database to use (SQLite, Postgres, or HyperSQL).
* `locations.properties`: specifies the layout of the SchemaAnalyst directories, and should not require any changes (but may be useful if adding to the Ant script, which automatically loads it).
* `experiment.properties`: _The contents of this file can be ignored._
* `logging.properties`: specifies the level of logging output that should be produced. Changing the `.level` and `java.util.logging.ConsoleHandler.level` options allows the level to be altered. Note that unless you enable logging to a file, effectively the lower of the two levels is used.

*__Note__: To allow you to specify your own _local_ versions of these files, which you will not commit to the Git repository, SchemaAnalyst runners will automatically load versions suffixed with `.local` over those without the suffix. If you need to change any of the properties, you should therefore create your own local version by copying the file and adding the suffix (e.g. `database.properties` becomes `database.properties.local`).*

###### Databases <a name="databases"></a>
HSQLDB and SQLite require no additional configuration for use with SchemaAnalyst.  If using PostgreSQL, then note that the `database.properties` file is preconfigured to connect to a PostgreSQL database with the following credentials:

	Username: user
	Password: pass

In addition, you must give this user full privileges over the `postgres` database.

[^^^ To Top ^^^](#table-of-contents)

### Compiling <a name="compiling"></a>
The SchemaAnalyst tool is built using Ant and the `build.xml` configuration file, according to the following steps:

1. Open a terminal/command prompt and navigate to the main `schemaanalyst` folder.
2. Run the `ant compile` command to invoke the Java compiler.
3. After a short time, the message `BUILD SUCCESSFUL` should be displayed, as below:

```
C:\schemaanalyst> ant compile
Buildfile: C:\schemaanalyst\build.xml

compile:
    [mkdir] Created dir: C:\schemaanalyst\build
    [javac] Compiling 672 source files to C:\schemaanalyst\build
    [javac] Note: Some input files use unchecked or unsafe operations.
    [javac] Note: Recompile with -Xlint:unchecked for details.

BUILD SUCCESSFUL
Total time: 10 seconds
```

*__Note:__ The message `Some input files use unchecked or unsafe operations` may be ignored if it appears during compilation.*

[^^^ To Top ^^^](#table-of-contents)

### Testing <a name="testing"></a>
To confirm that the code has properly compiled, you should be able to run the test suite with `ant test`, which should complete with no failures or errors.

```
C:\schemaanalyst>ant test
Buildfile: C:\schemaanalyst\build.xml

compile:

test:
    [junit] Testsuite: org.schemaanalyst.unittest.AllTests
    [junit] Tests run: 836, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.947 sec
    ...
    BUILD SUCCESSFUL
Total time: 3 seconds
```

*__Note:__ this assumes that all three DBMS (HyperSQL, SQLite, and Postgres) are accessible.  If they are not, then any tests related to the unavailable databases may fail by default.  Please refer to the [Dependencies](#dependencies) section for links to download and install these DBMS.*

[^^^ To Top ^^^](#table-of-contents)

---

# Tutorial <a name="tutorial"></a>

### Asciinema Recording <a name="asciicinema"></a>

Please watch this Asciicinema recording that shows some of the key features of SchemaAnalyst, which are explained in more detail below.

[![asciicast](https://asciinema.org/a/5qttak0iqg1db1r38bdfyviod.png)](https://asciinema.org/a/5qttak0iqg1db1r38bdfyviod)

---

### Help Menu <a name="help"></a>
You are able to print the help menu at any time with the `--help`, or `-h` command of the `Go` class within the `java org.schemaanalyst.util` package as follows:

Open a terminal window in the main `schemaanalyst` folder and tye:

`java org.schemaanalyst.util.Go -h`

Which produces the following console output:

```
Usage: <main class> [options]
  Options:
    --criterion, -c
       Coverage Criterion
       Default: ICC
    --generator, -g, --dataGenerator
       Data Generation Algorithm
       Default: avsDefaults
    --dbms, -d, --database
       Database Management System
       Default: SQLite
    --help, -h
       Prints this help menu
       Default: false
    --sql, --inserts
       Target file for writing INSERT statements
    --mutation, -m
       Perform mutation testing
       Default: false
  * --schema, -s
       Target Schema
       Default: <empty string>
    --testSuite, -t
       Target file for writing JUnit test suite
    --testSuitePackage, -p
       Target package for writing JUnit test suite
       Default: generatedtestcd .
```

### Mutation Analysis <a name="mutation-analysis"></a>

###### Syntax <a name="mutation-analysis-syntax"></a>

To create data to exercise the integrity constraints of a schema using the data generation component of SchemaAnalyst, and then perform mutation analysis using it, the `MutationAnalysis` class from the `org.schemaanalyst.mutation.analysis.executor` package can be used as follows:

Open a terminal window in the main `schemaanalyst` folder and type:

`java -cp build:lib/* org.schemaanalyst.mutation.analysis.executor.MutationAnalysis casestudy <options>`

Where `casestudy` is replaced with the path to the parsed case study (i.e. the schema of interest), and `<options>` can be replaced by any number of parameters described below.

###### Parameters <a name="mutation-analysis-parameters"></a>

| Parameter | Required | Description |
|:---------:|:--------:|:-----------:|
| casestudy | X | The class name of the schema to use, which has been parsed into the SchemaAnalyst intermediate representation.|
| criterion |   | The coverage criterion to use to generate data.|
| dataGenerator |  | The data generator to use to produce SQL INSERT statements.|
| maxevaluations |  | The maximum fitness evaluations for the search algorithm to use.|
| randomseed |  | The seed used to produce random values for the data generation process.|
| mutationPipeline |  | The mutation pipeline to use to produce and, optionally, remove mutants.|
| technique |  | The mutation technique to use (e.g., original, fullSchemata, minimalSchemata).|
| useTransactions |  | Whether to use SQL transactions to improve the performance of a technique, if possible.|

*__Note:__ If you attempt to execute any of the `Runner` classes of SchemaAnalyst without the necessary parameters, or if you type the `--help` tag, you should be presented with information describing the parameters and detailing which of these are required. Where parameters are not required, the defaults values should usually be sensible.  While there are other parameters available for this class, it is generally not necessary to understand their purpose.*

###### Output <a name="mutation-analysis-output"></a>
Executing this class produces a single results file in CSV format that contains one line per execution, located at `results/newmutationanalysis.dat`. This contains a number of columns:

| Column | Description |
|:------:|:-----------:|
| dbms | The DBMS.|
| casestudy | The schema.|
| criterion | The integrity constraint coverage criterion.|
| datagenerator | The data generation algorithm.|
| randomseed | The value used to seed the pseudo-random number generator.|
| coverage | The level of coverage the produced data achieves according to the criterion.|
| evaluations | The number of fitness evaluations used by the search algorithm.|
| tests | The number of test cases in the produced test suite.|
| mutationpipeline | The mutation pipeline used to generate mutants.|
| scorenumerator | The number of mutants killed by the generated data.|
| scoredenominator | The total number of mutants used for mutation analysis.|
| technique | The mutation analysis technique.|
| transactions | Whether SQL transactions were applied, if possible.|
| testgenerationtime | The time taken to generate test data in milliseconds.|
| mutantgenerationtime | The time taken to generate mutants in milliseconds.|
| originalresultstime | The time taken to execute the test suite against the non-mutated schema.|
| mutationanalysistime | The time taken to perform analysis of all of the mutant schemas.|
| timetaken | The total time taken by the entire process.|

If it is necessary to retrieve information about the execution time for each mutant individually, the `MutantTiming` technique is provided. This is a specialised version of the `Original` technique that is instrumented to record this additional information.

###### Intepretation <a name="mutation-analysis-interpretation"></a>
The output produced by mutation analysis contains a significant amount of information, some of which might not be needed for your purposes.  If you are simply concerned with the correctness of your schema, focus on the `scorenumerator` and `scoredenominator` columns, as defined previously.  By dividing the numerator by the denominator you will generate a mutation score in the range [0, 1].  This score provides an estimate for how well the schema has performed when its integrity constraints were exercised, with higher scores indicating that the schema is more likely to permit valid data from entering a table and to reject any invalid data.  Although there does not currently exist a standard for this metric, scores between 0.6 - 0.7 (60% - 70%) are generally considered good.  If your schema's score falls below this, consider viewing the [Mutant Analysis](#mutant-analysis) section to gain further insight on the types of mutants created and removed during the process.

###### Examples <a name="mutation-analysis-examples"></a>

1.  Perform mutation analysis with the default configuration and the `ArtistSimilarity` schema:

	`java -cp build:lib/* org.schemaanalyst.mutation.analysis.executor.MutationAnalysis parsedcasestudy.ArtistSimilarity`

	Which produces the following data in the `results/newmutationanalysis.dat` file:

	```
	dbms,casestudy,criterion,datagenerator,randomseed,testsuitefile,coverage,evaluations,tests,mutationpipeline,scorenumerator,scoredenominator,technique,transactions,testgenerationtime,mutantgenerationtime,originalresultstime,mutationanalysistime,timetaken
	SQLite,parsedcasestudy.ArtistSimilarity,CondAICC,avsDefaults,0,NA,100.0,22,9,AllOperatorsWithRemovers,5,9,original,false,259,67,5,31,371
	```

2.  Perform mutation analysis with a random seed of `1000`, the `ClauseAICC` coverage criterion, the `random` data generator and the `ArtistSimilarity` schema:

	`java -cp build:lib/* org.schemaanalyst.mutation.analysis.executor.MutationAnalysis parsedcasestudy.ArtistSimilarity --randomseed=1000 --criterion=ClauseAICC --dataGenerator=random`

	Which produces the following data in the `results/newmutationanalysis.dat` file:

	```
	dbms,casestudy,criterion,datagenerator,randomseed,testsuitefile,coverage,evaluations,tests,mutationpipeline,scorenumerator,scoredenominator,technique,transactions,testgenerationtime,mutantgenerationtime,originalresultstime,mutationanalysistime,timetaken
SQLite,parsedcasestudy.ArtistSimilarity,ClauseAICC,random,1000,NA,88.88888888888889,133786,8,AllOperatorsWithRemovers,5,9,original,false,8749,61,4,20,8844
	```

[^^^ To Top ^^^](#table-of-contents)

---

### Mutant Analysis <a name="mutant-analysis"></a>

###### Syntax <a name="mutant-analysis-syntax"></a>
To avoid the cost of running the entire mutation analysis process to measure the number of mutants produced by a particular set of operators, or how many are discarded by the ineffective mutant removal process, the mutation framework includes functionality to generate mutants without executing them.

This is implemented in the `AnalysePipeline` class within the `org.schemaanalyst.mutation.analysis.util` package as follows:

Open a terminal window in the main `schemaanalyst` folder and type:

`java -cp build:lib/* org.schemaanalyst.mutation.analysis.util.AnalysePipeline casestudy dbms <options>`

Where `casestudy` is replaced with the path to the parsed case study (i.e. the schema of interest), `dbms` is replaced with one of (SQLite, Postgres, HyperSQL), and `<options>` can be replaced by any of the other parameters described below.

###### Parameters <a name="mutant-analysis-parameters"></a>
| Parameter | Required | Description |
|:---------:|:--------:|:-----------:|
| casestudy | X | The class name of the schema to use, which has been parsed into the SchemaAnalyst intermediate representation. |
| dbms | X | The DBMS to use. This should match the DBMS name in the `database.properties` file. |
| mutationPipeline | | The mutation pipeline to use to generate mutants. |
| outputfolder | | The directory to output results into (default: as specified in configuration file). |

*__Note:__ If you attempt to execute any of the `Runner` classes of SchemaAnalyst without the necessary parameters, or if you type the `--help` tag, you should be presented with information describing the parameters and detailing which of these are required. Where parameters are not required, the defaults values should usually be sensible.*

###### Output <a name="mutant-analysis-output"></a>
Executing this class produces a single results file in CSV format that details the number of mutants produced for each operator, removed by each ineffective mutant removal phase and the overall number of effective mutants remaining for each mutation operator.  It will be located at `results/analysepipeline.dat`. This contains a number of columns:

| Column | Description |
|:------:|:-----------:|
|dbms|The DBMS|
|casestudy|The schema|
|pipeline|The mutation pipeline used to generate mutants|
|type|Indicate if mutant produced, retained, or removed|
|operator|The mutation operator used on the schema|
|count|The number of mutants produced, retained, or removed|
|timetaken|The time taken to process the mutant|

###### Example and Interpretation <a name="mutant-analysis-example"></a>

Run mutant analysis for the default mutation pipeline, the `ArtistSimilarity` schema and the `Postgres` database:

`java -cp build:lib/* org.schemaanalyst.mutation.analysis.util.AnalysePipeline parsedcasestudy.ArtistSimilarity Postgres`

The following shows partial output from the above process.  The content has been adapted to fit a table for clarity:

|     dbms      |  casestudy                         |  pipeline                  |  type      |  operator                 |  count  |  timetaken |
|-----------------|------------------------------------|----------------------------|------------|---------------------------|---------|------------|
|       Postgres  |  parsedcasestudy.ArtistSimilarity  |  AllOperatorsWithRemovers  |  produced  |  NNCA                     | 3 | 1 |
|       Postgres  |  parsedcasestudy.ArtistSimilarity  |  AllOperatorsWithRemovers  |  removed   |  EquivalentMutantRemover  | 1 | 27 |
|       Postgres  |  parsedcasestudy.ArtistSimilarity  |  AllOperatorsWithRemovers  |  retained  |  NNCA                     | 2 |  NA |

The first three columns specify the DBMS, case study (or schema) and pipeline used, respectively -- `Postgres`, `ArtistSimilarity` and `AllOperatorsWithRemovers` in this case. The type column describes what sort of data is listed in the given column, which must be `produced`, `removed` or `retained` (the number of mutants remaining after removal). Next, the class name of the mutation operator or remover applied is listed -- in this case, the `NNCA` (`NOT NULL` column addition) was used to create three mutants, one of which was removed for being equivalent, leaving two mutants remaining. The final column lists the time taken in milliseconds, for either production or removal.

[^^^ To Top ^^^](#table-of-contents)

---

### Test Data Generation <a name="test-data-generation"></a>

###### Syntax <a name="test-data-generation-syntax"></a>
SchemaAnalyst will create a series of `INSERT` statements to test the integrity constraints that are altered via mutation, as described in the [Overview](#overview) section.  This data is typically hidden from the user, but if you wish to see what data the system is using for this process, then you can do the following:

Open a terminal window in the main `schemaanalyst` folder and type:

`java -cp build:lib/* org.schemaanalyst.testgeneration.tool.PrintTestSuite casestudy dbms coverageCriterion dataGenerator <options>`

Where `casestudy` is replaced with the path to the parsed case study (i.e. the schema of interest), `dbms` is replaced with one of (SQLite, Postgres, HyperSQL), and `<options>` can be replaced by any of the other parameters described below.

###### Parameters <a name="test-data-generation-parameters"></a>
| Parameter | Required | Description |
|:---------:|:--------:|:-----------:|
| dbms | X | The DBMS|
| casestudy | X | The class name of the schema to use, which has been parsed into the SchemaAnalyst intermediate representation.|
| criterion | X  | The coverage criterion to use to generate data.|
| dataGenerator | X | The data generator to use to produce SQL INSERT statements.|
| maxevaluations |  | The maximum fitness evaluations for the search algorithm to use.|
| randomseed |  | The seed used to produce random values for the data generation process.|

###### Output <a name="test-data-generation-output"></a>
The created `INSERT` statements are saved as a `.java` file in the `generatedtest` directory.  These statements are also automatically displayed in the console window after execution.  See the example below for the output from a specific schema.

###### Example <a name="test-data-generation-example"></a>
Generate test data for the ArtistSimilarity schema using the `Postgres` database, the `UCC` coverage criterion, and the `avsDefaults` dataGenerator:

`java -cp build:lib/* org.schemaanalyst.testgeneration.tool.PrintTestSuite parsedcasestudy.ArtistSimilarity Postgres UCC avsDefaults`

This will produce a series of `INSERT` statements for each mutant of the schema.  Some abbreviated output from the above execution is included below:

```
------------------------------
Test case 1

Test requirement:
(Match[?[artists: artist_id]] ? ¬Null[artists: artist_id])

Insert statements:
INSERT INTO "artists"(
	"artist_id"
) VALUES (
	''
)

INSERT INTO "artists"(
	"artist_id"
) VALUES (
	'a'
)
...
```

[^^^ To Top ^^^](#table-of-contents)

---

# Building and Execution Environment <a name="environment"></a>

TODO: Need to put specs of Alden Linux comp here

[^^^ To Top ^^^](#table-of-contents)

---

# Publications

[1. ](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/paper-seke2015/)Kinneer, Cody, Gregory M. Kapfhammer, Chris J. Wright, and Phil McMinn (2015). "Automatically evaluating the efficiency of search-based test data generation for relational database schemas," in Proceedings of the 27th International Conference on Software Engineering and Knowledge Engineering.

[2. ](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/paper-qsic2014a/)Wright, Chris J., Gregory M. Kapfhammer, and Phil McMinn (2014). "The impact of equivalent,
redundant, and quasi mutants on database schema mutation analysis," in Proceedings of the
14th International Conference on Quality Software.

[3. ](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/paper-icst2013/)Kapfhammer, Gregory M., Phil McMinn, and Chris J. Wright (2013). "Search-based testing of
relational schema integrity constraints across multiple database management systems," in Pro-
ceedings of the 6th International Conference on Software Testing, Verification and Validation.

[4. ](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/paper-mutation2013/)Wright, Chris J., Gregory M. Kapfhammer, and Phil McMinn (2013). "Efficient mutation analysis
of relational database structure using mutant schemata and parallelisation," in Proceedings of
the 8th International Workshop on Mutation Analysis. Just, Rene, Gregory M. Kapfhammer, and Franz Schweiggert.

[^^^ To Top ^^^](#table-of-contents)

---

# General SQL Parser <a name="sqlparser"></a>

We have purchased a license of [General SQLParser](http://www.sqlparser.com/) to generate `Java` code interpretting `SQL` statements for the various supported databases. You will not be able to convert SQL code to the Java without either purchasing a license of the [General SQL
Parser](http://www.sqlparser.com/shopping.php) or generating your own Java code. Removing General SQL Parser is what allows us to release this product free and open-source!

[^^^ To Top ^^^](#table-of-contents)

---

# License
[GNU General Public License v3.0](./LICENSE.txt)

The GNU General Public License is a free, copyleft license for software
and other kinds of works.

The licenses for most software and other practical works are designed to
take away your freedom to share and change the works. By contrast, the
GNU General Public License is intended to guarantee your freedom to
share and change all versions of a program--to make sure it remains free
software for all its users. We, the Free Software Foundation, use the
GNU General Public License for most of our software; it applies also to
any other work released this way by its authors. You can apply it to
your programs, too.

When we speak of free software, we are referring to freedom, not price.
Our General Public Licenses are designed to make sure that you have the
freedom to distribute copies of free software (and charge for them if
you wish), that you receive source code or can get it if you want it,
that you can change the software or use pieces of it in new free
programs, and that you know you can do these things.

To protect your rights, we need to prevent others from denying you these
rights or asking you to surrender the rights. Therefore, you have
certain responsibilities if you distribute copies of the software, or if
you modify it: responsibilities to respect the freedom of others.

For example, if you distribute copies of such a program, whether gratis
or for a fee, you must pass on to the recipients the same freedoms that
you received. You must make sure that they, too, receive or can get the
source code. And you must show them these terms so they know their
rights.

Developers that use the GNU GPL protect your rights with two steps: (1)
assert copyright on the software, and (2) offer you this License giving
you legal permission to copy, distribute and/or modify it.

For the developers' and authors' protection, the GPL clearly explains
that there is no warranty for this free software. For both users' and
authors' sake, the GPL requires that modified versions be marked as
changed, so that their problems will not be attributed erroneously to
authors of previous versions.

Some devices are designed to deny users access to install or run
modified versions of the software inside them, although the manufacturer
can do so. This is fundamentally incompatible with the aim of protecting
users' freedom to change the software. The systematic pattern of such
abuse occurs in the area of products for individuals to use, which is
precisely where it is most unacceptable. Therefore, we have designed
this version of the GPL to prohibit the practice for those products. If
such problems arise substantially in other domains, we stand ready to
extend this provision to those domains in future versions of the GPL, as
needed to protect the freedom of users.

Finally, every program is threatened constantly by software patents.
States should not allow patents to restrict development and use of
software on general-purpose computers, but in those that do, we wish to
avoid the special danger that patents applied to a free program could
make it effectively proprietary. To prevent this, the GPL assures that
patents cannot be used to render the program non-free.

The precise terms and conditions for copying, distribution and
modification follow [GNU General Public License v3.0](./LICENSE.txt).

[^^^ To Top ^^^](#table-of-contents)
