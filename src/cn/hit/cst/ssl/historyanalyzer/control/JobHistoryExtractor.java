package cn.hit.cst.ssl.historyanalyzer.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hit.cst.ssl.bean.JobHistory;
import cn.hit.cst.ssl.utils.FileUtils;
import cn.hit.cst.ssl.utils.JSONUtils;
/**
 * 
* @ClassName: JobHistoryExtractor 
* @Description: extract YARN-general job history data based on log files and REST API
* @author Yukun Zeng
* @date May 31, 2016 9:52:37 AM 
*
 */
public class JobHistoryExtractor {
	/**
	 * 
	 * @Method: main
	 * @Description: TODO
	 * @param args
	 * args[0]: job log files input directory
	 * args[1]: job data output file path
	 * @return void
	 */
	public static void main(String[] args){
		//File[] logFiles = FileUtils.iterateFiles(args[0]);
		File[] logFiles = FileUtils.manItrFiles(args[0], "job", 200);
		ArrayList<String> jobData = retrieveJobData(logFiles);
		try {
			FileUtils.writeToFile(args[1], jobData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Method: retrieveJobData
	 * @Description: retrieve job data based on log files and through requesting JSON from REST API
	 * @param logFiles
	 * the log files array, which are all collected by our Benchmark
	 * @return ArrayList<String>
	 * Arraylist lthat stores the history data as file lines to be write into a file
	 */
	public static ArrayList<String> retrieveJobData(File[] logFiles){
		String jsonStr, requestStr, tmpJobData;
		JobHistory jobHistory;
		JSONObject appJo;
		ArrayList<String> jobData = new ArrayList<String>();
		int extendedFileNum = 200;
		//TODO: change to read file in one time and store info in memory
		for(int i = 1; i <= extendedFileNum; i++){
			try {
				jobHistory = FileUtils.getJobHistory(logFiles[i]);
				requestStr = "http://172.29.132.196:8088/ws/v1/cluster/apps/" + jobHistory.getAppId();
				jsonStr = JSONUtils.getJsonString(requestStr);
				appJo = (new JSONObject(jsonStr)).getJSONObject("app");
				jobHistory.setByJSON(appJo);
				tmpJobData = jobHistory.getJobHistoryTable();
//				tmpJobData = jobHistory.getAppId() + "\t" + appJo.getString("name") + "\t" + appJo.getString("applicationType") + "\t"
//				+ jobHistory.getInputSize() + "\t" + appJo.getString("elapsedTime") + "\t" + appJo.getString("memorySeconds") + "\t"
//				+ appJo.getString("vcoreSeconds");
				jobData.add(tmpJobData);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				System.out.println("Crashed record on iteration: " + i);
				extendedFileNum ++;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jobData;
	}
}
