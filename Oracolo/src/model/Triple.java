package model;

/**
 * This type is a triple consists of an id, a ratings and a timestamp,
 * representing a review (userId, itemId, timestamp, rating) without one of the
 * two id. It's used to store in a map all reviews, using the missing id as key
 * of the map. In fact the map contains for all userId/itemId a list of Triple.
 * 
 * @author Rosella Omana Mancilla
 */

public class Triple {

	Integer id;
	RatingValue raiting;
	long timestamp;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            an id
	 * @param raiting
	 *            the rating
	 * @param timestamp
	 *            th time of the review
	 */
	public Triple(final int id, final RatingValue raiting, final long timestamp) {

		this.id = id;
		this.raiting = raiting;
		this.timestamp = timestamp;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the raiting
	 */
	public RatingValue getRaiting() {
		return raiting;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Triple [id=" + id + ", raiting=" + raiting + ", timestamp=" + timestamp + "]";
	}

}
