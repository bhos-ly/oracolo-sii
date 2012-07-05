package tests;

import java.util.LinkedList;

import junit.framework.TestCase;
import model.RatingValue;
import model.Triple;

import org.junit.Before;
import org.junit.Test;

import building.CorrelationBuilder;

/**
 * CorrelationBuilder test class.
 * 
 * @author Rosella Omana Mancilla
 */
public class CorrelationBuilderTest extends TestCase {

	CorrelationBuilder builder;
	Triple t1, t2, t3;

	@Override
	@Before
	public void setUp() throws Exception {
		t1 = new Triple(6, new RatingValue(1.5), 832764);
		t2 = new Triple(5, new RatingValue(1.0), 632764);
		builder = CorrelationBuilder.getInstance();
	}

	/**
	 * Test 1: from an empty map addToItem2user inserts an element
	 */
	@Test
	public void test_addToItem2user_emptyMap() {

		builder.addToItem2user(t1, 4);
		assertFalse(builder.getItem2user().isEmpty());
		assertEquals(1, builder.getItem2user().size());
	}

	/**
	 * Da una mappa vuota inseriscoo 2 coppie differenti con indici diversi La
	 * mappa ha ora 2 entry
	 */

	/**
	 * Test 2: from an empty map two call of addToItem2user insert two elements
	 * to the map
	 */
	@Test
	public void test_addToItem2user_2diff_entry() {
		builder.getItem2user().clear();
		builder.getUsers2item().clear();
		builder.addToItem2user(t1, 4);
		builder.addToItem2user(t2, 1);
		assertFalse(builder.getItem2user().isEmpty());
		assertEquals(2, builder.getItem2user().size());
	}

	/**
	 * Test 3: from an empty map two call of addToItem2user with the same
	 * itemId, insert only one element to the map with a list of two elements
	 */
	@Test
	public void test_addToItem2user_2same_entry() {
		builder.getItem2user().clear();
		builder.getUsers2item().clear();
		builder.addToItem2user(t1, 4);
		builder.addToItem2user(t2, 4);
		assertFalse(builder.getItem2user().isEmpty());
		assertEquals(1, builder.getItem2user().size());
		assertEquals(2, ((LinkedList<Triple>) (builder.getItem2user().get(4))).size());
	}

	/**
	 * Test 4: if the input path is not valid the two maps remain empty
	 */
	@Test
	public void test_buildCorrelation_Nofile() {
		builder.getItem2user().clear();
		builder.buildCorrelation("./trainingSet.dat");
		assertTrue(builder.getItem2user().isEmpty());
		assertTrue(builder.getUsers2item().isEmpty());
	}

}
