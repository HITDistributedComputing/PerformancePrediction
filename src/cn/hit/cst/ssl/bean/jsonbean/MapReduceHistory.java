package cn.hit.cst.ssl.bean.jsonbean;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hit.cst.ssl.bean.JobHistory;

public class MapReduceHistory extends JobHistory {
	
	private String jobId;
	private int mapsTotal;
	private int reducesTotal;
	
	private int avgMapTime;
	private int avgReduceTime;
	private int avgshuffleTime;
	private int avgMergeTime;
	
	
	public MapReduceHistory(String appId, String jobId, String inputSize){
		super(appId, inputSize);
		this.jobId = jobId;
	}
	
	public void setMPByJSON(JSONObject appJo) throws JSONException{
		
		this.mapsTotal = Integer.valueOf(appJo.getString("mapsTotal"));
		this.reducesTotal = Integer.valueOf(appJo.getString("reducesTotal"));
		this.avgMapTime = Integer.valueOf(appJo.getString("avgMapTime"));
		this.avgReduceTime = Integer.valueOf(appJo.getString("avgReduceTime"));
		this.avgshuffleTime = Integer.valueOf(appJo.getString("avgShuffleTime"));
		this.avgMergeTime = Integer.valueOf(appJo.getString("avgMergeTime"));
		
	}
	
	public String getMRHistoryTable(){
		return this.getAppId() + "\t"
				+ this.getJobId() + "\t"
				+ this.getName() + "\t"
				+ this.getType() + "\t"
				+ this.getInputSizeMap().get(this.getInputSize()) + "\t"
				+ this.getElapsedTime() + "\t"
				+ this.getMbSec() + "\t"
				+ this.getVcoreSec() + "\t"
				+ this.getMapsTotal() + "\t"
				+ this.getReducesTotal() + "\t"
				+ this.getAvgMapTime() + "\t"
				+ this.getAvgReduceTime() + "\t"
				+ this.getAvgshuffleTime() + "\t"
				+ this.getAvgMergeTime() + "\n";
	}
	
	public String getTableHeader(){
		return "AppId\t"
			+"JobId\t"
			+"Name\t"
			+"Type\t"
			+"InputSize\t"
			+"ElapsedTime\t"
			+"MbSec\t"
			+"VcoreSec\t"
			+"MapsTota\t"
			+"ReducesTotal\t"
			+"AvgMapTime\t"
			+"AvgReduceTime\t"
			+"AvgshuffleTime\t"
			+"AvgMergeTime\n";
	}
	
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public int getMapsTotal() {
		return mapsTotal;
	}
	public void setMapsTotal(int mapsTotal) {
		this.mapsTotal = mapsTotal;
	}
	public int getReducesTotal() {
		return reducesTotal;
	}
	public void setReducesTotal(int reducesTotal) {
		this.reducesTotal = reducesTotal;
	}
	public int getAvgMapTime() {
		return avgMapTime;
	}
	public void setAvgMapTime(int avgMapTime) {
		this.avgMapTime = avgMapTime;
	}
	public int getAvgReduceTime() {
		return avgReduceTime;
	}
	public void setAvgReduceTime(int avgReduceTime) {
		this.avgReduceTime = avgReduceTime;
	}
	public int getAvgshuffleTime() {
		return avgshuffleTime;
	}
	public void setAvgshuffleTime(int avgshuffleTime) {
		this.avgshuffleTime = avgshuffleTime;
	}
	public int getAvgMergeTime() {
		return avgMergeTime;
	}
	public void setAvgMergeTime(int avgMergeTime) {
		this.avgMergeTime = avgMergeTime;
	}

	
	
}
