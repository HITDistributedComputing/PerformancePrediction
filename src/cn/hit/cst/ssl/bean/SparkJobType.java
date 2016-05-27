package cn.hit.cst.ssl.bean;

import java.util.ArrayList;
import java.util.Map;

import cn.hit.cst.ssl.bean.jsonbean.SparkHistoryJob;
import cn.hit.cst.ssl.bean.jsonbean.YARNHistoryJob;
import cn.hit.cst.ssl.predictor.PredictionModel;
import cn.hit.cst.ssl.predictor.regression.DualLinearRegressionModel;

public class SparkJobType extends JobType {
	//avgTaskTime on server "String"
	private Map<String, Integer> avgTaskTime;
	
	public SparkJobType(PredictionModel predictor, String type, 
			String name, ArrayList<YARNHistoryJob> sparkHistoryJobs) {
		super(predictor, type, name, sparkHistoryJobs);
	}
	
	public SparkJobType(String type, String name, 
			ArrayList<YARNHistoryJob> sparkHistoryJobs) {
		super(type, name, sparkHistoryJobs);
	}

	public Map<String, Integer> getAvgTaskTime() {
		return avgTaskTime;
	}

	public void setAvgTaskTime(Map<String, Integer> avgTaskTime) {
		this.avgTaskTime = avgTaskTime;
	}

	@Override
	public void trainModel() {
		// TODO Auto-generated method stub
		int count = this.historyJobs.size();
		double[] x = new double[count], 
				mbY = new double[count],
				cpuY = new double[count];
		for (int i = 0; i < this.historyJobs.size(); i++){
			x[i] = this.historyJobs.get(i).getX();
			mbY[i] = this.historyJobs.get(i).getMbSec();
			cpuY[i] = this.historyJobs.get(i).getVcoreSec();
		}
		this.predictor = new DualLinearRegressionModel();
		this.predictor.trainModel(x, mbY, cpuY);
	}
}
