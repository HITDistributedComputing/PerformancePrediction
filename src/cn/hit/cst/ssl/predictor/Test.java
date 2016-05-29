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
import cn.hit.cst.ssl.utils.FileUtils;

public class Test {
	//args[0]: input file path
	//args[1]: start to train the model when we got args[1] samples
	//args[2]: file path for output prediction results
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ÊäÈë×÷Òµfeature
		//map: framework - <type - job>
		Map<String, Map<String, JobType>> frameMap = 
				new HashMap<String, Map<String, JobType>>();
		Map<String, JobType> nameMap;
		
		ArrayList<YARNHistoryJob> historyJobs;
		JobType jobType;
		File file = new File(args[0]);
		String line, type, name;
		YARNHistoryJob yarnHistoryJob; 
		
		ArrayList<String> jobData = new ArrayList<String>();
		jobData.add("AppId\tName\tType\tMBSec\tVCoreSec\tDuration\tPredictedDuration\tPredictedMB\tPredictedCPU\n");

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
						int modelRange = Integer.valueOf(args[1]);
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
								SparkJobType sparkJobType = (SparkJobType) jobType;
								jobData.add(((SparkHistoryJob)yarnHistoryJob).getJobHistoryTable()
										+ sparkJobType.predictLongestDurationByIndex(count - 1) + "\t"
										+ jobType.predictMBSecByIndex(count - 1) + "\t"
										+ jobType.predictVCoreSecByIndex(count - 1) + "\n");
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
			FileUtils.writeToFile(args[2], jobData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static JobType constructJobType(String type, String name,
			YARNHistoryJob historyJob){
		switch (type) {
		case "SPARK":
			return new SparkJobType(type, name, historyJob);

		default:
			return null;
		}
	}
	
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
