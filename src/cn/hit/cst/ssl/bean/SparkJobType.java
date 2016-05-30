package cn.hit.cst.ssl.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.hit.cst.ssl.bean.jsonbean.Host;
import cn.hit.cst.ssl.exception.NullModelException;
import cn.hit.cst.ssl.predictor.regression.DualLinearRegressionModel;
/**
 * 
* @ClassName: SparkJobType 
* @Description: the class that holds spark framework detailed parameters for predicting x in our linear regression model
* @author Yukun Zeng
* @date May 30, 2016 4:30:50 PM 
*
 */
public class SparkJobType extends JobType {
	//avgTaskTime on server "String"
	//update scenario: whenever a new history job is added to JobType
	private Map<String, ServerAvgTaskTime> avgTaskTimeMap;
	
	public SparkJobType(String type, String name, 
			YARNHistoryJob sparkHistoryJob) {
		super(type, name, sparkHistoryJob);
		this.avgTaskTimeMap = new HashMap<String, ServerAvgTaskTime>();
		
		ArrayList<Host> hosts = ((SparkHistoryJob)sparkHistoryJob).getHosts();
		ServerAvgTaskTime serverAvgTaskTime;
		
		for (Host host : hosts) {
			//create new ServerAvgTaskTime
			serverAvgTaskTime = new ServerAvgTaskTime(host.getTotalDuration(), host.getTotalTask());
			this.avgTaskTimeMap.put(host.getHostPort(), serverAvgTaskTime);
		}
	}

	public Map<String, ServerAvgTaskTime> getAvgTaskTimeMap() {
		return avgTaskTimeMap;
	}

	public void setAvgTaskTimeMap(Map<String, ServerAvgTaskTime> avgTaskTimeMap) {
		this.avgTaskTimeMap = avgTaskTimeMap;
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
	
	@Override
	//the two only entrance (together with constructor) 
	//for adding history job, will update avgtasktime
	public void addHistoryJob(YARNHistoryJob historyJob) {
		//TODO: 1. add history job using super.add D
		//2. update the avgTaskTimeMap D
		//3. make sure all the entrance for adding history job including constructor
		//to update avgTaskTimeMap D
		//4. integrate the Model Training Trigger here
		super.addHistoryJob(historyJob);
		SparkHistoryJob sparkHistoryJob = (SparkHistoryJob) historyJob;
		ArrayList<Host> hosts = sparkHistoryJob.getHosts();
		//temp variables for iteration
		Host host;
		String hostPort;
//		Double avgTaskTime;
		Double totalDuration;
		int totalTask;
		ServerAvgTaskTime serverAvgTaskTime;
		
		for (int i = 0;  i < hosts.size(); i++){
			host = hosts.get(i);
			hostPort = host.getHostPort();
			//if the total task of a job on a host is 0
			if (host.getTotalDuration() == 0 || host.getTotalTask() == 0) {
				System.out.println(historyJob.getAppId() + " consists of 0 tasks");
				return;
			}
			totalDuration = host.getTotalDuration();
			totalTask = host.getTotalTask();
			if (this.avgTaskTimeMap.get(hostPort) == null) {
				serverAvgTaskTime = new ServerAvgTaskTime(totalDuration, totalTask);
				avgTaskTimeMap.put(hostPort, serverAvgTaskTime);
			}
			else {
				serverAvgTaskTime = this.avgTaskTimeMap.get(hostPort);
//				System.out.println("Updating " + sparkHistoryJob.getAppId());
				serverAvgTaskTime.update(totalDuration, totalTask);
			}
			//here is to predict duration using strategies that see a job host history as a unit for average
//			avgTaskTime = host.getTotalDuration() / host.getTotalTask();
//			if (this.avgTaskTimeMap.get(hostPort) == null) {
//				serverAvgTaskTime = new ServerAvgTaskTime(avgTaskTime);
//				avgTaskTimeMap.put(hostPort, serverAvgTaskTime);
//			}
//			else {
//				serverAvgTaskTime = this.avgTaskTimeMap.get(hostPort);
//				System.out.println("Updating " + sparkHistoryJob.getAppId());
//				serverAvgTaskTime.update(avgTaskTime);
//			}
		}
	}

	@Override
	public double predictMBSecByIndex(int index) throws NullModelException {
		// TODO Auto-generated method stub
		SparkHistoryJob jobHistory = (SparkHistoryJob)this.historyJobs.get(index);
		String hostName = jobHistory.getLongestHost().getHostPort();
		Double avgTaskTime = this.avgTaskTimeMap.get(hostName).getAvgTaskTime();
		return this.getPredictor().predictMBSec(jobHistory.getXbyTaskTime(avgTaskTime));
	}

	@Override
	public double predictVCoreSecByIndex(int index) throws NullModelException {
		// TODO Auto-generated method stub
		//1. predict directly by duration
//		SparkHistoryJob jobHistory = (SparkHistoryJob)this.historyJobs.get(index);
//		return this.getPredictor().predictVCoreSec(jobHistory.getX());
		//2. predict by calculating duration through avgTaskTime
		SparkHistoryJob jobHistory = (SparkHistoryJob)this.historyJobs.get(index);
		String hostName = jobHistory.getLongestHost().getHostPort();
		Double avgTaskTime = this.avgTaskTimeMap.get(hostName).getAvgTaskTime();
		return this.getPredictor().predictVCoreSec(jobHistory.getXbyTaskTime(avgTaskTime));
	}
	
	public double predictLongestDurationByIndex(int index) throws NullModelException {
		// TODO Auto-generated method stub
		//1. predict directly by duration
//		SparkHistoryJob jobHistory = (SparkHistoryJob)this.historyJobs.get(index);
//		return this.getPredictor().predictVCoreSec(jobHistory.getX());
		//2. predict by calculating duration through avgTaskTime
		SparkHistoryJob jobHistory = (SparkHistoryJob)this.historyJobs.get(index);
		String hostName = jobHistory.getLongestHost().getHostPort();
		Double avgTaskTime = this.avgTaskTimeMap.get(hostName).getAvgTaskTime();
		return jobHistory.getXbyTaskTime(avgTaskTime);
	}
}
