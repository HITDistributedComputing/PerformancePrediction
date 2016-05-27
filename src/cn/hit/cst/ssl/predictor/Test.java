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
import cn.hit.cst.ssl.bean.SparkJobType;
import cn.hit.cst.ssl.bean.jsonbean.SparkHistoryJob;
import cn.hit.cst.ssl.bean.jsonbean.YARNHistoryJob;
import sun.awt.image.OffScreenImage;

public class Test {
	//args[0]: input file path
	//args[1]: start to train the model when we got args[1] samples
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
		
		int itrCount = 0;
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
					historyJobs = new ArrayList<YARNHistoryJob>();
					historyJobs.add(yarnHistoryJob);
					//adding the current job type to the map
					jobType = constructJobType(type, name, historyJobs);
					nameMap.put(name, jobType);
					frameMap.put(type, nameMap);
				}
				//name Map already exists
				else {
					//name map exists but the application of current name haven't been executed
					if (nameMap.get(name) == null) {
						//construct history jobs of the current type
						historyJobs = new ArrayList<YARNHistoryJob>();
						historyJobs.add(yarnHistoryJob);
						//adding the current job type to the map
						jobType = constructJobType(type, name, historyJobs);
						nameMap.put(name, jobType);
					}
					else {
						jobType = nameMap.get(name);
						jobType.addHistoryJob(yarnHistoryJob);
						//triggered when adding any history job to any job type of any framework
						//TODO: train the job type model in two trategies:
						//1. train it only when the count of a type reach args[1]
						if (jobType.getHistoryJobs().size() == Integer.valueOf(args[1])) {
							//TODO: train the corresponding model
							System.out.println(jobType.getType() + " "
									+ jobType.getName() + " job reaches " + args[1] + "...");
							jobType.trainModel();
						}
						//2. train it whenever args[1] history job has been added
//						if ((jobType.getHistoryJobs().size() % Integer.valueOf(args[1])) == 0) {
//							System.out.println(jobType.getType() + " "
//									+ jobType.getName() + " job reaches " 
//									+ jobType.getHistoryJobs().size() + "...");
//							jobType.trainModel();
//						}
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
				itrCount ++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static JobType constructJobType(String type, String name,
			ArrayList<YARNHistoryJob> historyJobs){
		switch (type) {
		case "SPARK":
			return new SparkJobType(type, name, historyJobs);

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
