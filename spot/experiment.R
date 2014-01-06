#####################################################################
# experiment.R
#
# Experiment for optimizing the runtime parameters of SchemaAnalyst
# using the Sequential Parameter Optimization Toolkit (SPOT).
#
# The experiment is designed around the general two-phase process
# of using SchemaAnalyst by (1) generating a series of database 
# interactions (i.e., SQL statements) against an initial schema
# and then (2) running mutation analysis using this initial set of 
# database interactions to kill (optimally, at least) a series of 
# mutant schemas.
# 
# Here, SPOT is used to generate a variety of configurations that
# take into account four parameters of the database interaction
# generation phase: (1) satisfy rows, (2) negate rows,
# (3) random profile, and (4) random seed, each across
# a series of values defined in the SPOT region of interest (ROI)
# declaration that is part of expConfig().
#
# In each evaluation, the SPOT-generated configuration is then used
# to generate a set of database interactions, at which point we
# run mutation analysis using the Original technique (which,
# despite being relatively slowest, is also most comprehensive).
#
# During these steps, both the runtime of the generation phase
# and the mutant score resulting from the analysis phase are
# recorded and provided to SPOT as optimization targets
# (although the useGenerationTime option to expConfig()
# does allow for the use of mutationScore alone as the
# optimization target). In particular, note that SPOT minimizes the
# optimization targets, so we actually provide it with the inverse
# mutation score (1 - mutationScore).
# 
# After completing the specified number of evaluations, the results
# of the SPOT analysis are returned by the expRun() function as
# well as stored in the variable res and then saved to an .RData
# file in the spot/results/{DB_NAME}/ directory.
#
# ---- Usage:---------------------------------------------------
# Note: It is expected that this code will be run from the 
# project directory root for SchemaAnalyst; otherwise, there 
# will be classpath issues. From here, the functions can
# be loaded by running
# source("spot/experiment.R")
#
# An experiment can be configured and run with the following:
# config <- expConfig(numEvaluations, useGenerationTime)
# res <- expRun(config, "{SCHEMA_NAME}", "{GENERATOR}")
#
# To see the best determined parameter configurations, print
# res$alg.currentBest
#
# To see all tested configurations, print
# res$alg.currentResult
#
# Saved results can be loaded in the current R session with
# load("spot/results/{DB_NAME}/{RESULTS_FILE_NAME}.RData")
# at which point the results are stored in the variable res.
# --------------------------------------------------------------
#
# Authors: Nathaniel Blake & Colin Soleim, Allegheny College
#####################################################################

require(SPOT)

# Define experiment variables
DATABASE = "HSQLDB" # used for output location; doesn't alter SchemaAnalyst preferences (currently)
# upper bounds during parameter tuning
MAX_SATISFY_ROWS <- 10 
MAX_NEGATE_ROWS <- 10
MAX_EVALUATIONS <- 1000000 # statically defined to be high; not part of SPOT's optimization work

# Define the configuration function (substitute for configuration files).
# -- Takes as a parameter the maximum number of evaluations for the SPOT experiment
expConfig <- function(numEvals, useGenerationTime=FALSE) {
	# Set Region Of Interest; variables are as follows:
	# X1 - negaterows
	# X2 - satisfyrows
	# X3 - randomprofile
	# X4 - randomseed
	roi <- spotROI(
				   # note that the lower bounds for satisfy rows and negaterows are currently 1;
				   # this is because it if either equals 0, generation hangs
				   lower = c(1, 1, 1, 0), # minimum values
				   upper = c(MAX_NEGATE_ROWS, MAX_SATISFY_ROWS, 2, 9999999), # max values
				   type = c("INT", "INT", "FACTOR", "INT"), # variable types
				   # Inexplicably, variable labels cause an error (t.default(x) argument is not a matrix),
				   # so leave them out for now.
				   #varnames = c("negaterows", "satisfyrows", "randomprofile", "randomseed"), # variable labels
				   dimROI = 4
				)

	# define the target function
	fn <- function(pars, casestudy, seed, generator){
		# assign the SPOT-determined parameters
		negaterows <- pars[1]
		satisfyrows <- pars[2]
		# translate the FACTOR type value into categorical variable
		switch (as.character(pars[3]),
			"1" = (randomprofile = "small"),
			"2" = (randomprofile = "large")
		)
		randomseed = pars[4]

		# generate the set of database interaction statements using the current parameters
		genCallString <- paste("java -Xmx3G -cp build/:lib/*",
							   " org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator",
							   " parsedcasestudy.", casestudy, " --maxevaluations=", MAX_EVALUATIONS,
							   " --negaterows=", negaterows, " --satisfyrows=", satisfyrows, 
							   " --randomprofile=", randomprofile, " --datagenerator=", generator, 
							   " --randomseed=", randomseed, " --writeReport",
							   " --reportLocation=spot/generationCosts.dat", sep = "")
		print(genCallString)
		system(genCallString, intern=FALSE)

		# use Original mutation analysis on the resulting set of statements
		analysisCallString <- paste("java -Xmx3G -cp build/:lib/*",
									" org.schemaanalyst.mutation.analysis.technique.Original",
									" parsedcasestudy.", casestudy, " 1", 
									" --outputfolder=spot/analyses/", sep = "")
		system(analysisCallString, intern=FALSE);

		# the above java code will output to a csv file; read in the most recent mutation score
		scores <- read.csv(paste("spot/analyses/parsedcasestudy.", casestudy, ".dat", sep=""))
		mutationNumerator <- tail(scores$scorenumerator, n=1)
		mutationDenominator <- tail(scores$scoredenominator, n=1)
		mutationScore <- mutationNumerator / mutationDenominator
		# invert the mutation score (optimization minimizes this value)
		inverseMutationScore <- 1 - mutationScore

		if (useGenerationTime) {
			# read in the generation execution time
			generationCosts <- read.csv("spot/generationCosts.dat")
			genExecTime <- tail(generationCosts$timetaken, n=1)

			# this line will return a multi-variable optimization target that includes
			# both the mutation score and the execution time of AVM
			return(c(inverseMutationScore, genExecTime))
		} 
		else { # we'll only optimize the mutation score
			return(inverseMutationScore)
		}

	}

	# set SPOT result column titles according to whether we're using multiple optimization targets
	if (useGenerationTime) {
		resultColumn <- c("InverseMutationScore", "GenerationTime")
	}
	else
		resultColumn <- "InverseMutationScore"

	# set SPOT parameters
	config <- list(
				alg.func = fn,
				alg.resultColumn=resultColumn, # defined conditionally above
				alg.roi = roi,
				# Total number of evaluations
				auto.loop.nevals = numEvals, # expConfig() function parameter
				init.design.func = "spotCreateDesignLhd",
				# number of separate initial designs to check
				init.design.size = 10,
				# number of runs per parameter configuration--
				# 1 works efficiently since this is deterministic
				init.design.repeats = 1,
				spot.filemode = FALSE,
				seq.predictionModel.func = "spotPredictRandomForest",
				spot.seed=123,
				spot.ocba=FALSE
			)

	return(config)
}

# Function that initiates the SPOT experiment and stores the result in
# a variable res, saved in an RData file to spot/results/{DB_NAME}/.
#
# Example arguments:
# 	casestudy="NistDML183"
# 	generator="alternatingValue"
expRun <- function(config, casestudy, generator) {
	# execute the SPOT experiment
	res <- spot(spotConfig=config, casestudy=casestudy, generator=generator)

	# save the results to a file
	outputDir <- paste0("spot/results/", DATABASE, "/")
	filename <- paste(casestudy, generator, config$auto.loop.nevals, sep="-")
	filePath <- paste0(outputDir, filename, ".RData")
	# ensure that the output directory exists; if not, create it
	if (file.exists(outputDir) || dir.create(outputDir, recursive=TRUE))
		save(res, file=filePath)
	else # in this case, the directory doesn't exist and we failed to create it
		print(paste0("Error: Unable to save results to '",filePath,"'."))

	return(res)
}

