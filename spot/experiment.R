############################################################
# SPOT Experiment for parameter tuning with SchemaAnalyst
#
# The experiment design is that SPOT generates a set of
# parameters for GenerateResultsFromGenerator, while the user
# specifies the case study and the generator. At this point,
# the generation is run with the generated parameters. The
# execution time of the generation is measured as an optional
# optimization target.
#
# Next, Original mutation analysis is run and the inverse
# of the mutation score is recorded and returned as the first
# (or only) optimization target.
#
# Usage:
# config <- expConfig()
# res <- expRun(config, "schema_name", "datagenerator")
#
# To see the best parameter configurations, print
# res$alg.currentBest
# To see all configurations, print
# res$alg.currentResult

# import the SPOT package
require(SPOT)

# Define the configuration function (substitute for configuration files).
expConfig <- function(){
	# Set Region Of Interest; variables are as follows:
	# X1 - maxevaluations
	# X2 - negaterows
	# X3 - satisfyrows
	# X4 - randomprofile
	# X5 - randomseed
	roi <- spotROI(
				   lower = c(10, 0, 0, 1, 0), # minimum values
				   upper = c(10000000, 1000, 1000, 2, 100000000), # maximum values
				   type = c("INT", "INT", "INT", "FACTOR", "INT"), # variable types
				   # inexplicably, variable labels cause an error (t.default(x) argument is not a matrix)
				   # leave them out for now
				   #varnames = c("maxeval", "negaterows", "satisfyrows", "randomprofile", "randomseed"), # variable labels
				   dimROI = 5
				)

	# define the target function
	fn <- function(pars, casestudy, seed, generator){
		# assign the SPOT-determined parameters
		maxeval <- pars[1]
		negaterows <- pars[2]
		satisfyrows <- pars[3]
		# translate the FACTOR type value into categorical variable
		switch (as.character(pars[4]),
			"1" = (randomprofile = "small"),
			"2" = (randomprofile = "large")
		)
		randomseed = pars[5]

		# go up a directory to the project root in order for java calls to work
		setwd("..")


		# generate the set of insert statements using the current parameters
		genCallString <- paste("java -Xmx3G -cp build/:lib/* org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator parsedcasestudy.", casestudy, " --maxevaluations=", maxeval , " --negaterows=", negaterows, " --satisfyrows=", satisfyrows, " --randomprofile=", randomprofile, " --datagenerator=", generator, " --randomseed=", randomseed, sep = "")

		# time the execution of the initial generation
		exectime <- system.time(system(genCallString, intern=FALSE))
		exectime <- exectime[3] # exectime is a proc.time object; [3] is elapsed time

		# use standard analysis on the resulting set of statements
		analysisCallString <- paste("java -Xmx3G -cp build/:lib/* org.schemaanalyst.mutation.analysis.technique.Original parsedcasestudy.", casestudy, " 1 ", "--outputfolder=", "spot/", sep = "")
		system(analysisCallString, intern=FALSE);
		# this java code will output to a csv file; read in the most recent mutation score
		scores <- read.csv(paste("spot/parsedcasestudy.", casestudy, ".dat", sep=""))
		mutation_numerator <- tail(scores$scorenumerator, n=1)
		mutation_denominator <- tail(scores$scoredenominator, n=1)
		mutation_score <- as.numeric(as.character(mutation_numerator)) / as.numeric(as.character(mutation_denominator))
		# invert the mutation score (optimiation minimizes this value
		inverse_mutation_score <- 1 - mutation_score;

		# return to the spot directory
		setwd("spot")

		# this line will use a multi-variable optimization target that includes
		# both the mutation score and the execution time of AVM; however,
		# SPOT seems to handle this a tad wonkily--for now, use only mut. score
		#return(c(inverse_mutation_score,exectime))
		return(inverse_mutation_score)
	}

	# set SPOT parameters
	config <- list(
				alg.func = fn,
				alg.resultColumn=c("inverse-mutationscore"),
				alg.roi = roi,
				# Total number of evaluations
				auto.loop.nevals = 50,
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

# make a function that takes experiment variables
# example:
# 	casestudy <- "NistDML183"
# 	seed <- 1234
# 	generator <- "alternatingValue"
expRun <- function(config, casestudy, generator){
	res <- spot(spotConfig=config, casestudy=casestudy, generator=generator)
	return(res)
}

