package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.PairRating;
import model.Triple;

/**
 * Abstract class that calculates Similarity for two objects.
 * 
 * @author Rosella Omana Mancilla
 */
public abstract class SimilarityCalculator {

	/**
	 * This method calculates the similarity of two object given their id.
	 * 
	 * @param objectIDA
	 *            id of the first object
	 * @param objectIDB
	 *            id of the second object
	 * @param timestamp
	 * @return similarity of the two object
	 */
	public Double calculateSimilarity(final Object objectIDA, final Object objectIDB, final long timestamp) {
		return 0.0;
	}

	/**
	 * Calculate mean rating from a list of Triple verifying that the Triple's
	 * timestamp is earlier than a given time
	 * 
	 * @param list
	 *            list of Triple to analyze
	 * @param time
	 *            max time to consider
	 * @return the mean rating
	 */
	public Double calculateAvgTime(final List<Triple> list, final long time) {
		List<Triple> auxList = getEarlierRatings(time, list);
		if (auxList.isEmpty()) {
			/**
			 * an empty list is retrieved when the user/item had never rate an
			 * item or has never been rated, before that time, so is a new user
			 * and 0.0 is returned.
			 */

			return 0.0;
		}
		return getAvgRating(auxList);
	}

	/**
	 * This method calculate the average rating of a given List of Triples
	 * 
	 * @param tripleList
	 *            a list of Triple
	 * 
	 * @return the average value
	 */
	public Double getAvgRating(final List<Triple> tripleList) {
		double resultDoubleAvg = 0.0;
		for (Triple t : tripleList) {
			resultDoubleAvg = resultDoubleAvg + t.getRaiting().getValue();
		}

		if (resultDoubleAvg == 0.0) {
			return 0.0;
		}
		resultDoubleAvg = resultDoubleAvg / tripleList.size();

		return resultDoubleAvg;

	}

	/**
	 * This method eliminate all Triple that has a timestamp older than a given
	 * time from a list
	 * 
	 * @param timestamp
	 *            given time
	 * @param tripleList
	 *            the list to modify
	 * @return the modified list
	 */
	public List<Triple> getEarlierRatings(final long timestamp, final List<Triple> tripleList) {
		Iterator<Triple> it = tripleList.iterator();

		while (it.hasNext()) {
			Triple t = it.next();
			if (t.getTimestamp() > timestamp) {
				it.remove();
			}
		}
		return tripleList;

	}

	/**
	 * This method returns a list of PairRating from two given list of Triple
	 * representing the rating of two user for the same item
	 * 
	 * @param firtsList
	 *            first user's or item's list of Triple
	 * @param secondList
	 *            second user's or item's list of Triple
	 * @return a list of rating pairs
	 */

	public List<PairRating> getRatingsSameObject(final List<Triple> firtsList, final List<Triple> secondList) {

		List<PairRating> result = new LinkedList<PairRating>();

		for (Triple t1 : firtsList) {
			for (Triple t2 : secondList) {
				if (t1.getId().equals(t2.getId())) {

					PairRating new_pairR = new PairRating(t1.getRaiting(), t2.getRaiting());
					result.add(new_pairR);
				}
			}
		}
		return result;
	}

}
