package building;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.RatingValue;
import model.Triple;

/**
 * Singleton that creates a correlation for every review's entry. It correlates
 * a userId to a List of triple itemId,rating,timestamp in a map and viceversa.
 * These two maps represent the training set in a useful way.
 * 
 * @author Rosella Omana Mancilla
 */
public class CorrelationBuilder {

	/**
	 * This is a map that contains for each item's id a list of users that rate
	 * it, with the associated rating and timestamp
	 */
	Map<Integer, List<Triple>> item2user;

	/**
	 * This is a map that contains for each user's id a list of items that they
	 * rated, with the associated rating and timestamp
	 */
	Map<Integer, List<Triple>> users2item;

	private static final CorrelationBuilder _theInstance = new CorrelationBuilder();

	/**
	 * Constructor
	 */
	private CorrelationBuilder() {

		this.item2user = new HashMap<Integer, List<Triple>>();
		this.users2item = new HashMap<Integer, List<Triple>>();

	}

	/**
	 * 
	 * @return
	 */
	public static CorrelationBuilder getInstance() {
		if (_theInstance == null) {
			throw new RuntimeException("No singleton instance available");
		} else {
			return _theInstance;
		}
	}

	/**
	 * @return the item2user
	 */
	public Map<Integer, List<Triple>> getItem2user() {
		return item2user;
	}

	/**
	 * @return the users2item
	 */
	public Map<Integer, List<Triple>> getUsers2item() {
		return users2item;
	}

	/**
	 * @param item2user
	 *            the item2user to set
	 */
	public void setItem2user(final Map<Integer, List<Triple>> item2user) {
		this.item2user = item2user;
	}

	/**
	 * @param users2item
	 *            the users2item to set
	 */
	public void setUsers2item(final Map<Integer, List<Triple>> users2item) {
		this.users2item = users2item;
	}

	/**
	 * This method create two maps that represent the user/item table with
	 * ratings from a given traningSet.dat file
	 * 
	 * @param path_name
	 *            the path of the .dat file
	 */
	public void buildCorrelation(String path_name) {

		if (path_name == null || path_name == "") {
			path_name = ("./data/user_ratedmovies.dat");
			System.out
					.println("TRAINING SET FILE WITH THE GIVEN PATH NOT FOUND. DEFAULT TRAINING SET WILL BE USED (./data/user_ratedmovies.dat).");
		}
		File directory = new File(path_name);
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
				RatingValue rVal = new RatingValue(reviewComponents[2]);
				Long timestamp = Long.parseLong(reviewComponents[3]);
				Triple newTripleUser = new Triple(Integer.parseInt(reviewComponents[0]), rVal, timestamp);
				Triple newTripleItem = new Triple(Integer.parseInt(reviewComponents[1]), rVal, timestamp);
				addToItem2user(newTripleUser, Integer.parseInt(reviewComponents[1]));
				addToUser2item(newTripleItem, Integer.parseInt(reviewComponents[0]));

			} while (true);
			fileReader.close();
			System.out.println("TRAINING SET HAS BEEN EXAMINED.");

		} catch (FileNotFoundException e) {
			System.err.println("NO FILE FOUND WITH THE GIVEN PATH. " + path_name);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

	}

	/**
	 * This method insert a new Triple (userId,rating,timestamp) to the
	 * item2User map for a certain given item
	 * 
	 * @param triple
	 *            the Triple to add
	 * @param itemId
	 *            the id of the given item
	 */
	public void addToItem2user(final Triple triple, final int itemId) {

		List<Triple> tripleList;

		if (item2user.containsKey(itemId)) {

			tripleList = item2user.get(itemId);
		} else {
			tripleList = new LinkedList<Triple>();

		}
		tripleList.add(triple);
		item2user.put(itemId, tripleList);
	}

	/**
	 * This method insert a new Triple (itemId,rating,timestamp) to the
	 * user2item map for a certain given user
	 * 
	 * @param triple
	 *            the Triple to add to the Map
	 * @param userId
	 *            the id of the given user
	 * 
	 */
	private void addToUser2item(final Triple triple, final int userId) {

		List<Triple> tripleList;

		if (users2item.containsKey(userId)) {

			tripleList = users2item.get(userId);
		} else {
			tripleList = new LinkedList<Triple>();

		}
		tripleList.add(triple);
		users2item.put(userId, tripleList);
	}

	/**
	 * This method retrieves the rating of a given user for a certain item
	 * earlier than a certain time, if the given user didn't rate the item 0.0
	 * is returned
	 * 
	 * @param userId
	 *            the id of the given user
	 * @param itemId
	 *            the id of an item
	 * @param timestamp
	 *            time
	 * @return
	 */
	public Double getRatingForItem(final Integer userId, final Integer itemId, final long timestamp) {

		List<Triple> user2raiting = this.item2user.get(itemId);
		if (user2raiting == null) {
			return 0.0;
		}
		Iterator<Triple> it = user2raiting.iterator();
		while (it.hasNext()) {
			Triple current = it.next();
			if (current.getId() == userId && current.getTimestamp() < timestamp) {
				return current.getRaiting().getValue();
			}
		}
		return 0.0;
	}
}
