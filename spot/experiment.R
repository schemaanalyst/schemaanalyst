############################################################
# SPOT Experiment for parameter tuning with SchemaAnalyst
#
# The experiment design is that SPOT generates a set of
# parameters for GenerateResultsFromGenerator, while the user
# specifies the case study and the generator. At this point,
# the generation is run with the generated parameters. The
# execution time of the generation is used as an optional
# secondary optimization target.
#
# Next, Original mutation analysis is run and the inverse
# of the mutation score is recorded and used as the first
# (or only) optimization target.
#
# Usage:
# config <- expConfig(num_evaluations, use_generationtime)
# res <- expRun(config, "schema_name", "datagenerator")
#
# To see the best parameter configurations, print
# res$alg.currentBest
#
# To see all configurations, print
# res$alg.currentResult
#
# import the SPOT package
require(SPOT)

# Define experiment variables
DATABASE = "HSQLDB" # used for output location; doesn't alter SchemaAnalyst preferences (currently)
# upper bounds during parameter tuning
MAX_SATISFY_ROWS <- 10 
MAX_NEGATE_ROWS <- 10

# Define the configuration function (substitute for configuration files).
# -- Takes as a parameter the maximum number of evaluations for the SPOT experiment
expConfig <- function(num_evals, use_generationtime=FALSE) {
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
				   # inexplicably, variable labels cause an error (t.default(x) argument is not a matrix)
				   # leave them out for now
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

		# go up a directory to the project root in order for java calls to work
		setwd("..")

		# generate the set of INSERT statements using the current parameters
		genCallString <- paste("java -Xmx3G -cp build/:lib/*",
							   " org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator",
							   " parsedcasestudy.", casestudy, 
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
									" --outputfolder=spot/", sep = "")
		system(analysisCallString, intern=FALSE);

		# return to the spot directory
		setwd("spot")

		# the above java code will output to a csv file; read in the most recent mutation score
		scores <- read.csv(paste("parsedcasestudy.", casestudy, ".dat", sep=""))
		mutation_numerator <- tail(scores$scorenumerator, n=1)
		mutation_denominator <- tail(scores$scoredenominator, n=1)
		mutation_score <- mutation_numerator / mutation_denominator
		# invert the mutation score (optimization minimizes this value)
		inverse_mutation_score <- 1 - mutation_score

		if (use_generationtime) {
			# read in the generation execution time
			generation_costs <- read.csv("generationCosts.dat")
			gen_exec_time <- tail(generation_costs$timetaken, n=1)

			# this line will return a multi-variable optimization target that includes
			# both the mutation score and the execution time of AVM
			return(c(inverse_mutation_score, gen_exec_time))
		} 
		else { # we'll only optimize the mutation score
			return(inverse_mutation_score)
		}

	}

	# set SPOT result column titles according to whether we're using multiple optimization targets
	if (use_generationtime) {
		resultColumn <- c("inverse-mutation-score", "generation-time")
	}
	else
		resultColumn <- "inverse-mutation-score"

	# set SPOT parameters
	config <- list(
				alg.func = fn,
				alg.resultColumn=resultColumn, # defined conditionally above
				alg.roi = roi,
				# Total number of evaluations
				auto.loop.nevals = num_evals, # expConfig() function parameter
				init.design.func = "spotCreateDesignLhd",
				# number of separate designs to check--
				# bigger will get better results but take longer
				init.design.size = 10,
				# number of runs per parameter configuration--
				# 1 should be fine since this is deterministic
				init.design.repeats = 1,
				spot.filemode = TRUE,
				seq.predictionModel.func = "spotPredictRandomForest",
				spot.seed=123,
				spot.ocba=FALSE
			)

	return(config)
}

# define a function that initiates a SPOT experiment
# example arguments:
# 	casestudy="NistDML183"
# 	generator="alternatingValue"
expRun <- function(config, casestudy, generator) {
	# execute the SPOT experiment
	res <- spot(spotConfig=config, casestudy=casestudy, generator=generator)

	# save the results to a file
	directory = paste("results/", DATABASE, "/", sep="")
	filename = paste(casestudy, generator, config$auto.loop.nevals, ".RData", sep="-")
	save(res, file=paste(directory, filename, sep=""))

	return(res)
}

