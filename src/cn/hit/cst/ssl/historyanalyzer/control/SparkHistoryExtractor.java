package cn.hit.cst.ssl.historyanalyzer.control;

import org.json.JSONArray;

import cn.hit.cst.ssl.utils.JSONUtils;

public class SparkHistoryExtractor {
	public static void main(String args[]){
		//��hadoop��ҳ�а����ץȡ��Ϣ
		//����app id��spark rest api�������ץȡspark�����Ϣ
		//application_1463992848779_0001 -- 1010 �м���MR��ҵ
		String appSuffix = null, jsonRequestStr = null, jsonString = null;
		int rest;
		for(int i = 1; i < 1011; i++){
			appSuffix = String.valueOf(i);
			if ((rest = 4 - appSuffix.length()) > 0) {
				for(int j = 0; j < rest; j++){
					appSuffix = "0" + appSuffix;
				}
			}
			jsonRequestStr = "http://172.29.132.196:7078/api/v1/applications/application_1463992848779_"
					+ appSuffix + "/1/executors";
			try {
				jsonString = JSONUtils.getJsonString(jsonRequestStr);
				//TODO: judge if the json string we got is a mr application
				JSONArray jsonArray = new JSONArray(jsonString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
