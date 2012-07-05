/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (c) I-Smart S.R.L., 2012
 *
 * This unpublished material is proprietary to I-Smart S.R.L.
 * All rights reserved. The methods and techniques described
 * herein are considered trade secrets and/or confidential.
 * Reproduction or distribution, in whole  or in part, is
 * forbidden except by express written permission of I-Smart S.R.L.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package main;

import main.impl.RecommenderImpl;

/**
 * Main class. Launching the main with inputFile.dat and outputFile.dat
 * OracoloMain writes in outputFile.dat the predictions for a triple
 * userId,itemId,timestamp. (eg. java -jar inputFile.dat outputFile.dat)
 * 
 * @author Rosella Omana Mancilla
 */
public class OracoloMain {

	public static void main(final String[] args) {
		String inputFile;
		String outputFile;
		String trainingFile;
		// DataSetGenerator generator = new
		// DataSetGenerator("./data/user_ratedmovies.dat");
		if (args.length > 2) {
			inputFile = args[0];
			outputFile = args[1];
			trainingFile = args[2];

			Recommender recommender = new RecommenderImpl();
			recommender.predictForFile(inputFile, outputFile, trainingFile);

			ErrorCaltulator errCalc = new ErrorCaltulator(outputFile);

			System.out.println("\nMean Absolute Error = " + errCalc.getMae());
			System.out.println("\nRoot Mean Absolute Error = " + errCalc.getRmse());
			System.out.println("\nMean Absolute Percentage Error = " + errCalc.getMape() + "%");
		} else {
			System.out
					.println("MISSING INPUT VALUES. RELAUNCH WITH inputFile.dat and outputFile.dat\nLike java -jar Oracolo.jar inputFile.dat outputFile.dat trainingSet.dat");
		}

	}

	/**
	 * To launch as test you can create a training set and a test set using
	 * DataSetGenerator then call Recommender.predictForFile() with
	 * DataSetGenerator static field testsetpath and traningsetpath as
	 * parameters.
	 */
	// public static void main(final String[] args) {
	//
	// String outputFile = "./data/output.data";
	// DataSetGenerator generator = new
	// DataSetGenerator("./data/user_ratedmovies.dat");
	// Recommender recommender = new RecommenderImpl();
	// recommender.predictForFile(DataSetGenerator.getTestsetpath(), outputFile,
	// DataSetGenerator.getTraningsetpath());
	//
	// ErrorCaltulator errCalc = new ErrorCaltulator(outputFile);
	//
	// System.out.println("\nMean Absolute Error = " + errCalc.getMae());
	// System.out.println("\nRoot Mean Absolute Error = " + errCalc.getRmse());
	// System.out.println("\nMean Absolute Percentage Error = " +
	// errCalc.getMape() + "%");
	//
	// }
}
