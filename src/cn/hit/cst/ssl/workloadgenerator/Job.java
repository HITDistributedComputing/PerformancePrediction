package cn.hit.cst.ssl;

import java.util.HashMap;
import java.util.Map;

public class Job {
	private String jobName;
	private String jobCmd;
	private String inputDir;
	//inputMap: used to store both the HDFS input file path and corresponding probability
	private Map<String, Double> inputMap;
	private Map<Long, String> inputSizePathMap;
	
	public Job(){
		jobName = new String();
		jobCmd = new String();
		inputDir = new String();
		inputMap = new HashMap<String, Double>();
		inputSizePathMap = new HashMap<Long, String>();
	}

	public Job(String jobName, String jobCmd, String inputDir) {
		super();
		this.jobName = jobName;
		this.jobCmd = jobCmd;
		this.inputDir = inputDir;
		this.inputMap = new HashMap<String, Double>();
		inputSizePathMap = new HashMap<Long, String>();
	}

	public String getJobCmd() {
		return jobCmd;
	}

	public void setJobCmd(String jobCmd) {
		this.jobCmd = jobCmd;
	}

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public Map<String, Double> getInputMap() {
		return inputMap;
	}
	
	public void setInputMap(Map<String, Double> inputMap) {
		this.inputMap = inputMap;
	}
	
	public void putInputMap(String inputFile, Double probability){
		this.inputMap.put(inputFile, probability);
	}
}
