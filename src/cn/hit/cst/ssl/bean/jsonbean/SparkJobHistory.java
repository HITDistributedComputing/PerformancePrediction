package cn.hit.cst.ssl.bean.jsonbean;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hit.cst.ssl.bean.JobHistory;
/**
 * 
* @ClassName: SparkJobHistory 
* @Description: JavaBean for spark history job info we can get from JSON of spark history server
* @author Yukun Zeng
* @date May 30, 2016 5:53:18 PM 
*
 */
public class SparkJobHistory extends JobHistory {
	
	private String executorId;
	private int totalDuration;
	private int totalTasks;
	private String hostPort;

	public SparkJobHistory(String appId, String inputSize) {
		super(appId, inputSize);
		// TODO Auto-generated constructor stub
	}
	public SparkJobHistory(){
		
	}
	
	//AppId name type input elapsedTime mb-sec vcore-sec
	public String getJobHistoryTable(){
		return this.getAppId() + "\t"
				+ this.getName() + "\t"
				+ this.getType() + "\t"
				+ this.getExecutorId() + "\t"
				+ this.getHostPort() + "\t"
				+ this.getInputSizeMap().get(this.getInputSize()) + "\t"
				+ this.getTotalTasks() + "\t"
				+ this.getTotalDuration() + "\t"
				+ this.getElapsedTime() + "\t"
				+ this.getMbSec() + "\t"
				+ this.getVcoreSec() + "\n";
		
		/*return this.getAppId() + "\t"
			+ this.getExecutorId() + "\t"
			+ this.getHostPort() + "\t"
			+ this.getType() + "\t"
			+ this.getTotalTasks() + "\t"
			+ this.getTotalDuration() + "\n";*/
	}
	
	public String getTableHeader(){
		return "AppId\t"
			+"Name\t"
			+"Type\t"
			+"ExecutorId\t"
			+"HostPort\t"
			+"InputSize\t"
			+"TotalTasks\t"
			+"TotalDuration\t"
			+"ElapsedTime\t"
			+"MbSec\t"
			+"VcoreSec\n";
	}
	
	public void setSparkByJSON(JSONObject appJo) throws JSONException{
		//this.setType("spark");
		this.executorId = appJo.getString("id");
		this.totalDuration = Integer.valueOf(appJo.getString("totalDuration"));
		this.totalTasks = Integer.valueOf(appJo.getString("totalTasks"));
		this.hostPort = appJo.getString("hostPort");
	}
	

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public int getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(int totalTasks) {
		this.totalTasks = totalTasks;
	}

	public String getHostPort() {
		return hostPort;
	}

	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	
}
