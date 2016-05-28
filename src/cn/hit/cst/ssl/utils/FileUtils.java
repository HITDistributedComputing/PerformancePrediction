package cn.hit.cst.ssl.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import cn.hit.cst.ssl.bean.JobHistory;
import cn.hit.cst.ssl.bean.jsonbean.MapReduceHistory;
import cn.hit.cst.ssl.bean.jsonbean.SparkJobHistory;

public class FileUtils {
	public static File[] iterateFiles(String path){
		File dir = new File(path);
		if (!dir.isDirectory()) {
			return null;
		}
		return dir.listFiles();
	}
	
	//iterate files in $path, from $prefix_0 to $prefix_$number
	public static File[] manItrFiles(String path, String prefix, int number){
		File[] files = new File[number + 1];
		for(int i = 1; i <= number; i++){
			files[i] =  new File(path + "//" + prefix + "_" + i);
		}
		return files;
	}
	
	public static JobHistory getJobHistory(File logFile) throws IOException{
		//start by line 10
		int lineNumber;
		FileReader fReader = new FileReader(logFile);
		LineNumberReader lReader = new LineNumberReader(fReader);
		String tmpLine = lReader.readLine(), appId = null, inputFile = null;
		String[] tmpArray;
		while (tmpLine != null) {
			tmpLine = lReader.readLine();
			if (lReader.getLineNumber() == 2) {
				tmpArray = tmpLine.split("/");
				inputFile = tmpArray[tmpArray.length - 1];
			}
			else if (tmpLine.contains("Submitted application")) {
				tmpArray = tmpLine.split(" ");
				appId = tmpArray[tmpArray.length - 1];
				break;
			}
		}
		return new JobHistory(appId, inputFile);
	}
	
	public static MapReduceHistory getMRJobHistory(File logFile) throws IOException{
		//start by line 10
		int lineNumber;
		FileReader fReader = new FileReader(logFile);
		LineNumberReader lReader = new LineNumberReader(fReader);
		String tmpLine = lReader.readLine(), appId = null, jobId = null, inputFile = null;
		String[] tmpArray;
		while (tmpLine != null) {
			tmpLine = lReader.readLine();
			if (lReader.getLineNumber() == 2) {
				tmpArray = tmpLine.split("/");
				inputFile = tmpArray[tmpArray.length - 1];
			}
			else if (tmpLine.contains("Submitted application")) {
				tmpArray = tmpLine.split(" ");
				appId = tmpArray[tmpArray.length - 1];
			}
			else if (tmpLine.contains("Submitting tokens for job")){
				tmpArray = tmpLine.split(" ");
				jobId = tmpArray[tmpArray.length - 1];
			}
			
			if(appId!=null && jobId!=null){
				break;
			}
		}
		return new MapReduceHistory(appId, jobId, inputFile);
	}
	
	public static SparkJobHistory getSparkJobHistory(File logFile) throws IOException{
		//start by line 10
		int lineNumber;
		FileReader fReader = new FileReader(logFile);
		LineNumberReader lReader = new LineNumberReader(fReader);
		String tmpLine = lReader.readLine(), appId = null, inputFile = null;
		String[] tmpArray;
		while (tmpLine != null) {
			tmpLine = lReader.readLine();
			if (lReader.getLineNumber() == 2) {
				tmpArray = tmpLine.split("/");
				inputFile = tmpArray[tmpArray.length - 1];
			}
			else if (tmpLine.contains("Submitted application")) {
				tmpArray = tmpLine.split(" ");
				appId = tmpArray[tmpArray.length - 1];
				break;
			}
		}
		return new SparkJobHistory(appId, inputFile);
	}
	
	public static void writeToFile(String filePath, ArrayList<String> jobData) throws IOException{
		File targetFile = new File(filePath);
		if (targetFile.exists()) {
			System.out.println("File Exists!");
			return;
		}
		else {
			targetFile.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(targetFile);
		BufferedWriter out = new BufferedWriter(fileWriter);
		for(int i = 0; i < jobData.size(); i ++){
			out.write(jobData.get(i));
		}
		out.close();
	}
    
    // 读取文件指定行。
	public static void readAppointedLineNumber(File sourceFile, int lineNumber)  throws IOException {  
        FileReader in = new FileReader(sourceFile);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = "";  
        if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {  
            System.out.println("不在文件的行数范围(1至总行数)之内。");  
            System.exit(0);  
        }  
        int lines = 0;  
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
            if((lines - lineNumber) == 0) {  
                System.out.println(s);  
                System.exit(0);  
            }  
        }  
        reader.close();  
        in.close();  
    }  
	
	// 文件内容的总行数。  
    public static int getTotalLines(File file) throws IOException {  
        FileReader in = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = reader.readLine();  
        int lines = 0;  
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
            if(lines>=2){  
                if(s!=null){  
                    System.out.println(s+"$");  
                }  
            }  
        }  
        reader.close();  
        in.close();  
        return lines;  
    }  
    
    public static ArrayList<ArrayList<String>> getSpecifiedCols(String filePath, int[] colNum) throws IOException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> specifiedCols = null;
		String line = null;
		String[] lineArray = null;
		reader.readLine();
		while (reader.ready()) {
			line = reader.readLine();
			lineArray = line.split("\t");
			specifiedCols = new ArrayList<String>();
			for(int i = 0; i < colNum.length; i++){
				specifiedCols.add(lineArray[colNum[i]]);
			}
			result.add(specifiedCols);
		}
		return result;
	}
}
