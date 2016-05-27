package cn.hit.cst.ssl.predictor;

import java.io.IOException;
import java.util.ArrayList;

import cn.hit.cst.ssl.predictor.regression.LinearRegressionModel;
import cn.hit.cst.ssl.utils.FileUtils;

/******************************************************************************
 *  Compilation:  javac LinearRegression.java StdIn.java
 *  Execution:    java LinearRegression < data.txt
 *  
 *  Reads in a sequence of pairs of real numbers and computes the
 *  best fit (least squares) line y  = ax + b through the set of points.
 *  Also computes the correlation coefficient and the standard errror
 *  of the regression coefficients.
 *
 *  Note: the two-pass formula is preferred for stability.
 *
 ******************************************************************************/

public class LinearRegression { 
	
    public static void main(String[] args) {
    	int[] colNum = {4, 5, 9};
    	ArrayList<ArrayList<String>> rawData = null;
		try {
			rawData = FileUtils.getSpecifiedCols(args[0], colNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int count = rawData.size();
    	double[] x = new double[count];
    	double[] y = new double[count];
    	for (int i = 0; i < count; i ++) {
			x[i] = Double.valueOf(rawData.get(i).get(2));
			y[i] = Double.valueOf(rawData.get(i).get(0));
		}
        LinearRegressionModel model = new LinearRegressionModel(x, y);
    }
}
