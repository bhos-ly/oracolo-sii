package model;

/**
 * This type represent a input review, all entry of an input file to OracoloMain
 * are stored into an InputTriple, in addition
 * 
 * @author Rosella Omana Mancilla
 */
public class InputTriple {

	private Integer userId;
	private Integer itemId;
	private RatingValue rating;
	private long timestamp;

	public InputTriple(final Integer userId, final Integer itemId, final RatingValue rating, final long timestamp) {

		this.userId = userId;
		this.itemId = itemId;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(final Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the rating
	 */
	public RatingValue getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(final RatingValue rating) {
		this.rating = rating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InputTriple [userId=" + userId + ", itemId=" + itemId + ", rating=" + rating + ", timestamp=" + timestamp + "]";
	}

}
