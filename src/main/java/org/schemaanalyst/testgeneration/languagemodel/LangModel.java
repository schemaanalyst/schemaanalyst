package org.schemaanalyst.testgeneration.languagemodel;

/**

Class applies language model to score strings 

**/

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LangModel {

	// class variables
	// Hashes storing various Language Model probabilities
	private Map<String, Double> unigram_probs = new HashMap<String, Double>();
	private Map<String, Double> unigram_backoff_probs = new HashMap<String, Double>();
	private Map<String, Double> bigram_probs = new HashMap<String, Double>();

	// Estimated probability of unknown character
	double unknown_char_prob;

	// Constructors
	// Read in data from language model to be manipulated later
	// Takes language model file as argument
	public LangModel(String lmFileName) throws IOException {

		// Flag to indicate length of n-grams currently being read (0 == read
		// nothing)
		int ngram_len = 0;

		// Keep track of highest unigram prob (for dealing with whitespace)
		// and initilaise probability of unknown character
		double highest_unigram_prob = -1000;
		unknown_char_prob = 0;

		FileInputStream fstream = new FileInputStream(lmFileName);

		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;

		// Regular expressions setup
		Pattern ngram_len_p = Pattern.compile("(\\d+)-grams:");
		Pattern unigram_p = Pattern.compile("(-[0-9\\.]+)\\s*(\\S)\\s*(-[0-9\\.]+)");
		Pattern bigram_p = Pattern.compile("(-[0-9\\.]+)\\s*(\\S+) (\\S+)");

		// Read file line by line
		while ((strLine = br.readLine()) != null) {

			Matcher match_ngram_len = ngram_len_p.matcher(strLine);
			if (match_ngram_len.find()) {
				ngram_len = Integer.parseInt(match_ngram_len.group(1));
			} else if (ngram_len == 1) {
				Matcher match_unigram = unigram_p.matcher(strLine);
				if (match_unigram.find()) {

					// ** Could simplify this code by not bothering to parse
					// Double??? **
					double unigram_prob = Double.parseDouble(match_unigram.group(1));
					String unigram = match_unigram.group(2);
					double unigram_backoff_prob = Double.parseDouble(match_unigram.group(3));

					unigram_probs.put(unigram, Double.valueOf(unigram_prob));
					unigram_backoff_probs.put(unigram, Double.valueOf(unigram_backoff_prob));

					// Keep track of highest and lowest probs
					if (unigram_prob < unknown_char_prob) {
						unknown_char_prob = unigram_prob;
					} // if
					if (unigram_prob > highest_unigram_prob) {
						highest_unigram_prob = unigram_prob;
					} // if

					// System.out.println("*** match ***: unigram: "+unigram+"
					// prob:"+unigram_prob+" backoff
					// prob:"+unigram_backoff_prob);

				} // if
			} else if (ngram_len == 2) {
				Matcher match_bigram = bigram_p.matcher(strLine);
				if (match_bigram.find()) {
					double bigram_prob = Double.parseDouble(match_bigram.group(1));
					String bigram = match_bigram.group(2) + match_bigram.group(3);

					bigram_probs.put(bigram, Double.valueOf(bigram_prob));

					// System.out.println("*** match *** bigram: "+bigram+"
					// prob: "+bigram_prob);

				} // if
			} // if/else

		} // while
			// Close the input stream
		in.close();

		// Store whitespace probabilities (estimated as highest observed unigram
		// probability)
		String whitespace = " ";
		unigram_probs.put(whitespace, Double.valueOf(highest_unigram_prob));
		unigram_backoff_probs.put(whitespace, Double.valueOf(highest_unigram_prob));

	} // LangModel

	// Method which returns language model score for string str
	// Splits string into bigrams and looks up the probability for each. If the
	// bigram isn't found then
	// backs off to use the unigram and backoff probabilities
	// str is string for which score is computed, verbose is flag indicating
	// whether to print out details
	// about how this score is computed
	public double score(String str, boolean verbose) {

		if (verbose == true) {
			System.out.println("String is " + str);
		} // if

		double log_prob = 0;

		// Get length of string
		int no_chars = str.length();

		// Break string down into bigrams
		for (int i = 0; i < (no_chars - 1); i++) {
			String bigram = str.substring(i, i + 2);

			if (verbose == true) {
				System.out.println("Bigram is " + bigram);
			} // if

			// Get negative log likelihood for each bigram
			// (Either get directly or estimate using backoff)

			if (bigram_probs.containsKey(bigram)) {
				// Get direct bigram probabilities
				double bigram_prob = (bigram_probs.get(bigram)).doubleValue();
				log_prob = log_prob + bigram_prob;
				if (verbose == true) {
					System.out.println("Direct bigram prob: " + Math.pow(10, bigram_prob) + "\n");
				} // if
			} else {

				// Otherwise split into unigrams and do backoff
				String first_char = bigram.substring(0, 1);
				String second_char = bigram.substring(1, 2);

				double unigram_backoff_prob;
				if (unigram_backoff_probs.containsKey(first_char)) {
					unigram_backoff_prob = (unigram_backoff_probs.get(first_char)).doubleValue();
				} else {
					unigram_backoff_prob = unknown_char_prob;
				} // if/else
				log_prob = log_prob + unigram_backoff_prob;
				// System.out.println("Unigram ("+first_char+") backoff prob:
				// "+unigram_backoff_prob);

				double unigram_prob;
				if (unigram_probs.containsKey(second_char)) {
					unigram_prob = (unigram_probs.get(second_char)).doubleValue();
				} else {
					unigram_prob = unknown_char_prob;
				} // if/else
				log_prob = log_prob + unigram_prob;
				if (verbose == true) {
					double bigram_prob = unigram_backoff_prob + unigram_prob;
					System.out.println("Inferred bigram prob: " + Math.pow(10, bigram_prob)
							+ " (formed from unigram probs " + first_char + ": " + Math.pow(10, unigram_backoff_prob)
							+ " and " + second_char + ": " + Math.pow(10, unigram_prob) + ")\n");
				} // if
			} // if/else

		} // for

		// Convert log probs to probs and take geometric mean
		// N.B. If compute probability directly and then take root start to
		// encounter problems
		// with underflow
		double avg_prob = Math.pow(10, log_prob / ((double) no_chars));

		return avg_prob;

	} // score

}

