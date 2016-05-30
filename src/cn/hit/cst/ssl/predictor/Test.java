package cn.hit.cst.ssl.predictor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.hit.cst.ssl.bean.JobType;
import cn.hit.cst.ssl.bean.SparkHistoryJob;
import cn.hit.cst.ssl.bean.SparkJobType;
import cn.hit.cst.ssl.bean.YARNHistoryJob;
import cn.hit.cst.ssl.exception.NullModelException;
import cn.hit.cst.ssl.exception.XYArraySizeException;
import cn.hit.cst.ssl.utils.FileUtils;
import cn.hit.cst.ssl.visualization.ScatterPlotter;

/** 
* @ClassName: Test 
* @Description: Test class for performance prediction and results visualization 
* @author Yukun Zeng
* @date May 30, 2016 1:26:29 PM 
*  
*/
public class Test {
	/**
	 * 
	 * @Method: main function for making predictions and visualizing
	 * @Description: TODO
	 * @param args
	 * args[0]: input file path
	 * args[1]: start to train the model when we got args[1] samples
	 * args[2]: file path for output prediction results
	 * @return void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//������ҵfeature
		//map: framework - <type - job>
		Map<String, Map<String, JobType>> frameMap = 
				new HashMap<String, Map<String, JobType>>();
		Map<String, JobType> nameMap;
		
		ArrayList<YARNHistoryJob> historyJobs;
		JobType jobType;
		File file = new File(args[0]);
		String line, type, name;
		YARNHistoryJob yarnHistoryJob; 
		int modelRange = Integer.valueOf(args[1]);
		
		ArrayList<String> jobData = new ArrayList<String>();
		jobData.add("AppId\tName\tType\tMBSec\tVCoreSec\tDuration\tPredictedDuration\tPredictedMB\tPredictedCPU\n");
		//actual vs. predicted result array
		ArrayList<Double> mbSecArray = new ArrayList<Double>(), 
				vcoreSecArray = new ArrayList<Double>(), 
				longestDurationArray = new ArrayList<Double>(),
				preMbSecArray = new ArrayList<Double>(),
				preVcoreSecArray = new ArrayList<Double>(),
				preDurationArray = new ArrayList<Double>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			while (reader.ready()) {
				line = reader.readLine();
				//construct a history job using the line
				yarnHistoryJob = constructHistoryJob(line);
				type = yarnHistoryJob.getType();
				name = yarnHistoryJob.getName();
				//try to get the <Type - Job> map
				nameMap = frameMap.get(type);
				//if the <Type - Job> map is null, then this is the first time that we get jobs
				//of such framework -> construct a new map for storing the new framework jobs
				if (nameMap == null) {
					//construct the new map
					nameMap = new HashMap<String, JobType>();
					//construct history jobs of the current type
					
//					historyJobs = new ArrayList<YARNHistoryJob>();
//					historyJobs.add(yarnHistoryJob);
					//adding the current job type to the map
					jobType = constructJobType(type, name, yarnHistoryJob);
					nameMap.put(name, jobType);
					frameMap.put(type, nameMap);
				}
				//name Map already exists
				else {
					//name map exists but the application of current name haven't been executed
					if (nameMap.get(name) == null) {
						//construct history jobs of the current type
						
//						historyJobs = new ArrayList<YARNHistoryJob>();
//						historyJobs.add(yarnHistoryJob);
						//adding the current job type to the map
						jobType = constructJobType(type, name, yarnHistoryJob);
						nameMap.put(name, jobType);
					}
					else {
						jobType = nameMap.get(name);
						jobType.addHistoryJob(yarnHistoryJob);
						//triggered when adding any history job to any job type of any framework
						//TODO: train the job type model in two trategies:
						//1. train it only when the count of a type reach args[1]
						//count: current history job count of jobType
						int count = jobType.getHistoryJobs().size();
						//controls when to train the model
						//if (count == modelRange){
						if ((count % modelRange == 0)) {
							//TODO: train the corresponding model
							System.out.println(jobType.getType() + " "
									+ jobType.getName() + " job reaches " + count + "...");
							jobType.trainModel();
						}
						//2. train it whenever args[1] history job has been added
//						if ((count % modelRange) == 0) {
//							System.out.println(jobType.getType() + " "
//									+ jobType.getName() + " job reaches " + count + "...");
//							jobType.trainModel();
//						}
						else if(count > modelRange){
							try {
								//TODO: extend to other frameworks
								SparkJobType sparkJobType = (SparkJobType) jobType;
								Double preDuration = sparkJobType.predictLongestDurationByIndex(count - 1), 
										preMbSec = sparkJobType.predictMBSecByIndex(count - 1), 
										preVcoreSec = sparkJobType.predictVCoreSecByIndex(count - 1);
								//TODO: 1. pass predicted vs. actual value for visualizing data
								//2. calculate outside the loop to avoid unnecessary computation 
								SparkHistoryJob sparkHistoryJob = (SparkHistoryJob)yarnHistoryJob;
								longestDurationArray.add(sparkHistoryJob.getLongestHost().getTotalDuration());
								mbSecArray.add(sparkHistoryJob.getMbSec());
								vcoreSecArray.add(sparkHistoryJob.getVcoreSec());
								preDurationArray.add(preDuration);
								preMbSecArray.add(preMbSec);
								preVcoreSecArray.add(preVcoreSec);
								//
								jobData.add(((SparkHistoryJob)yarnHistoryJob).getJobHistoryTable()
										+ preDuration + "\t"
										+ preMbSec + "\t"
										+ preVcoreSec + "\n");
//								jobData.add(yarnHistoryJob.getJobHistoryTable()
//										+ jobType.predictMBSecByIndex(count - 1) + "\t"
//										+ jobType.predictVCoreSecByIndex(count - 1) + "\n");
							} catch (NullModelException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				//Obsolete, reconstructed
				//test if some job type have been executed more than args[1] times
				//if yes, then train its prediction model
//				for (Map.Entry<String, Map<String, JobType>> frameEntry
//						: frameMap.entrySet()){
//					Map<String, JobType> tmpTypeMap = frameEntry.getValue();
//					for(Map.Entry<String, JobType> typeEntry
//							: tmpTypeMap.entrySet()){
//						if (typeEntry.getValue().getHistoryJobs().size() == Integer.valueOf(args[1])) {
//							//TODO: train the corresponding model
//							System.out.println(typeEntry.getValue().getType() + " "
//									+ typeEntry.getKey() + " job reaches 50...");
//							typeEntry.getValue().trainModel();
//						}
//					}
//				}
			}
			ScatterPlotter.scatterRegressionPlot(longestDurationArray, preDurationArray, "Actual", "Predicted");
//			ScatterPlotter.scatterPlot(longestDurationArray, preDurationArray, 
//					"Predicted vs. Actual Longest Duration", "Actual", "Predicted");
			FileUtils.writeToFile(args[2], jobData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XYArraySizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @Method: constructJobType
	 * @Description: construct different job type obj according to the input
	 * @param type
	 * job type string
	 * @param name
	 * job name string
	 * @param historyJob
	 * the first history job data
	 * @return JobType
	 * newly constructed job type obj
	 */
	public static JobType constructJobType(String type, String name,
			YARNHistoryJob historyJob){
		switch (type) {
		case "SPARK":
			return new SparkJobType(type, name, historyJob);

		default:
			return null;
		}
	}
	/**
	 * 
	 * @Method: constructHistoryJob
	 * @Description: 
	 * @param line
	 * the line from the file that contains history job data
	 * @return YARNHistoryJob
	 * newly constructed yarn history job
	 */
	public static YARNHistoryJob constructHistoryJob(String line){
		String type = line.split("\t")[2];
		switch (type) {
		case "SPARK":
			return new SparkHistoryJob(line);
		default:
			return null;
		}
	}
}
