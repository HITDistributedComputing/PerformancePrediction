package cn.hit.cst.ssl.bean.jsonbean;

public class Job {
	private int jobId;
	private String name;
	private String status;
	private int []stageIds;
	private int numTasks;
	private int numCompletedStages;
	
	public Job(int jobId,String name,String submissionTime,String completionTime,
			String status,int numTasks, int[] stageIds, int numCompletedStages){
		this.jobId = jobId;
		this.name = name;
		this.status = status;
		this.stageIds = stageIds;
		this.numTasks = numTasks;
		this.numCompletedStages = numCompletedStages;
		
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNumTasks() {
		return numTasks;
	}

	public void setNumTasks(int numTasks) {
		this.numTasks = numTasks;
	}

	public int [] getStageIds() {
		return stageIds;
	}

	public void setStageIds(int [] stageIds) {
		this.stageIds = stageIds;
	}

	public int getNumCompletedStages() {
		return numCompletedStages;
	}

	public void setNumCompletedStages(int numCompletedStages) {
		this.numCompletedStages = numCompletedStages;
	}


	}


