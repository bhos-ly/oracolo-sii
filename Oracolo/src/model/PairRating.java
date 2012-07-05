package model;

/**
 * This type is a tuple of two RatingValues, representing rating of two
 * different user for the same item.
 * 
 * @author Rosella Omana Mancilla
 */
public class PairRating {

	RatingValue ratingUID1;
	RatingValue ratingUID2;

	/**
	 * Constructor
	 * 
	 * @param rUID1
	 *            rating first user
	 * @param rUID2
	 *            rating second user
	 */
	public PairRating(final RatingValue rUID1, final RatingValue rUID2) {
		this.ratingUID1 = rUID1;
		this.ratingUID2 = rUID2;
	}

	/**
	 * @return the ratingUID1
	 */
	public RatingValue getRatingUID1() {
		return ratingUID1;
	}

	/**
	 * @return the ratingUID2
	 */
	public RatingValue getRatingUID2() {
		return ratingUID2;
	}

}
