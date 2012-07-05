/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (c) I-Smart S.R.L., 2012
 *
 * This unpublished material is proprietary to I-Smart S.R.L.
 * All rights reserved. The methods and techniques described
 * herein are considered trade secrets and/or confidential.
 * Reproduction or distribution, in whole  or in part, is
 * forbidden except by express written permission of I-Smart S.R.L.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package main.impl;

import java.util.List;

import main.SimilarityCalculator;
import model.PairRating;
import model.Triple;
import building.CorrelationBuilder;

/**
 * Similarity calculator between two items.
 * 
 * @author Rosella Omana Mancilla
 */
public class ItemSimilarityCalculator extends SimilarityCalculator {

	private CorrelationBuilder builder;

	private static final ItemSimilarityCalculator _theInstance = new ItemSimilarityCalculator();

	/**
	 * Constructor
	 */
	private ItemSimilarityCalculator() {
		this.builder = CorrelationBuilder.getInstance();
	}

	/**
	 * 
	 * @return
	 */
	public static ItemSimilarityCalculator getInstance() {
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
		List<Triple> tripleListA = builder.getItem2user().get(objectIDA);
		List<Triple> tripleListB = builder.getItem2user().get(objectIDB);
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
