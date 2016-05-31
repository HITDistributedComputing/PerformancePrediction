package cn.hit.cst.ssl.historyanalyzer.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.hit.cst.ssl.bean.jsonbean.SparkJobHistory;
import cn.hit.cst.ssl.utils.FileUtils;
import cn.hit.cst.ssl.utils.JSONUtils;
/**
 * 
* @ClassName: SparkHistoryExtractor 
* @Description: extract spark history job data
* @author Wenhao Fan
* @date May 31, 2016 10:14:59 AM 
*
 */
public class SparkHistoryExtractor {

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
		String jsonStr, jsonStr2, requestStr, requestStr2, tmpJobData;
		SparkJobHistory sparkJobHistory;
		JSONObject temp;
		JSONObject appJo;
		ArrayList<String> jobData = new ArrayList<String>();
		
		//int extendedFileNum = 1000;

		for(int i=600;i<1001;i++){
			try {
				sparkJobHistory = FileUtils.getSparkJobHistory(logFiles[i]);
				requestStr = "http://172.29.132.196:7078/api/v1/applications/"+sparkJobHistory.getAppId()+"/1/executors";
				requestStr2 = "http://172.29.132.196:8088/ws/v1/cluster/apps/" + sparkJobHistory.getAppId();
				jsonStr = JSONUtils.getJsonString(requestStr);
				jsonStr2 = JSONUtils.getJsonString(requestStr2);
				JSONArray jArray = new JSONArray(jsonStr);
				appJo = (new JSONObject(jsonStr2)).getJSONObject("app");
				
				if(i==1){
					tmpJobData = sparkJobHistory.getTableHeader();
					jobData.add(tmpJobData);
				}
				
				for(int k=0;k<jArray.length();k++){
					temp = jArray.getJSONObject(k);
					String tmpstr = temp.getString("id");
					if(!tmpstr.equals("driver")){
						sparkJobHistory.setByJSON(appJo);
						sparkJobHistory.setSparkByJSON(temp);
						tmpJobData = sparkJobHistory.getJobHistoryTable();
						jobData.add(tmpJobData);
					}
				}
				System.out.println(i + "record added!");
				/*sparkJobHistory1.setByJSON(appJo1);
				appJo1 = jArray.getJSONObject(0);
				sparkJobHistory1.setByJSON(appJo1);
				appJo2 = jArray.getJSONObject(2);
				sparkJobHistory2.setByJSON(appJo2);
				tmpJobData = sparkJobHistory1.getJobHistoryTable();
				jobData.add(tmpJobData);
				tmpJobData = sparkJobHistory2.getJobHistoryTable();
				jobData.add(tmpJobData);*/
			
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				System.out.println("Crashed record on iteration: " );
				System.out.println(i);
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
