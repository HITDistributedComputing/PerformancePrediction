package cn.hit.cst.ssl.benchmark;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class JobGenerator {
	protected ArrayList<Job> jobs;

	public Job randomJobType(Random random){
		int typeCount = this.jobs.size();
		return jobs.get(random.nextInt(typeCount));
	}
	
	/*randomInputFile
	 * @param inputMap
	 * the map of input data file path and corresponding probability
	 * @returns
	 * the input file path as a random result
	 */
	public String randomInputFile(Map<String, Double> inputMap) {
		Double randNum = Math.random();
		Double accuPro = 0.0;//Accumulated probability
		for(Map.Entry<String, Double> entry : inputMap.entrySet()){
			accuPro += entry.getValue();
			if (randNum <= accuPro) {
				return entry.getKey();
			}
		}
		try {
			throw new Exception("Error in randomInputSize!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void jobsReader(String file) throws IOException{
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//notice: 1st line = name, 2nd line = shell script and params,
		//3rd line = input directory
		//the rest = name of input file \t corresponding probability per line
		String jobName, jobCmd, inputDir, tmpJobInfo;
		String[] jobInfo;
		Job job;
		try{
			while(fileReader.ready()){
				jobName = fileReader.readLine();
				jobCmd = fileReader.readLine();
				inputDir = fileReader.readLine();
				job = new Job(jobName, jobCmd, inputDir);
				while (true) {
					tmpJobInfo = fileReader.readLine();
					if (tmpJobInfo.equals("")) {
						break;
					}
					jobInfo = tmpJobInfo.split(" ");
					job.putInputMap(jobInfo[0], Double.valueOf(jobInfo[1]));
				}
				this.jobs.add(job);
			}
			fileReader.close();
		}catch(Exception e){
			e.printStackTrace();
			fileReader.close();
		}
	}
	
	//generate jobs with random inputSizes whose probabilities are offered in MR_fullload, etc
	public String jobGenerator(int jobCount, Random random){
		//TODO use randomJob to generate target job, instead of just wordcount
		Job job;
		String inputFile, jobCmd, logCmd;
		logCmd = " >> benchLogs/job_" + jobCount + " 2>> benchLogs/job_" + jobCount;
		//job = this.jobs.get(0);
		job = randomJobType(random);
		inputFile = randomInputFile(job.getInputMap());
		jobCmd = job.getJobCmd() + " " + job.getInputDir() + inputFile
				+ " " + jobCount + logCmd;
		//TODO: return only one job cmd
		//improve the design architecture, decouple some specific job cmd from MRGen to Job
		return jobCmd;
	}	
	
	//jobFenerator overloaded, can input file size
	public String jobGenerator(int jobCount, Random random, String size){
		//TODO use randomJob to generate target job, instead of just wordcount
		Job job;
		String inputFile, jobCmd, logCmd;
		logCmd = " >> benchLogs/job_" + jobCount + " 2>> benchLogs/job_" + jobCount;
		//job = this.jobs.get(0);
		job = randomJobType(random);
		//inputFile = randomInputFile(job.getInputMap());
		long longSize = Long.parseLong(size);
		//reduce workload pressure
		if ( longSize >= 2000000000 )
		    longSize = longSize / 2;
		inputFile = FileSize.SizeHandle(longSize);
		//jobCmd = job.getJobCmd() + " \"" + job.getInputDir() + inputFile
		//		+ "\" " + jobCount + logCmd;
		jobCmd = job.getJobCmd() + " \"$(sizeHandle.sh " + longSize + ")\" " + jobCount + logCmd;
		//TODO: return only one job cmd
		//improve the design architecture, decouple some specific job cmd from MRGen to Job
		return jobCmd;
	}

	//generate jobs of a specified inputSize
//	public String jobGenerator(int jobCount, Random random, String inputSize){
//		char[] inputSizeBits = inputSize.toCharArray();
//		
//		//TODO use randomJob to generate target job, instead of just wordcount
//				Job job;
//				String inputFile, jobCmd, logCmd;
//				logCmd = " >> benchLogs/job_" + jobCount + " 2>> benchLogs/job_" + jobCount;
//				//job = this.jobs.get(0);
//				job = randomJobType(random);
//				
//				jobCmd = job.getJobCmd() + " " + job.getInputDir() + inputFile
//						+ " " + jobCount + logCmd;
//				//TODO: return only one job cmd
//				//improve the design architecture, decouple some specific job cmd from MRGen to Job
//				return jobCmd;
//	}
	
	//construct input path, can only receive params that are less than 11 digits
//	public static String constructInputPath(Job job, char[] inputSizeBits){
//		char[] top = new char[3];
//		for (int i = 0; i < 3; i++){
//			top[i] = inputSizeBits[i];
//		}
//		String tmpStr = String.copyValueOf(top), inputPath = "{";
//		int tmpInt = Integer.valueOf(tmpStr);
//		int rest = tmpInt % 5;
//		int itr = tmpInt / 5;
//		for (int i = 0; i < itr; i++){
//			inputPath += "wiki_0000000, ";	
//		}
//		
//		
//		for(int i = 0; i < 11; i++){
//			
//		}
//		
//		if (inputSizeBits.length > 11) {
//			
//		}
//	}
	
	public ArrayList<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}
}
