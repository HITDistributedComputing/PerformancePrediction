package cn.hit.cst.ssl.bean;

import java.util.ArrayList;

import cn.hit.cst.ssl.exception.NullModelException;
import cn.hit.cst.ssl.predictor.PredictionModel;
/**
 * 
* @ClassName: JobType 
* @Description: an object of this class = data for a type of job running on a certain framework
* @author Yukun Zeng
* @date May 30, 2016 4:04:40 PM 
*
 */
public abstract class JobType {
	
	protected PredictionModel predictor;
	protected String type;
	protected String name;
	protected ArrayList<YARNHistoryJob> historyJobs;
	/**
	 * 
	 * @Description: coupled with our main program logic, construct a JobType obj when the first job of
	 * a type of a certain framework comes
	 * @param type
	 * job type, such as SPARK, MAPREDUCE, etc
	 * @param name
	 * job name, such as WordCount, Grep, etc
	 * @param historyJob
	 * the first history job of such type of job
	 */
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
	/**
	 * 
	 * @Method: addHistoryJob
	 * @Description: extended setter, add one history job to our array
	 * @param historyJob
	 * the history job data to be added
	 * @return void
	 */
	public void addHistoryJob(YARNHistoryJob historyJob){
		this.historyJobs.add(historyJob);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 
	 * @Method: trainModel
	 * @Description: method to train our linear regression model
	 * @return void
	 */
	public abstract void trainModel();
	/**
	 * 
	 * @Method: predictMBSecByIndex
	 * @Description: use index to get job pre-running data and input those data to regression model for MB-Sec prediction
	 * @param index
	 * the array position of the job we want to predict
	 * @throws NullModelException
	 * @return double
	 * predicted MB Seconds result
	 * 
	 */
	public abstract double predictMBSecByIndex(int index) throws NullModelException;
	/**
	 * 
	 * @Method: predictVCoreSecByIndex
	 * @Description: use index to get job pre-running data and input those data to regression model for VCore-Sec prediction
	 * @param index
	 * the array position of the job we want to predict
	 * @throws NullModelException
	 * @return double
	 * predicted MB Seconds result
	 */
	public abstract double predictVCoreSecByIndex(int index) throws NullModelException;
}
