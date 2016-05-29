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
	
	public abstract Double getX();
}

