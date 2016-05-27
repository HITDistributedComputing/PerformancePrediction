package cn.hit.cst.ssl.predictor;

import cn.hit.cst.ssl.exception.NullModelException;

public interface PredictionModel {
	public double predictMBSec(double x) throws NullModelException;
	public double predictVCoreSec(double x) throws NullModelException;
	
	public void trainModel(double[] x, double[] mbY,
			double[] cpuY);
}
