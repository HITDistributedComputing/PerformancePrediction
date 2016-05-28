package cn.hit.cst.ssl.bean;

public abstract class YARNHistoryJob {
	protected String appId;
	//application name like: grep, wc
	protected String name;
	//type = framework type
	protected String type;
	protected int elapsedTime;
	protected long mbSec;
	protected int vcoreSec;
	
	public YARNHistoryJob(String appId, String name, String type, 
			int elapsedTime, long mbSec,
			int vcoreSec){
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
		this.elapsedTime = Integer.valueOf(lineArray[3]);
		this.mbSec = Long.valueOf(lineArray[4]);
		this.vcoreSec = Integer.valueOf(lineArray[5]);
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

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getMbSec() {
		return mbSec;
	}

	public void setMbSec(long mbSec) {
		this.mbSec = mbSec;
	}

	public int getVcoreSec() {
		return vcoreSec;
	}

	public void setVcoreSec(int vcoreSec) {
		this.vcoreSec = vcoreSec;
	}
	
	public abstract Double getX();
}

