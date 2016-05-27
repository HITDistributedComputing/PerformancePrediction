package cn.hit.cst.ssl.predictor.regression;

import cn.hit.cst.ssl.exception.NullModelException;
import cn.hit.cst.ssl.predictor.PredictionModel;

//Notice:
//the two prediction model will only be initialized after running trainModel()
public class DualLinearRegressionModel implements PredictionModel {
	private LinearRegressionModel mbSecRegression;
	private LinearRegressionModel cpuSecRegression;
	
	public DualLinearRegressionModel() {
		super();
		this.mbSecRegression = null;
		this.cpuSecRegression = null;
	}

	@Override
	public double getMBSec(double duration) throws NullModelException {
		// TODO Auto-generated method stub
		if (mbSecRegression == null) 
			throw new NullModelException(this.getClass().toString());
		else
			return mbSecRegression.predictY(duration);
	}

	@Override
	public double getVCoreSec(double duration) throws NullModelException {
		// TODO Auto-generated method stub
		if (this.cpuSecRegression == null) 
			throw new NullModelException(this.getClass().toString());
		else
			return this.cpuSecRegression.predictY(duration);
	}

	@Override
	public void trainModel(double[] x, double[] mbY,
			double[] cpuY) {
		this.mbSecRegression = new LinearRegressionModel();
		this.cpuSecRegression = new LinearRegressionModel();
		// TODO Auto-generated method stub
		System.out.println("Training MB-Sec Regression Model:");
		mbSecRegression.trainModel(x, mbY);
		System.out.println("Training VCore-Sec Regression Model:");
		cpuSecRegression.trainModel(x, cpuY);
	}
}
