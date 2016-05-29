package cn.hit.cst.ssl.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JobHistory {
	private String appId;
	private String jobId;
	private String name;
	private String type;
	private String inputSize;
	private double elapsedTime;
	private double mbSec;
	private double vcoreSec;
	private Map<String, Long> inputSizeMap;
	public static String inputMapFilePath = "D://Data//inputMap";
	
	public JobHistory(String appId, String inputSize) {
		super();
		inputSizeMap = new HashMap<String, Long>();
		initInputMap();
		this.appId = appId;
		this.inputSize = inputSize;
	}
	
	
	public JobHistory(){
		
	}
	
	public void initInputMap(){
		File inputMapFile = new File(inputMapFilePath);
		BufferedReader mapReader;
		String line = null;
		String[] mapStr = null;
		Long tmpSize;
		try {
			mapReader = new BufferedReader(new FileReader(inputMapFile));
			while (mapReader.ready()) {
				line = mapReader.readLine();
				mapStr = line.split(" ");
				tmpSize = Long.valueOf(mapStr[1]);
				this.inputSizeMap.put(mapStr[0], tmpSize);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setByJSON(JSONObject appJo) throws JSONException{
		this.name = appJo.getString("name");
		this.type = appJo.getString("applicationType");
		this.elapsedTime = Integer.valueOf(appJo.getString("elapsedTime"));
		this.mbSec = Integer.valueOf(appJo.getString("memorySeconds"));
		this.vcoreSec = Integer.valueOf(appJo.getString("vcoreSeconds"));
	}
	
	//AppId name type input elapsedTime mb-sec vcore-sec
	public String getJobHistoryTable(){
		return this.appId + "\t"
				+ this.name + "\t"
				+ this.type + "\t"
				+ this.inputSizeMap.get(this.inputSize) + "\t"
				+ this.elapsedTime + "\t"
				+ this.mbSec + "\t"
				+ this.vcoreSec + "\n";
	}

	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getInputSize() {
		return inputSize;
	}
	
	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}
	
	public double getElapsedTime() {
		return elapsedTime;
	}
	
	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public double getMbSec() {
		return mbSec;
	}
	
	public void setMbSec(double mbSec) {
		this.mbSec = mbSec;
	}
	
	public double getVcoreSec() {
		return vcoreSec;
	}
	
	public void setVcoreSec(double vcoreSec) {
		this.vcoreSec = vcoreSec;
	}
	
	public Map getInputSizeMap(){
		return inputSizeMap;
	}
}
