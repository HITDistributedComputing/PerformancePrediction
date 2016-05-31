package cn.hit.cst.ssl.historyanalyzer.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cn.hit.cst.ssl.utils.FileUtils;
/**
 * 
* @ClassName: HistoryUtils 
* @Description: History Utils to do some processing and simplifying work for our history files
* @author Yukun Zeng
* @date May 30, 2016 6:16:00 PM 
*
 */
public class HistoryUtils {
	//args[0]: input file path
	//args[1]: output file path
	public static void main(String[] args){
		try {
			simplifySparkHistory(args[0], args[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Method: simplifySparkHistory
	 * @Description: simplifies the history data we get from REST api
	 * @param filePath
	 * the input file path 
	 * @param newPath
	 * output file path
	 * @throws IOException
	 * @return void
	 */
	public static void simplifySparkHistory(String filePath, String newPath) throws IOException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		//get the header line;
		String header = reader.readLine(), host = null, newLine = "";
		String[] lineArray, nameArray;
		ArrayList<String> simplifiedFile = new ArrayList<String>();
		simplifiedFile.add(header + "\n");
		while (reader.ready()) {
			lineArray = reader.readLine().split("\t");
			//col 1=name, col 3=Host
			System.out.println(lineArray[0]);
			nameArray = (lineArray[1]).split("\\.");
			lineArray[1] = nameArray[nameArray.length - 1];
			host = lineArray[3];
			lineArray[3] = host.split(":")[0];
			for (String col : lineArray) {
				newLine += col + "\t";
			}
			simplifiedFile.add(newLine + "\n");
			newLine = "";
		}
		FileUtils.writeToFile(newPath, simplifiedFile);
	}
}
