package main.impl;

import java.util.List;

import main.SimilarityCalculator;
import model.PairRating;
import model.Triple;
import building.CorrelationBuilder;

/**
 * Similarity calculator between two users.
 * 
 * @author Rosella Omana Mancilla
 */
public class PersonSimilarityCalculator extends SimilarityCalculator {

	private CorrelationBuilder builder;

	private static final PersonSimilarityCalculator _theInstance = new PersonSimilarityCalculator();

	/**
	 * Constructor
	 */
	private PersonSimilarityCalculator() {
		this.builder = CorrelationBuilder.getInstance();
	}

	/**
	 * 
	 * @return
	 */
	public static PersonSimilarityCalculator getInstance() {
		if (_theInstance == null) {
			throw new RuntimeException("No singleton instance available");
		} else {
			return _theInstance;
		}
	}

	/**
	 * @return the builder
	 */
	public CorrelationBuilder getBuilder() {

		return builder;
	}

	/**
	 * @param builder
	 *            the builder to set
	 */
	public void setBuilder(final CorrelationBuilder builder) {
		this.builder = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.SimilarityCalculator#calculateSimilarity(java.lang.Object,
	 * java.lang.Object, long)
	 */
	@Override
	public Double calculateSimilarity(final Object objectIDA, final Object objectIDB, final long timestamp) {

		if (objectIDA.getClass() != Integer.class || objectIDB.getClass() != Integer.class) {
			return null;
		}

		List<Triple> tripleListA = builder.getUsers2item().get(objectIDA);
		List<Triple> tripleListB = builder.getUsers2item().get(objectIDB);
		tripleListA = getEarlierRatings(timestamp, tripleListA);
		tripleListB = getEarlierRatings(timestamp, tripleListB);

		Double avgA = getAvgRating(tripleListA);
		Double avgB = getAvgRating(tripleListB);

		List<PairRating> pairRatingLists = getRatingsSameObject(tripleListB, tripleListA);

		Double num = 0.0, denom1 = 0.0, denom2 = 0.0;
		Double val1 = 0.0;
		Double val2 = 0.0;

		/**
		 * NO CORRELATION
		 */
		if (pairRatingLists.isEmpty()) {
			return 0.0;
		}
		for (PairRating pair : pairRatingLists) {

			val1 = pair.getRatingUID1().getValue() - avgA;
			val2 = pair.getRatingUID2().getValue() - avgB;
			num += val1 * val2;

			denom1 += val1 * val1;
			denom2 += val2 * val2;
		}

		Double denominator = Math.sqrt(denom1) + Math.sqrt(denom2);
		return num / denominator;
	}

}
