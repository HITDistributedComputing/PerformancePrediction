package cn.hit.cst.ssl.historyanalyzer.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.hit.cst.ssl.bean.jsonbean.MapReduceHistory;
import cn.hit.cst.ssl.utils.FileUtils;
import cn.hit.cst.ssl.utils.JSONUtils;

public class MapReduceExtractor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File[] logFiles = FileUtils.manItrFiles(args[0], "job", 1000);
		ArrayList<String> jobData = retrieveJobData(logFiles);
		try {
			FileUtils.writeToFile(args[1], jobData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed!");
	}
	
	public static ArrayList<String> retrieveJobData(File[] logFiles){
		String jsonStr1, jsonStr2, requestStr1, requestStr2, tmpJobData;
		MapReduceHistory mRHistory;
		JSONObject appJo1, appJo2;
		ArrayList<String> jobData = new ArrayList<String>();
		int extendedFileNum = 1000;
		//TODO: change to read file in one time and store info in memory
		for(int i = 1; i <= extendedFileNum; i++){
			try {
				mRHistory = FileUtils.getMRJobHistory(logFiles[i]);
				requestStr1 = "http://172.29.132.196:8088/ws/v1/cluster/apps/" + mRHistory.getAppId();
				requestStr2 = "http://172.29.132.196:19888/ws/v1/history/mapreduce/jobs/" + mRHistory.getJobId();
				
				jsonStr1 = JSONUtils.getJsonString(requestStr1);
				jsonStr2 = JSONUtils.getJsonString(requestStr2);
				appJo1 = (new JSONObject(jsonStr1)).getJSONObject("app");
				mRHistory.setByJSON(appJo1);
				
				appJo2 = (new JSONObject(jsonStr2)).getJSONObject("job");
				mRHistory.setMPByJSON(appJo2);
				
				if(i == 1){
					tmpJobData = mRHistory.getTableHeader();
					jobData.add(tmpJobData);
				}
				
				tmpJobData = mRHistory.getMRHistoryTable();
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
			
			System.out.println(i+"added!");
		}
		return jobData;
	}

}
