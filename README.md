# SchemaAnalyst

<img src="https://github.com/schemaanalyst/logo/blob/master/schemaanalyst-logo-gh.png" height="250" alt="SchemaAnalyst - a mutation testing tool for relational database schemas">

## Quick Start

Want to get started right away? Then, jump to [Getting
Started](#getting-started)! Otherwise, read on for additional details about the
SchemaAnalyst tool for testing relational database schemas.

## Description

There has been little work that has sought to ensure that a relational
database's schema contains correctly specified integrity constraints [(TOSEM
2015)](#five). Testing a database's schema verifies that all of its integrity
constraints will accept and reject data as intended, confirming the integrity
and security of the database itself. Early testing of a schema not only helps
to ensure that the integrity constraints are correct, it can also reduce
implementation and maintenance costs associated with managing a relational
database.

SchemaAnalyst uses a search-based approach to test the complex relationships
between the integrity constraints in relational databases. Other
schema-analyzing tools test a database's schema in a less efficient manner but,
more importantly, use a less effective technique. A recent study  finds that,
for all of the case studies, SchemaAnalyst obtains higher constraint coverage
than a similar schema-analyzing tool while reaching 100% coverage on two
schemas for which the competing tool covers less than 10% of the constraints
[(ICST 2013)](#two). SchemaAnalyst also achieves these results with generated
data sets that are substantially smaller than the competing tool and in an
amount of execution time that is competitive or faster [(ICST 2013)](#two).

## Table of Contents <a name="table-of-contents"></a>

+ [Overview](#overview)
+ [Getting Started](#getting-started)
    - [Downloading](#downloading)
    - [Dependencies](#dependencies)
    - [Configuring](#configuring)
    - [Compiling](#compiling)
    - [Testing](#testing)
    - [Set Classpath](#classpath)
    - [Convert Schema to Java](#sqlparser)
+ [Tutorial](#tutorial)
    - [Asciicinema Recording](#asciicinema)
    - [Help Menu](#help)
    - [Options](#options)
    - [Test Data Generation](#test-data-generation)
        * [Syntax](#test-data-generation-syntax)
        * [Parameters](#test-data-generation-parameters)
        * [Output](#test-data-generation-output)
        * [Example](#test-data-generation-example)
    - [Mutation Analysis](#mutation-analysis)
        * [Syntax](#mutation-analysis-syntax)
        * [Parameters](#mutation-analysis-parameters)
        * [Output](#mutation-analysis-output)
        * [Interpretation](#mutation-analysis-interpretation)
        * [Examples](#mutation-analysis-examples)
+ [Building and Execution Environment](#environment)
+ [Publications](#publications)
+ [License](#license)

## Overview <a name="overview"></a>

A database schema acts as the cornerstone for any application that relies on a
relational database. It specifies the types of allowed data, as well as the
organization and relationship between the data.  Any oversight at this
fundamental level can easily propagate errors toward future development stages.
Such oversights might include incomplete foreign or primary key declarations,
or incorrect use or omission of the `UNIQUE`, `NOT NULL`, and `CHECK` integrity
constraints. Such seemingly small mistakes at this stage can prove costly to
correct, thus we created SchemaAnalyst to allow for the early detection of such
problems prior to integration of a schema with an application.  Ultimately,
SchemaAnalyst meticulously tests the correctness of a schema &mdash; it ensures that
valid data is permitted entry into a database and that erroneous data is
rejected.

To do this, various "mutants" are created from a given schema using a defined
set of mutation operators. These operators change the schema's integrity
constraints in different ways. For instance, a mutant may be created by
removing a column from a primary key, or from removing a `NOT_NULL` constraint
from a column, among many other possibilities. These schemas are then evaluated
through a process known as mutation analysis. Using a search-based technique,
test suites are created that execute `INSERT` statements into tables for both
the original schema and the mutant schema [(ICST 2013)](#two). If the `INSERT`
statement is accepted by the original schema but rejected by the mutant schema,
then it shows that the inserted data adheres to the integrity constraints of
the original schema, and the test suite is able to detect and respond
appropriately to the change. This is said to "kill" the mutant. After all
mutants have been analyzed in this fashion, a mutation score is generated as
follows: <em>mutation score = number of killed mutants / number of
mutants</em>. In general, the higher the mutation score the better the quality
of the generated test suite [(ICST 2013)](#two).

## Getting Started <a name="getting-started"></a>

### Downloading <a name="downloading"></a>

The source code is hosted in a [GitHub
repository](https://github.com/schemaanalyst/schemaanalyst). To obtain
SchemaAnalyst, simply clone this repository on your machine using the following
command:

`git clone git@github.com:schemaanalyst/schemaanalyst.git`

### Dependencies <a name="dependencies"></a>

To use SchemaAnalyst, Java 1.7 JDK (or higher) must be installed to run any of
the Java programs.  See the table below for a full description of the required
and optional dependencies.

| Software                                                                                       | Required?   | Purpose                             |
| :--------:                                                                                     | :---------: | :-------:                           |
| [Java 1.7 JDK (or higher)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) | X           | Running the system                  |
| [PostgreSQL](http://www.postgresql.org/download)                                               |             | Using Postgres with selected schema |
| [SQLite](http://www.sqlite.org/download.html)                                                  |             | Using SQLite with selected schema   |
| [HSQLDB](http://hsqldb.org/)                                                                   |             | Using HyperSQL with selected schema |

If you are getting started with SchemaAnalyst and relational databases, then it
will likely be easiest if you use the SQLite database management system. In
fact, SQLite is often installed by default by many Linux operating systems. If
you are running the Ubuntu operating system and you discover that SQLite is not
currently installed, then you can install it by running the following command in
your terminal window:

```
sudo apt install sqlite3
```

### Configuring <a name="configuring"></a>

###### Properties <a name="properties"></a>

SchemaAnalyst uses a number of _properties_ files to specify some configuration
options. These are located in the `config/` directory. These names of these
files are as follows:

* `database.properties`: contains properties relating to database connections,
  such as usernames and passwords. The `dbms` property at the top of this file
  specifies which database to use (i.e., SQLite, Postgres, or HyperSQL).

* `locations.properties`: specifies the layout of the SchemaAnalyst
  directories, and should not require any changes (but may be useful if adding
  to the Ant script, which automatically loads it).

* `experiment.properties`: _The contents of this file can be ignored as it is no
  longer used in the current version of SchemaAnalyst. Subsequent versions of
  SchemaAnalyst will likely not include this file and the code that reads it._

* `logging.properties`: specifies the level of logging output that should be
  produced. Changing the `.level` and `java.util.logging.ConsoleHandler.level`
  options allows the level to be altered. Note that unless you enable logging
  to a file, effectively the lower of the two levels is used.

*__Note__: To allow you to specify your own local versions of these files, which
you will not commit to the Git repository, SchemaAnalyst runners will
automatically load versions suffixed with `.local` over those without the
suffix. If you need to change any of the properties, you should therefore create
your own local version by copying the file and adding the suffix (e.g.,
`database.properties` becomes `database.properties.local`).*

###### Databases <a name="databases"></a>

HSQLDB and SQLite both require no additional configuration for use with
SchemaAnalyst. If you are using PostgreSQL, then please note that the
`database.properties` file is preconfigured to connect to a PostgreSQL database
using the default credentials. In addition, you must give this user full
privileges over the `postgres` database.

### Compiling <a name="compiling"></a>

The SchemaAnalyst tool is built using [Gradle](http://gradle.org/). Please
follow these steps to compile the system using the provided Gradle wrapper:

1. Open a terminal and navigate to the default `schemaanalyst` directory.

2. Type `./gradlew compile` to first download the Gradle dependencies then the
   necessary `.jar` files in the `lib` directory and compile the system into
   the `build` directory.

*__Note__: The message `Some input files use unchecked or unsafe operations`
may be ignored if it appears during compilation.*

### Testing <a name="testing"></a>

To confirm that the code has properly compiled, you should be able to run the
provided test suite by typing the following command:

`./gradlew test`

A `BUILD SUCCESSFUL` message should appear, indicating that testing has
completed with no failures or errors.

*__Note__: This assumes that all three DBMSs (i.e., HyperSQL, SQLite, and
Postgres) are accessible. If they are not, then any tests related to the
unavailable databases may fail by default.  Please refer to the
[Dependencies](#dependencies) section for links to download and install these
DBMS.*

### Set CLASSPATH <a name="classpath"></a>

Before running any of the commands listed in the [Tutorial](#tutorial) section,
you should set the CLASSPATH environment variable by typing the following
command in your terminal window:

`export CLASSPATH="build/classes/main:lib/*:build/lib/*:."`

### Convert Schema to Java <a name="sqlparser"></a>

We have purchased a license of [General SQLParser](http://www.sqlparser.com/)
to generate `Java` code interpreting SQL statements for the various supported
databases. You will not be able to convert SQL code to Java without either
purchasing a license of the [General SQL
Parser](http://www.sqlparser.com/shopping.php) or generating your own Java
code. Removing General SQL Parser is what allowed us to release this tool under
a free and open-source license! We have included a number of sample schema to
use with SchemaAnalyst. The original `.sql` files can be found in the
`schemaanalyst/casestudies/schema/` directory, while the converted `.java`
files can be found in the `schemaanalyst/build/classes/main/parsedcasestudy/`
directory after compiling the system.

## Tutorial <a name="tutorial"></a>

### Asciinema Recording <a name="asciicinema"></a>

Please watch this Asciinema recording that shows some of the key features of
SchemaAnalyst, which are explained in more detail below.

[![asciicast](https://asciinema.org/a/cuh7p68xg3ra65sl5rp8wimj5.png)](https://asciinema.org/a/cuh7p68xg3ra65sl5rp8wimj5)

---

### Help Menu <a name="help"></a>

SchemaAnalyst uses a command-line interface with a variety of execution
options. Two primary commands are included: `generation` for [Test Data
Generation](#test-data-generation) and `mutation` for [Mutation
Analysis](#mutation-analysis). Note that one of these two commands *must* be
applied, and their syntax is discussed at a later point in this document.

You are also able to print the help menu at any time with the `--help`, or `-h`
command of the `Go` class within the `java org.schemaanalyst.util` package by
typing the following command in your terminal window:

`java org.schemaanalyst.util.Go -h`

This command will then produce the following output:

```
Usage: <main class> [options] [command] [command options]
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
  * --schema, -s
       Target Schema
       Default: <empty string>
  Commands:
    generation      Generate test data with SchemaAnalyst
      Usage: generation [options]
        Options:
          --sql, --inserts
             Target file for writing INSERT statements
          --testSuite, -t
             Target file for writing JUnit test suite
             Default: TestSchema
          --testSuitePackage, -p
             Target package for writing JUnit test suite
             Default: generatedtest

    mutation      Perform mutation testing of SchemaAnalyst
      Usage: mutation [options]
        Options:
          --maxEvaluations
             The maximum fitness evaluations for the search algorithm to use.
             Default: 100000
          --pipeline
             The mutation pipeline to use to generate mutants.
             Default: AllOperatorsWithRemovers
          --seed
             The random seed.
             Default: 0
          --technique
             Which mutation analysis technique to use.
             Default: original
          --transactions
             Whether to use transactions with this technique (if possible).
             Default: false
```

### Options <a name="options"></a>

The following options can precede the `generation` and `mutation` commands for
additional functionality (note that the `--schema` option is required):

| Parameter   | Required   | Description                                                        |
| :---------: | :--------: | :-----------:                                                      |
| --criterion |            | The coverage criterion to use to generate data                     |
| --dbms      |            | The database management system to use (SQLite, HyperSQL, Postgres) |
| --generator |            | The data generator to use to produce SQL `INSERT`` statements      |
| --help      |            | Show the help menu                                                 |
| --schema    | X          | The schema chosen for analysis                                     |

*__Note:__ If you attempt to execute any of the `Runner` classes of
SchemaAnalyst without the necessary parameters, or if you type the `--help`
tag, you should be presented with information describing the parameters and
detailing which of these are required. Where parameters are not required, the
defaults values should usually be sensible. While there are other parameters
available for this class, it is generally not necessary to understand their
purpose.*

#### Test Data Generators

There are multiple implemented test data generators available for you to use:

 1. `avsDefaults` - AVM implementation using default values
 2. `avs` - AVM implementation using random values
 3. `random` - Random data generator technique.
 4. `dominoRandom` - The original and random DOMINO (DOMain-specific approach to INtegrity cOnstraint test data generator) technique.
 5. `dominoAVS` - The hybrid technique DOMINO and AVM.

### Test Data Generation <a name="test-data-generation"></a>

###### Syntax <a name="test-data-generation-syntax"></a>

SchemaAnalyst will create a series of `INSERT` statements to test the integrity
constraints that are altered via mutation, as described in the
[Overview](#overview) section. This data is typically hidden from the user
during the analysis, but if you wish to see what data the system is generating
for this process, then you can use the following syntax:

`java org.schemaanalyst.util.Go -s schema <options> generation <parameters>`

Where `schema` is replaced with the path to the schema of interest, `<options>`
can be replaced by any number of the options described in the
[Options](#options) section, and `<parameters>` can be replaced by any number
of parameters described below.

###### Parameters <a name="test-data-generation-parameters"></a>

| Parameter          | Required   | Description                                                    |
| :---------:        | :--------: | :-----------:                                                  |
| --inserts          |            | Target file for writing `INSERT` statements into a `.sql` file |
| --testSuite        |            | Target file for writing JUnit test suite                       |
| --testSuitePackage |            | Target package for writing JUnit test suite                    |

###### Output <a name="test-data-generation-output"></a>

By default, the `generation` command creates a JUnit test suite in the
`generatedtest` directory.  The name of the file can be changed with the
`--testSuite` parameter, while the package can be changed with the
`--testSuitePackage` parameter. Alternatively, the `--inserts` parameter can
be used to generate a `.sql` file with all of the `INSERT` statements used to
test the integrity constraints of the schema. These statements are also
automatically displayed in the console window after execution. See the example
below for the output from a specific schema.

###### Example <a name="test-data-generation-example"></a>

To generate test data for the ArtistSimilarity schema using the `Postgres`
database, the `UCC` coverage criterion, the `avsDefaults` dataGenerator, and
save the output in the file `SampleOutput.sql`, type the following command in
your terminal window:

`java org.schemaanalyst.util.Go -s parsedcasestudy.ArtistSimilarity --dbms Postgres --criterion UCC --generator avsDefaults generation --inserts SampleOutput`

This will produce a series of `INSERT` statements for each mutant of the
schema.  Some abbreviated output from the previous command includes:

```
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

INSERT INTO "artists"(
        "artist_id"
) VALUES (
        ''
)
...
```

### Mutation Analysis <a name="mutation-analysis"></a>

###### Syntax <a name="mutation-analysis-syntax"></a>

To create data to exercise the integrity constraints of a schema using the data
generation component of SchemaAnalyst and then perform mutation analysis using
the generated data type the following command in your terminal:

`java org.schemaanalyst.util.Go -s schema <options> mutation <parameters>`

Where `schema` is replaced with the path to the schema of interest, `<options>`
can be replaced by any number of the options described in the
[Options](#options) section, and `<parameters>` can be replaced by any number
of parameters described below.

###### Parameters <a name="mutation-analysis-parameters"></a>

| Parameter        | Required   | Description                                                                                    |
| :---------:      | :--------: | :-----------:                                                                                  |
| --maxEvaluations |            | The maximum fitness evaluations for the search algorithm to use                                |
| --pipeline       |            | The mutation pipeline to use to produce and, optionally, remove mutants                        |
| --seed           |            | The seed used to produce random values for the data generator                                  |
| --technique      |            | The mutation technique to use (e.g., original, fullSchemata, minimalSchemata, or mutantTiming) |
| --transactions   |            | Whether to use SQL transactions to improve the performance of a technique, if possible         |

###### Output <a name="mutation-analysis-output"></a>

Specifying the `technique` parameter to output the mutant timing results will
create a CSV file located at `results/mutanttiming.csv`. This file is useful if
you are interested in looking at individual mutants. It contains seven
attributes: `identifier`, `dbms`, `schema`, `operator`, `type`, `killed`, and
`time.` More details about these attributes are available in the following table:

| Column     | Description                                                           |
| :------:   | :-----------:                                                         |
| identifier | The unique identifier for the dbms, schema and operator configuration |
| dbms       | The DBMS                                                              |
| schema     | The schema                                                            |
| operator   | The mutation operator used to generate the mutant                     |
| type       | The type of mutant (e.g., NORMAL, DUPLICATE, EQUIVALENT)              |
| killed     | The kill status of a mutant (i.e., true=killed, false=alive)          |
| time       | The time, in ms, to generate the mutant                               |


To perform mutation analysis with `technique=mutantTiming` and the
`ArtistSimilarity` schema you can type the following command in your terminal
window:

`java org.schemaanalyst.util.Go -s parsedcasestudy.ArtistSimilarity mutation --technique=mutantTiming`

This command will produce the following rows of data in the
`results/mutanttiming.dat` file:

```
identifier,dbms,schema,operator,type,killed,time
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,FKCColumnPairE,NORMAL,true,89
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,FKCColumnPairE,NORMAL,true,96
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,PKCColumnA,NORMAL,false,89
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,PKCColumnA,NORMAL,false,92
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,NNCA,NORMAL,false,75
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,NNCA,NORMAL,false,73
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,UCColumnA,NORMAL,false,84
mebiyeqtukr3ojgdtuyf,Postgres,ArtistSimilarity,UCColumnA,NORMAL,false,91
```

Executing this class produces a single results file in CSV format that contains
one line per execution, located at `results/newmutationanalysis.dat`. This
file contains the following columns that have this description:

| Column               | Description                                                                 |
| :------:             | :-----------:                                                               |
| dbms                 | The DBMS                                                                    |
| casestudy            | The schema                                                                  |
| criterion            | The integrity constraint coverage criterion                                 |
| datagenerator        | The data generation algorithm                                               |
| randomseed           | The value used to seed the pseudo-random number generator                   |
| coverage             | The level of coverage the produced data achieves according to the criterion |
| evaluations          | The number of fitness evaluations used by the search algorithm              |
| tests                | The number of test cases in the produced test suite                         |
| mutationpipeline     | The mutation pipeline used to generate mutants                              |
| scorenumerator       | The number of mutants killed by the generated data                          |
| scoredenominator     | The total number of mutants used for mutation analysis                      |
| technique            | The mutation analysis technique                                             |
| transactions         | Whether SQL transactions were applied, if possible                          |
| testgenerationtime   | The time taken to generate test data in milliseconds                        |
| mutantgenerationtime | The time taken to generate mutants in milliseconds                          |
| originalresultstime  | The time taken to execute the test suite against the non-mutated schema     |
| mutationanalysistime | The time taken to perform analysis of all of the mutant schemas             |
| timetaken            | The total time taken by the entire process                                  |

###### Interpretation <a name="mutation-analysis-interpretation"></a>

The output produced by mutation analysis contains a significant amount of
information, some of which might not be needed for your purposes. If you are
simply concerned with the correctness of your schema, focus on the
`scorenumerator` and `scoredenominator` columns, as defined previously. By
dividing the numerator by the denominator you will generate a mutation score in
the range [0, 1]. This score provides an estimate for how well the schema has
performed when its integrity constraints were exercised, with higher scores
indicating that the schema is more likely to permit valid data from entering a
table and to reject any invalid data. Although there does not currently exist a
standard for this metric, scores between 0.6 -- 0.7 (60% -- 70%) are generally
considered good. If your schema's score falls below this level, consider
viewing the [Mutation Analysis](#mutant-analysis) section to gain further insight
into the types of mutants created and killed during the process.

###### Examples <a name="mutation-analysis-examples"></a>

1.  Type the following command in your terminal to perform mutation analysis
    with the default configuration, and the `ArtistSimilarity` schema:

    `java org.schemaanalyst.util.Go -s parsedcasestudy.ArtistSimilarity mutation`

    This command produces the following data in the `results/newmutationanalysis.dat` file:

    ```
    dbms,casestudy,criterion,datagenerator,randomseed,testsuitefile,coverage,evaluations,tests,mutationpipeline,scorenumerator,scoredenominator,technique,transactions,testgenerationtime,mutantgenerationtime,originalresultstime,mutationanalysistime,timetaken
    SQLite,parsedcasestudy.ArtistSimilarity,CondAICC,avsDefaults,0,NA,100.0,22,9,AllOperatorsWithRemovers,5,9,original,false,259,67,5,31,371
    ```

2.  Type the following command in your terminal to perform mutation analysis
    with a random seed of `1000`, the `ClauseAICC` coverage criterion, the
    `random` data generator, and the `ArtistSimilarity` schema:

    `java org.schemaanalyst.util.Go -s parsedcasestudy.ArtistSimilarity --criterion ClauseAICC --generator random mutation --seed 1000`

    This command produces the following data in the `results/newmutationanalysis.dat` file:

    ```
    dbms,casestudy,criterion,datagenerator,randomseed,testsuitefile,coverage,evaluations,tests,mutationpipeline,scorenumerator,scoredenominator,technique,transactions,testgenerationtime,mutantgenerationtime,originalresultstime,mutationanalysistime,timetaken
    SQLite,parsedcasestudy.ArtistSimilarity,ClauseAICC,random,1000,NA,88.88888888888889,133786,8,AllOperatorsWithRemovers,5,9,original,false,8749,61,4,20,8844
    ```

## Building and Execution Environment <a name="environment"></a>

All of the previous instructions for building, installing, and using
SchemaAnalyst have been tested on Mac OS X 10.11 and Ubuntu Linux 16.04. All of
the development and testing on both workstations was done with Java Standard
Edition 1.8. While SchemaAnalyst is very likely to work on other Unix-based
development environments, we cannot guarantee correct results for systems
different than the ones mentioned previously. Currently, we do not provide full
support for the building, installation, and use of SchemaAnalyst on Windows.

## Publications

[(ICST 2013)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/Kapfhammer2013/)Kapfhammer,
Gregory M., Phil McMinn, and Chris J. Wright (2013). "Search-based testing of
relational schema integrity constraints across multiple database management
systems," in Proceedings of the 6th International Conference on Software
Testing, Verification and Validation. <a name="one"></a>

[(Mutation 2013)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/Wright2013/)Wright,
Chris J., Gregory M. Kapfhammer, and Phil McMinn (2013). "Efficient mutation
analysis of relational database structure using mutant schemata and
parallelisation," in Proceedings of the 8th International Workshop on Mutation
Analysis. <a name="two"></a>

[(QSIC 2014)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/Wright2014/)Wright,
Chris J., Gregory M. Kapfhammer, and Phil McMinn (2014). "The impact of
equivalent, redundant, and quasi mutants on database schema mutation analysis,"
in Proceedings of the 14th International Conference on Quality Software. <a
name="three"></a>

[(SEKE 2015)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/Kinneer2015/)Kinneer,
Cody, Gregory M. Kapfhammer, Chris J. Wright, and Phil McMinn (2015).
"Automatically evaluating the efficiency of search-based test data generation
for relational database schemas," in Proceedings of the 27th International
Conference on Software Engineering and Knowledge Engineering. <a
name="four"></a>

[(TOSEM 2015)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/McMinn2015/)McMinn,
Phil, Chris J. Wright, and Gregory M. Kapfhammer (2015). "The Effectiveness of
Test Coverage Criteria for Relational Database Schema Integrity Constraints,"
in Transactions on Software Engineering and Methodology, 25(1). <a
name="five"></a>

[(ICSME 2016)
](http://www.cs.allegheny.edu/sites/gkapfham/research/papers/McMinn2016c/)McMinn,
Phil, Chris J. Wright, Cody Kinneer, Colton J. McCurdy, Michael Camara, and
Gregory M. Kapfhammer (2015). "SchemaAnalyst: Search-based Test Data generation
for Relational Database Schemas" in Proceedings of the 32nd International
Conference on Software Maintenance and Evolution, 2016. <a name="six"></a>

## License

[GNU General Public License v3.0](./LICENSE.txt)

