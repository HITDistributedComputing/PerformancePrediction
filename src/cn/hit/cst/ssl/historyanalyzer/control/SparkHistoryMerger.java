package cn.hit.cst.ssl.historyanalyzer.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cn.hit.cst.ssl.bean.SparkHistoryJob;
import cn.hit.cst.ssl.bean.jsonbean.Host;
import cn.hit.cst.ssl.utils.FileUtils;

public class SparkHistoryMerger {
	
	public static void main(String args[]){
		// TODO Auto-generated method stub
		File inputFile = new File(args[0]);
		String line1, appId, lastApp;
		ArrayList<String> jobData = new ArrayList<String>();
		String[] line1Array;
		ArrayList<SparkHistoryJob> lines = new ArrayList<SparkHistoryJob>();
		ArrayList<Host> hosts = new ArrayList<Host>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			//get the header line
			reader.readLine();
			jobData.add("AppId\tName\tType\tElapsed\tMB-Sec\tVCoreSec\tHostPort\tInputSize\tTotalTasks\tTotalDuration"
					+ "\tHostPort2\tInputSize\tTotalTasks\tTotalDuration\n");
			//iterate the rest lines and do combinations
			while (reader.ready()) {
				line1 = reader.readLine();
				line1Array = line1.split("\t");
				lastApp = line1Array[0];
				hosts = new ArrayList<Host>();
				hosts.add(new Host(line1Array[3], Long.valueOf(line1Array[4]), 
						Integer.valueOf(line1Array[5]), Integer.valueOf(line1Array[6])));
				lines.add(new SparkHistoryJob(line1Array[0], line1Array[1], line1Array[2], hosts, 
						Integer.valueOf(line1Array[7]), Long.valueOf(line1Array[8]), Integer.valueOf(line1Array[9])));
				while (true) {
					reader.mark(102400);
					line1 = reader.readLine();
					try {
						line1Array = line1.split("\t");
					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						break;
					}
					System.out.println(line1Array[0] + " " + line1Array[3]);
					if (line1Array[0].equals(lastApp)) {
						hosts.add(new Host(line1Array[3], Long.valueOf(line1Array[4]), 
						Integer.valueOf(line1Array[5]), Integer.valueOf(line1Array[6])));
					}
					else {
						reader.reset();
						break;
					}
				}
			}
			for (SparkHistoryJob line : lines) {
				jobData.add(line.getNewSparkHistoryJob());
			}
			FileUtils.writeToFile(args[1], jobData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
