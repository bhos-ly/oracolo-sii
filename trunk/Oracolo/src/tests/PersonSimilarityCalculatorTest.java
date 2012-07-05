/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (c) I-Smart S.R.L., 2012
 *
 * This unpublished material is proprietary to I-Smart S.R.L.
 * All rights reserved. The methods and techniques described
 * herein are considered trade secrets and/or confidential.
 * Reproduction or distribution, in whole  or in part, is
 * forbidden except by express written permission of I-Smart S.R.L.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import main.impl.PersonSimilarityCalculator;
import model.PairRating;
import model.RatingValue;
import model.Triple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import building.CorrelationBuilder;

/**
 * Test class for PersonSimilarityCalculator.
 * 
 * @date: $Date$
 * @revision: $Revision$
 * @author: $Author$
 * 
 *          File : $HeadURL$
 * 
 */
public class PersonSimilarityCalculatorTest extends TestCase {

	List<Triple> tripleListEarlier;
	List<Triple> tripleListLater;
	PersonSimilarityCalculator personSimilatyCalc;

	List<Triple> tripleSameItem1;
	List<Triple> tripleSameItem2;

	private long time;
	private long timeAfter;
	private long timeBefore;

	@Override
	@Before
	public void setUp() throws Exception {

		personSimilatyCalc = PersonSimilarityCalculator.getInstance();
		time = 1162160;
		timeAfter = time + 1;
		timeBefore = time - 1;
		this.tripleListEarlier = auxCreateListTriple(time);

		this.tripleListLater = auxCreateListTriple(time);

		this.tripleSameItem1 = auxCreateListTriple(time);
		this.tripleSameItem2 = auxCreateListTriple(time);
	}

	/**
	 * This test verifies if all Triple in the list have an earlier timestamp
	 * than a given one, the list is the same
	 */
	public void testGetEarlierRatingsFull() {

		int sizeBefore = this.tripleListEarlier.size();
		tripleListEarlier = personSimilatyCalc.getEarlierRatings(timeAfter, tripleListEarlier);
		int sizeAfter = this.tripleListEarlier.size();
		Assert.assertEquals(sizeBefore, sizeAfter);

	}

	/**
	 * This test verifies if all Triple in the list have older timestamp than a
	 * given one, the list is modified and in this case is empty
	 */
	public void testGetEarlierRatingsEmpty() {

		int sizeBefore = this.tripleListLater.size();
		tripleListLater = personSimilatyCalc.getEarlierRatings(timeBefore, tripleListLater);
		int sizeAfter = this.tripleListLater.size();
		Assert.assertFalse(sizeBefore == sizeAfter);
		Assert.assertTrue(this.tripleListLater.isEmpty());

	}

	/**
	 * This test verifies if in the two list there're rating for same items
	 * Inputs of the method are two list with same content
	 */
	public void testGetRatingsSameItems() {

		List<PairRating> result = personSimilatyCalc.getRatingsSameObject(tripleSameItem1, tripleSameItem2);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(tripleSameItem1.size(), result.size());
	}

	/**
	 * This test verifies if in the two list there're ratings for same items
	 * Inputs of the method are two list, one of them is empty Output is empty
	 * list
	 */
	public void testGetRatingsSameItemsNoSame() {

		List<PairRating> result = personSimilatyCalc.getRatingsSameObject(tripleSameItem1, new LinkedList<Triple>());
		Assert.assertTrue(result.isEmpty());
	}

	/**
	 * This test verifies if in the two list there're ratings for same items
	 * Inputs of the method are two list, one of them is empty Output is empty
	 * list
	 */
	public void testGetRatingsSameItemsNoSame2() {

		List<PairRating> result = personSimilatyCalc.getRatingsSameObject(new LinkedList<Triple>(), tripleSameItem1);
		Assert.assertTrue(result.isEmpty());
	}

	/**
	 * This auxiliary method create a list of Triple with a given timestamp
	 * 
	 * @param time
	 *            timestamp
	 * @return a list of Triples
	 */
	private List<Triple> auxCreateListTriple(final long time) {
		List<Triple> resultList = new LinkedList<Triple>();
		Triple newTriple;
		for (int i = 0; i < 5; i++) {
			newTriple = new Triple(i, new RatingValue(2.5), time);
			resultList.add(newTriple);

		}
		return resultList;
	}

	/**
	 * This auxiliary method create a list of Triple with a given timestamp and
	 * a given start id value
	 * 
	 * @param time
	 *            timestam
	 * @param startId
	 *            a startig value for the fist element of the list
	 * @return a list of Triples
	 */
	private List<Triple> auxCreateListTripleRandomRating(final long time, final int startId) {
		List<Triple> resultList = new LinkedList<Triple>();
		Triple newTriple;
		for (int i = startId; i < startId + 5; i++) {
			newTriple = new Triple(i, new RatingValue(Math.random() * 5), time + i);
			resultList.add(newTriple);

		}
		return resultList;
	}

	/**
	 * This auxiliary method create a map of Integer - list of Triple created
	 * with auxCreateListTripleRandomRating with a start id number of Triple and
	 * a start id number for the keys of the map number.
	 * 
	 * @param startId
	 *            starting id of the
	 * @param
	 * @param startIdTriples
	 * @return a map of <integer , list of Triples>
	 */
	public Map<Integer, List<Triple>> auxCreateMap(final int startId, final int startIdTriples) {
		Map<Integer, List<Triple>> result = new HashMap<Integer, List<Triple>>();

		for (int j = startId; j < startId + 5; j++) {
			List<Triple> newList = auxCreateListTripleRandomRating(time, startIdTriples);
			result.put(j, newList);
		}
		return result;
	}

	/**
	 * This method recreate an inverse map from a given map. If map is
	 * a->listOf(b), inverse map is b->listOf(a)
	 * 
	 * @param map
	 *            the map to transform
	 * @return inverse map
	 */
	public Map<Integer, List<Triple>> auxCreateInversMap(final Map<Integer, List<Triple>> map) {

		Map<Integer, List<Triple>> result = new HashMap<Integer, List<Triple>>();
		Set<Integer> keys = map.keySet();
		Iterator<Integer> i = keys.iterator();
		while (i.hasNext()) {
			Integer currentKey = i.next();
			List<Triple> firstLine = map.get(currentKey);
			Iterator<Triple> iList = firstLine.iterator();
			List<Triple> newList;
			while (iList.hasNext()) {
				Triple t = iList.next();
				Triple newInversTriple = new Triple(currentKey, t.getRaiting(), t.getTimestamp());

				if (result.containsKey(t.getId())) {
					newList = result.get(t.getId());
					newList.add(newInversTriple);
				} else {
					newList = new LinkedList<Triple>();
					newList.add(newInversTriple);

				}
				result.put(t.getId(), newList);
			}
		}
		return result;

	}

	/**
	 * Test for calculateSimilarity of two equal id, the user/item table is
	 * full.
	 */
	@Test
	public void testCalculateSimilaritySameId() {

		Map<Integer, List<Triple>> user2ItemMap = auxCreateMap(480, 340);
		Map<Integer, List<Triple>> item2UserMap = auxCreateInversMap(user2ItemMap);
		CorrelationBuilder builder = personSimilatyCalc.getBuilder();
		builder.setItem2user(item2UserMap);
		builder.setUsers2item(user2ItemMap);

		Double j = personSimilatyCalc.calculateSimilarity(480, 480, time + 1000);

		Assert.assertTrue(j >= 1);
	}

	/**
	 * Test for calculateSimilarity of two different user, the user/item table
	 * is full.
	 */
	@Test
	public void testCalculateSimilarity() {

		Map<Integer, List<Triple>> user2ItemMap = auxCreateMap(480, 340);
		Map<Integer, List<Triple>> item2UserMap = auxCreateInversMap(user2ItemMap);
		CorrelationBuilder builder = personSimilatyCalc.getBuilder();
		builder.setItem2user(item2UserMap);
		builder.setUsers2item(user2ItemMap);

		Double j = personSimilatyCalc.calculateSimilarity(480, 482, time + 1000);
		Assert.assertFalse(j >= 1);
	}
}
