package main.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import main.Recommender;
import main.SimilarityCalculator;
import model.InputTriple;
import model.RatingValue;
import model.Triple;
import building.CorrelationBuilder;

/**
 * An implementation of Recommender.
 * 
 * @author Rosella Omana Mancilla
 */
public class RecommenderImpl implements Recommender {

	private CorrelationBuilder builder;

	private SimilarityCalculator simPersonCalculator;

	private final SimilarityCalculator simItemCalculator;

	public RecommenderImpl() {
		this.builder = CorrelationBuilder.getInstance();
		PersonSimilarityCalculator calculatorUser = PersonSimilarityCalculator.getInstance();
		ItemSimilarityCalculator calculatorItem = ItemSimilarityCalculator.getInstance();
		calculatorUser.setBuilder(builder);
		this.simPersonCalculator = calculatorUser;

		calculatorItem.setBuilder(builder);
		this.simItemCalculator = calculatorItem;

	}

	/**
	 * @return the builder
	 */
	public CorrelationBuilder getBuilder() {
		return builder;
	}

	/**
	 * @return the simCalculator
	 */
	public SimilarityCalculator getSimPersonCalculator() {
		return simPersonCalculator;
	}

	/**
	 * @param builder
	 *            the builder to set
	 */
	public void setBuilder(final CorrelationBuilder builder) {
		this.builder = builder;
	}

	/**
	 * @param simCalculator
	 *            the simCalculator to set
	 */
	public void setSimPersonCalculator(final SimilarityCalculator simCalculator) {
		this.simPersonCalculator = simCalculator;
	}

	@Override
	public RatingValue getUserPrediction(final Integer userId, final Integer mouvieId, final long timestamp) {
		List<Triple> tripleList = this.builder.getUsers2item().get(userId);
		Double avgUserId = this.simPersonCalculator.calculateAvgTime(tripleList, timestamp);

		Set<Integer> setKey = this.builder.getUsers2item().keySet();
		Double numTot = 0.0, den = 0.0;
		Double ratingBItem, simUserId, ratingBAVG;
		for (Integer userIdkey : setKey) {
			ratingBItem = this.builder.getRatingForItem(userIdkey, mouvieId, timestamp);
			if (ratingBItem != 0.0) {
				simUserId = this.simPersonCalculator.calculateSimilarity(userId, userIdkey, timestamp);
				List<Triple> currentTripleList = this.builder.getUsers2item().get(userIdkey);
				ratingBAVG = this.simPersonCalculator.calculateAvgTime(currentTripleList, timestamp);

				if (simUserId > 0) { // aggiungi alla formula solo gli user che
										// hanno sim maggiore di zero
					numTot += (simUserId * (ratingBItem - ratingBAVG));
					den += simUserId;
				}
			}
		}
		/**
		 * Default value for is mean rating of the user if not 0, otherwise 2.5
		 * is given.
		 */
		if (den == 0) { // there are no similar users
			if (avgUserId == 0.0) { // user is new
				RatingValue result = new RatingValue(0.0);
				result.setRating4NewUser(true);
				result.setMedia(false);
				return result;
			} else {	// user is not new but has not similar user
				RatingValue result = new RatingValue(avgUserId);
				result.setRating4NewUser(false);
				result.setMedia(true);
				return result;
			}
		} else {
			RatingValue result = new RatingValue(avgUserId + (numTot / den));
			result.setMedia(false);
			result.setRating4NewUser(false);
			return result;
		}
	}

	@Override
	public RatingValue getItemPrediction(final Integer userId, final Integer mouvieId, final long timestamp) {
		List<Triple> tripleList = this.builder.getItem2user().get(mouvieId);
		Double avgItemId;
		if (tripleList == null || tripleList.isEmpty()) {
			avgItemId = 0.0;
		} else {
			avgItemId = this.simItemCalculator.calculateAvgTime(tripleList, timestamp);
		}
		Set<Integer> setKey = this.builder.getItem2user().keySet();
		Double numTot = 0.0, den = 0.0;
		Double ratingBItem, simItemId, ratingBAVG;
		for (Integer itemIdkey : setKey) {
			ratingBItem = this.builder.getRatingForItem(itemIdkey, mouvieId, timestamp);
			if (ratingBItem != 0.0) {
				simItemId = this.simItemCalculator.calculateSimilarity(mouvieId, itemIdkey, timestamp);
				List<Triple> currentTripleList = this.builder.getItem2user().get(itemIdkey);
				ratingBAVG = this.simItemCalculator.calculateAvgTime(currentTripleList, timestamp);

				if (simItemId > 0) { // aggiungi alla formula solo gli user che
										// hanno sim maggiore di zero
					numTot += (simItemId * (ratingBItem - ratingBAVG));
					den += simItemId;
				}
			}

		}

		if (den == 0) {
			if (avgItemId == 0.0) {
				RatingValue result = new RatingValue(2.5);
				result.setRating4NewUser(true);
				result.setMedia(false);
				return result;
			} else {
				RatingValue result = new RatingValue(avgItemId);
				result.setRating4NewUser(false);
				result.setMedia(true);
				return result;
			}
		} else {
			RatingValue result = new RatingValue(avgItemId + (numTot / den));
			result.setMedia(false);
			result.setRating4NewUser(false);
			return result;
		}
	}

	/**
	 * From an input file a List of reviews (InputTriple) is generated to
	 * contain all the information in the file
	 * 
	 * @param pathFile
	 * @return a list of InputTiple that represent
	 */
	private List<InputTriple> getInputFile(final String pathFile) {
		List<InputTriple> resultList = new LinkedList<InputTriple>();
		File directory = new File(pathFile);
		FileReader fileReader;
		BufferedReader bReader;
		try {

			fileReader = new FileReader(directory);
			bReader = new BufferedReader(fileReader);
			String nextLine = bReader.readLine();
			do {
				nextLine = bReader.readLine();

				if (nextLine == null) {
					break;
				}

				String[] reviewComponents = nextLine.split("\t", 4);
				Long timestamp = Long.parseLong(reviewComponents[2]);
				RatingValue rating = null;
				if (reviewComponents[3] != null) {
					rating = new RatingValue(Double.parseDouble(reviewComponents[3]));
				}
				InputTriple newTripleUser =
						new InputTriple(Integer.parseInt(reviewComponents[0]), Integer.parseInt(reviewComponents[1]), rating, timestamp);
				resultList.add(newTripleUser);
			} while (true);
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("NO INPUT FILE FOUND WITH THE GIVEN PATH. " + pathFile);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		return resultList;
	}

	@Override
	public void predictForFile(final String inputFile, final String outputFile, final String trainingFile) {

		List<InputTriple> inputRows = this.getInputFile(inputFile);

		File output;
		FileWriter fileWriter = null;
		try {
			output = new File(outputFile);
			if (!output.exists()) {
				output.createNewFile();
				System.out.println("OUTPUT FILE CREATED.\n");

			}
			fileWriter = new FileWriter(output);

			fileWriter.write("userID\tmovieId\ttrueRating\trating\n");
			this.builder.buildCorrelation(trainingFile);
			String stringToWrite, stringToWrite2 = "";
			System.out.println("PREDICTION STARTED...");
			RatingValue itemPrediction = null;
			for (InputTriple input : inputRows) {
				RatingValue personPrediction = this.getUserPrediction(input.getUserId(), input.getItemId(), input.getTimestamp());
				if (personPrediction.isRating4NewUser()) {
					itemPrediction = this.getItemPrediction(input.getUserId(), input.getItemId(), input.getTimestamp());
					stringToWrite2 = "\t " + itemPrediction.getValue();
				} else {
					stringToWrite2 = "\t" + personPrediction.getValue();
				}
				stringToWrite = input.getUserId() + "\t" + input.getItemId() + "\t" + input.getRating().getValue() + stringToWrite2;

				fileWriter.write(stringToWrite + "\n");
				stringToWrite2 = "";
			}
			fileWriter.close();
			System.out.println("PREDICTION ENDED.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
