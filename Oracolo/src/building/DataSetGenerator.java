package building;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that create a training set and a test set from a given file .dat. It
 * divides the file in 1/3 and 2/3, one as test set and the other one as
 * training set.
 * 
 * 
 * @author Rosella Omana Mancilla
 * 
 */
public class DataSetGenerator {

	private FileReader fileReader;
	private BufferedReader bReader;
	private FileWriter testSetfileWriter;

	private final static int trainingSetSize = 379974;
	private final static int testSetSize = 189986;

	private static String testSetPath = "./data/generated/testSet.dat";

	private static String traningSetPath = "./data/generated/trainingSet.dat";

	/**
	 * Constructor, it create test set and training set as two .dat file
	 * 
	 * @param path_name
	 *            path of the input file
	 */
	public DataSetGenerator(final String path_name) {

		File directory = new File(path_name);
		System.out.println(directory.getPath());
		try {
			generateDataETestSet(directory);

		} finally {
			close();
		}

	}

	/**
	 * This creates two .dat file, a training and a test set from a given file.
	 * This method creates a second test set, with the same record of the
	 * training set
	 * 
	 * @param directory
	 *            the given file
	 */
	private void generateDataETestSet(final File directory) {

		int[] positions = generateRandomNumbers(DataSetGenerator.trainingSetSize, 569960);
		int array_index = 0;
		int index_testSet = 0;
		int line = 0;
		File testSetFile, testSetFileRating, trainingSetFile;
		FileWriter testSetfileWriter2, trainingSetfileWriter;
		try {
			this.fileReader = new FileReader(directory);
			this.bReader = new BufferedReader(this.fileReader);

			String nextLine = null;

			testSetFile = new File(testSetPath);
			if (!testSetFile.exists()) {
				testSetFile.createNewFile();
				System.out.println("TEST FILE CREATED.");
			}

			/**
			 ******************************************************************** 
			 * This is same file as trainingSet.dat but with real rating values.
			 */
			testSetFileRating = new File("./data/generated/testSetRating.dat");
			if (!testSetFileRating.exists()) {
				testSetFileRating.createNewFile();
				System.out.println("TEST FILE WITH REAL RATINGS CREATED.");
			}
			testSetfileWriter2 = new FileWriter(testSetFileRating);
			testSetfileWriter2.write("userID\tmovieID\ttimestamp\trealRating\n");
			/**
			 ********************************************************************
			 */
			testSetfileWriter = new FileWriter(testSetFile);
			testSetfileWriter.write("userID\tmovieID\trating\ttimestamp\n");

			trainingSetFile = new File(traningSetPath);
			if (!trainingSetFile.exists()) {
				trainingSetFile.createNewFile();
				System.out.println("TRAINING FILE CREATED.");
			}
			trainingSetfileWriter = new FileWriter(trainingSetFile);
			trainingSetfileWriter.write("userID\tmovieID\trating\ttimestamp\n");
			nextLine = bReader.readLine();
			do {

				nextLine = bReader.readLine();
				if (index_testSet == DataSetGenerator.testSetSize) {
					break;
				}
				String[] reviewComponents = nextLine.split("\t", 4);

				if ((array_index < DataSetGenerator.trainingSetSize) && line == positions[array_index]) {

					trainingSetfileWriter.write(nextLine + "\n");

					testSetfileWriter2.write(reviewComponents[0] + "\t" + reviewComponents[1] + "\t" + reviewComponents[3] + "\t"
							+ reviewComponents[2] + "\n");
					array_index++;
					line++;

				} else {

					String testLine =
							reviewComponents[0] + "\t" + reviewComponents[1] + "\t" + reviewComponents[3] + "\t" + reviewComponents[2];

					testSetfileWriter.write(testLine + "\n");

					index_testSet++;
					line++;
				}
			} while (true);
			trainingSetfileWriter.close();
			testSetfileWriter2.close();
			close();
			System.out.println("TRAINING SET CREATED.");
			System.out.println("TEST SET CREATED.");
			System.out.println("TRAINING SET WITH RATINGS CREATED.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Closure of reader and writer.
	 */
	private void close() {
		try {
			if (this.bReader != null) {
				this.bReader.close();
			}
			if (this.fileReader != null) {
				this.fileReader.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to close reader.", e);
		}
		try {
			if (this.testSetfileWriter != null) {
				this.testSetfileWriter.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to close writer.", e);
		}

	}

	/**
	 * Calculates the size as number of entries of a given file.
	 * 
	 * @param directory
	 *            the given file
	 * @return size of the file
	 */
	public int sizeFile(final File directory) {

		int i = 0;
		try {
			this.fileReader = new FileReader(directory);
			this.bReader = new BufferedReader(this.fileReader);

			String nextLine = null;
			bReader.readLine();

			do {

				nextLine = bReader.readLine();
				if (nextLine == null) {
					break;
				}
				i++;
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * This method generates an integer with random values.
	 * 
	 * @param n
	 *            size of the array
	 * 
	 * @param max
	 *            maximum value
	 * @return
	 */

	private int[] generateRandomNumbers(final int n, final int max) {
		Set<Integer> values = new HashSet<Integer>();
		int[] numbers = new int[n];

		for (int i = 0; i < n;) {
			double rand = Math.random() * max;
			int number = (int) rand;

			if (!values.contains(number)) {
				values.add(number);
				numbers[i] = number;
				i++;
			}
		}

		Arrays.sort(numbers);
		return numbers;
	}

	/**
	 * @return the testsetpath
	 */
	public static String getTestsetpath() {
		return testSetPath;
	}

	/**
	 * @param testsetpath
	 *            the testsetpath to set
	 */
	public static void setTestsetpath(final String testsetpath) {
		testSetPath = testsetpath;
	}

	/**
	 * @return the traningsetpath
	 */
	public static String getTraningsetpath() {
		return traningSetPath;
	}

	/**
	 * @param traningsetpath
	 *            the traningsetpath to set
	 */
	public static void setTraningsetpath(final String traningsetpath) {
		traningSetPath = traningsetpath;
	}

}
