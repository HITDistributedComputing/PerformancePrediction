package cn.hit.cst.ssl.bean;

import java.util.ArrayList;

import cn.hit.cst.ssl.exception.NullModelException;
import cn.hit.cst.ssl.predictor.PredictionModel;

public abstract class JobType {
	protected PredictionModel predictor;
	protected String type;
	protected String name;
	protected ArrayList<YARNHistoryJob> historyJobs;
	
	public JobType(String type, String name, YARNHistoryJob historyJob) {
		super();
		this.type = type;
		this.name = name;
		this.historyJobs = new ArrayList<YARNHistoryJob>();
		this.historyJobs.add(historyJob);
	}

	public PredictionModel getPredictor() {
		return predictor;
	}

	public void setPredictor(PredictionModel predictor) {
		this.predictor = predictor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<YARNHistoryJob> getHistoryJobs() {
		return historyJobs;
	}

	public void setHistoryJobs(ArrayList<YARNHistoryJob> historyJobs) {
		this.historyJobs = historyJobs;
	}
	
	public void addHistoryJob(YARNHistoryJob historyJob){
		this.historyJobs.add(historyJob);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public abstract void trainModel();
	
	public abstract double predictMBSecByIndex(int index) throws NullModelException;
	
	public abstract double predictVCoreSecByIndex(int index) throws NullModelException;
}
