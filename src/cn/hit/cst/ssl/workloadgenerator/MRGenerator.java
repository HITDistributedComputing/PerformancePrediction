package cn.hit.cst.ssl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MRGenerator extends JobGenerator{
	
	public MRGenerator(){
		//TODO: directory problem, how to get the jobs/MRJobs file path?
		jobs = new ArrayList<Job>();
		try {
			jobsReader("/home/hadoop/workspace/workloads/jobs/MRJobs_testload");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
