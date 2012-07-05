package main;

import model.RatingValue;

/**
 * Predictor Interface.
 * 
 * @author Rosella Omana Mancilla
 */
public interface Recommender {

	/**
	 * Predicts rating of a given user for a given item in a certain moment
	 * based on ratings of other similar users
	 * 
	 * @param userId
	 *            identifier of the user
	 * @param itemId
	 *            identifier of the item
	 * @param timestamp
	 *            moment of the rating
	 * @return a prediction rating value
	 */
	RatingValue getUserPrediction(Integer userId, Integer itemId, long timestamp);

	/**
	 * Predicts rating of an given user for a given item in a certain moment
	 * based on ratings of other similar items
	 * 
	 * @param userId
	 *            identifier of the user
	 * @param itemId
	 *            identifier of the item
	 * @param timestamp
	 *            moment of the rating
	 * @return a prediction rating value
	 */
	RatingValue getItemPrediction(final Integer userId, final Integer itemId, final long timestamp);

	/**
	 * Predicts ratings from a file .dat of triple (userId, itemId, timestamp)
	 * 
	 * @param inputFile
	 *            path of the file
	 * @param outputFile
	 *            output file path
	 * @param trainingFile
	 *            training file path
	 */
	void predictForFile(String inputFile, String outputFile, String trainingFile);

}
