#####################################################################
# generate-graphics.R
#
# Generates a series of visualizations from a set of experiment
# results as saved in .RDate files by experiment.R.
#
# ----- Usage: -------------------------------------------------
# Note: It is expected that this code will be run from the 
# project directory root for SchemaAnalyst. From here, the 
# visualization function can be loaded by running
# source("spot/generate-graphics.R")
#
# Next, the visualizations can be generated for a set of
# result files by running
# generateGraphics("path/to/resultsFiles/")
#
# All generated visualizations will be stored in the above path
# in a visualizations/ subdirectory.
# --------------------------------------------------------------
#
# Author: Nathaniel Blake, Allegheny College
#####################################################################

require(randomForest) # used to calculate parameter importance values
require(rpart) # used to generate classification trees
require(party) # used to generate conditional inference trees
require(ggplot2) # used to create visualizations for variable importance
require(reshape) # used to restructure data for use with ggplot2

generateGraphics <- function(resultsDirectory) {
	# create an output directory
	outputDir = paste0(resultsDirectory,"visualizations/")
	if (!file.exists(outputDir) && !dir.create(outputDir)) {
		# give up--we have nowhere to store output
		stop(paste0("Error: Could not create output directory '",outputDir,"'."))
	}

	# store all importance values in order to analyze later
	# TODO: prevent the row of NAs that this initialization causes
	mutScoreImportances = as.data.frame(matrix(ncol = 4))
	colnames(mutScoreImportances) <- c("SatisfyRows", "NegateRows", "RandomProfile", "RandomSeed")
	genTimeImportances = as.data.frame(matrix(ncol = 4))
	colnames(genTimeImportances) <- c("SatisfyRows", "NegateRows", "RandomProfile", "RandomSeed")

	# iterate through the results files
	for (fileName in list.files(resultsDirectory)) {
		# only load in .RData files
		if (!grepl(".RData", fileName))
			next # that is, continue to the next fileName

		# load the result set from this file
		print(paste0("Loading ", fileName, "."))
		load(paste0(resultsDirectory,fileName))

		# abbreviate the current result reference
		curRes <- res$alg.currentResult

		# Rename the results data columns.
		names(curRes)[7] <- "NegateRows"
		names(curRes)[8] <- "SatisfyRows"
		names(curRes)[9] <- "RandomProfile"
		names(curRes)[10] <- "RandomSeed"
		names(curRes)[11] <- "MutationScore"
		names(curRes)[12] <- "GenerationTime"

		# Re-invert the mutation score; we no longer need to minimize it as does SPOT,
		# so seeing the actual score will be more useful in graphics.
		curRes["MutationScore"] <- 1 - curRes["MutationScore"]

		# generate a base filename for file (image) output from the following tools
		outBaseName <- sub(".RData", "", fileName) # strip .RData from result file name

		# gather experiment details from file name
		schemaName <- unlist(strsplit(fileName, "-"))[1] # extract schema from file name
		generatorName <- unlist(strsplit(fileName, "-"))[2] #extract generator from file name

		# Calculate the relative importance of each parameter on the mutation score.
		mutScoreFit <- randomForest(MutationScore ~ SatisfyRows + NegateRows + RandomProfile + RandomSeed, data=curRes)
		mutScoreImportances <- rbind(mutScoreImportances, as.vector(importance(mutScoreFit))) # add these values as a row to importances

		# Calculate the relative importance of each parameter on the generation time.
		genTimeFit <- randomForest(GenerationTime ~ SatisfyRows + NegateRows + RandomProfile + RandomSeed, data=curRes)
		genTimeImportances <- rbind(genTimeImportances, as.vector(importance(genTimeFit))) # add these values as a row to importances

		# Generate classification tree. Leave out RandomSeed from this to prevent unwanted breaks.
		mutScoreFit <- rpart(MutationScore ~ SatisfyRows + NegateRows + RandomProfile, data=curRes)

		pdf(file=paste0(outputDir,outBaseName,"-MutScore-ClassTree.pdf")) # output to PDF 
		plot(mutScoreFit, uniform=FALSE, main="SchemaAnalyst Parameters Classification Tree",
			sub=paste0("Schema: ",schemaName,", Generator: ", generatorName))
		text(mutScoreFit, use.n=TRUE, all=TRUE, cex=.8)
		dev.off() # close open PDF file

		# Generate conditional inference tree. Leave out RandomSeed from this to prevent unwanted breaks.
		mutScoreFit <- ctree(MutationScore ~ SatisfyRows + NegateRows + RandomProfile, data=curRes)

		pdf(file=paste0(outputDir,outBaseName,"-MutScore-CondInfTree.pdf")) # output to PDF
		plot(mutScoreFit, main="SchemaAnalyst Parameters Conditional Inference Tree",
			sub=paste0("Schema: ",schemaName,", Generator: ", generatorName))
		dev.off() # close open PDF file
	}

	print(paste0("Saving generated visualizations to '",outputDir,"'."))

	# provide collected data related to parameter importance on mutation score
	plot <- qplot(variable, value, data=melt(mutScoreImportances), geom="boxplot", colour=variable, legend=FALSE) +
				xlab("Parameter") + ylab("Importance on Mutation Score") + # add axis labels
				theme(legend.position="none") + # hide the graph legend
				labs(title=paste0("Parameter Importance on Mutation Score\n",nrow(mutScoreImportances)-1," Result Sets")) # add title & subtitle (use size-1 to account for NA row)
	ggsave(filename=paste0(outputDir,"mutation-score-importance.pdf"))

	# provide collected data related to parameter importance on generation time
	plot <- qplot(variable, value, data=melt(genTimeImportances), geom="boxplot", colour=variable) +
				xlab("Parameter") + ylab("Importance on Generation Time") + # add axis labels
				theme(legend.position="none") + # hide the graph legend
				labs(title=paste0("Parameter Importance on Generation Time\n",nrow(genTimeImportances)-1," Result Sets")) # add title & subtitle (use size-1 to account for NA row)
	ggsave(filename=paste0(outputDir,"generation-time-importance.pdf"))
}
