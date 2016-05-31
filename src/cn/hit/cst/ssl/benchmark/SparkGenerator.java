package cn.hit.cst.ssl.benchmark;

import java.io.IOException;
import java.util.ArrayList;

public class SparkGenerator extends JobGenerator {
	public SparkGenerator(){
		//TODO: directory problem, how to get the jobs/MRJobs file path?
		jobs = new ArrayList<Job>();
		try {
			//jobsReader("/home/hadoop/workspace/workloads/jobs/SparkJobs_testload");
			jobsReader("../jobs/SparkJobs_testload");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
