/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (c) I-Smart S.R.L., 2012
 *
 * This unpublished material is proprietary to I-Smart S.R.L.
 * All rights reserved. The methods and techniques described
 * herein are considered trade secrets and/or confidential.
 * Reproduction or distribution, in whole  or in part, is
 * forbidden except by express written permission of I-Smart S.R.L.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Error calculator class. It calculates Mean Absolute Error, Mean Absolute
 * Percentage Error and Root Mean Absolute Error of an Oracle's output file.
 * 
 * @date: $Date$
 * @revision: $Revision$
 * @author: $Author$
 * 
 *          File : $HeadURL$
 * 
 */
public class ErrorCaltulator {

	private final File file;

	private FileReader reader;

	private BufferedReader bReader;

	private Double mae;

	private int mape;

	private Double rmse;

	/**
	 * Constructor. It already calculates the errors values.
	 */
	public ErrorCaltulator(final String path) {

		mae = 0.0;
		mape = 0;
		rmse = 0.0;
		Double mapeDouble = 0.0;
		int n = 0;
		this.file = new File(path);
		try {
			this.reader = new FileReader(file);
			this.bReader = new BufferedReader(reader);

			String nextLine = bReader.readLine();
			while (true) {
				nextLine = bReader.readLine();
				if (nextLine == null) {
					break;
				}
				String[] reviewComponents = nextLine.split("\t", 6);
				double rootSingleArg = (Double.parseDouble(reviewComponents[3]) - Double.parseDouble(reviewComponents[2]));
				rmse += rootSingleArg * rootSingleArg;
				mae += Math.abs(rootSingleArg);
				mapeDouble +=
						Math.abs((Double.parseDouble(reviewComponents[2]) - Double.parseDouble(reviewComponents[3]))
								/ Double.parseDouble(reviewComponents[2]));
				n++;
			}

			bReader.close();
			reader.close();
			rmse = rmse / n;
			rmse = Math.sqrt(rmse);
			mae = (mae) / n;
			mapeDouble = mapeDouble / n * 100;
			mape = mapeDouble.intValue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Retrieves the Mean Absolute Error
	 * 
	 * @return the mae
	 */
	public Double getMae() {
		return mae;
	}

	/**
	 * @param mae
	 *            the mae to set
	 */
	public void setMae(final Double mae) {
		this.mae = mae;
	}

	/**
	 * Retrieves the Mean Absolute Percentage Error
	 * 
	 * @return the mape
	 */
	public int getMape() {
		return mape;
	}

	/**
	 * @param mape
	 *            the mape to set
	 */
	public void setMape(final int mape) {
		this.mape = mape;
	}

	/**
	 * Retrieves the Root Mean Absolute Error
	 * 
	 * @return the rmse
	 */
	public Double getRmse() {
		return rmse;
	}

	/**
	 * 
	 * @param rmse
	 *            the rmse to set
	 */
	public void setRmse(final Double rmse) {
		this.rmse = rmse;
	}

}
