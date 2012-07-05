package model;

import java.math.BigDecimal;

/**
 * Particular type of Double, RatingValues can only be one of these values (
 * 0.5, 1.0, 1.5, 2.0, 2.5, 3.5, 4.0, 4.5, 5.0 ), they represent a rate of a
 * user for an item.
 * 
 * @author Rosella Omana Mancilla
 */
public class RatingValue {

	// 0.5, 1.0, 1.5, 2.0, 2.5, 3.5, 4.0, 4.5, 5.0

	private Double value;
	private boolean rating4NewUser;
	private boolean media;

	public Double getValue() {
		return value;
	}

	/**
	 * @return the media
	 */
	public boolean isMedia() {
		return media;
	}

	/**
	 * @param media
	 *            the media to set
	 */
	public void setMedia(final boolean media) {
		this.media = media;
	}

	public RatingValue(final String raiting) {
		value = Double.parseDouble(raiting);

	}

	public RatingValue(Double value) {
		/*
		 * DecimalFormat twoDForm = new DecimalFormat("#,###"); this.value =
		 * Double.valueOf(twoDForm.format(value));
		 */
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(1, BigDecimal.ROUND_UP);// 2 - decimal places
		value = bd.doubleValue();

		// System.out.println("hh = "+(int)this.value.intValue());
		Double decimal = (value - value.intValue()) * 10;
		switch (decimal.intValue()) {

			case 0:
				this.value = Double.parseDouble(value.intValue() + ".0");
				break;
			case 1:
				this.value = Double.parseDouble(value.intValue() + ".0");
				break;
			case 2:
				this.value = Double.parseDouble(value.intValue() + ".0");
				break;
			case 3:
				this.value = Double.parseDouble(value.intValue() + ".0");
				break;
			case 4:
				this.value = Double.parseDouble(value.intValue() + ".0");
				break;
			case 5:
				this.value = Double.parseDouble(value.intValue() + ".5");
				break;
			case 6:
				this.value = Double.parseDouble(value.intValue() + ".5");
				break;
			case 7:
				this.value = Double.parseDouble(value.intValue() + ".5");
				break;
			case 8:
				this.value = Double.parseDouble(value.intValue() + ".5");
				break;
			case 9:
				this.value = Double.parseDouble(value.intValue() + ".5");
				break;
			default:
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RatingValue [value=" + value + ", rating4NewUser=" + rating4NewUser + ", media=" + media + "]";
	}

	/**
	 * @return the fixedValue
	 */
	public boolean isRating4NewUser() {
		return rating4NewUser;
	}

	/**
	 * @param fixedValue
	 *            the fixedValue to set
	 */
	public void setRating4NewUser(final boolean fixedValue) {
		this.rating4NewUser = fixedValue;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// return "RatingValue [value=" + value + ", media=" + media + "]";
	// }

}
