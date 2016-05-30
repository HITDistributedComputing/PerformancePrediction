package cn.hit.cst.ssl.bean;

public abstract class YARNHistoryJob {
	protected String appId;
	//application name like: grep, wc
	protected String name;
	//type = framework type
	protected String type;
	protected double elapsedTime;
	protected double mbSec;
	protected double vcoreSec;
	/**
	 * 
	 * @Description: TODO
	 * @param appId
	 * yarn job id
	 * @param name
	 * name (Wordcount, Grep, etc) of a spark job
	 * @param type
	 * type of a yarn job, can be spark, mapreduce, etc
	 * @param elapsedTime
	 * the total time that a yarn job elapsed, same as the hadoop website
	 * @param mbSec
	 * total MB*Sec that a yarn job took, calculated by Hadoop
	 * @param vcoreSec
	 * total vcore*Sec that a yarn job took
	 */
	public YARNHistoryJob(String appId, String name, String type, 
			double elapsedTime, double mbSec,
			double vcoreSec){
		this.appId = appId;
		this.name = name;
		this.type = type;
		this.elapsedTime = elapsedTime;
		this.mbSec = mbSec;
		this.vcoreSec = vcoreSec;
	}
	/**
	 * 
	 * @Description: construct the obj directly by info read from our file
	 * @param line
	 * the line from the file that stores history info
	 */
	public YARNHistoryJob(String line){
		String[] lineArray = line.split("\t");
		this.appId = lineArray[0];
		this.name = lineArray[1];
		this.type = lineArray[2];
		this.elapsedTime = Double.valueOf(lineArray[3]);
		this.mbSec = Double.valueOf(lineArray[4]);
		this.vcoreSec = Double.valueOf(lineArray[5]);
	}
	
	//AppId name type input elapsedTime mb-sec vcore-sec
	/**
	 * 
	 * @Method: getJobHistoryTable
	 * @Description: get yarn job history info in table format
	 * @return String
	 * a line that stores yarn job history
	 */
	public String getJobHistoryTable(){
		return this.appId + "\t"
				+ this.name + "\t"
				+ this.type + "\t"
				+ this.mbSec + "\t"
				+ this.vcoreSec + "\t";
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getMbSec() {
		return mbSec;
	}

	public void setMbSec(double mbSec) {
		this.mbSec = mbSec;
	}

	public double getVcoreSec() {
		return vcoreSec;
	}

	public void setVcoreSec(double vcoreSec) {
		this.vcoreSec = vcoreSec;
	}
	/**
	 * 
	 * @Method: getX
	 * @Description: abstract method for returning the X that our model needs
	 * @return Double
	 * the X that our model needs
	 */
	public abstract Double getX();
}

