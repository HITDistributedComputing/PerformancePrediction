package cn.hit.cst.ssl.bean;

public class ServerAvgTaskTime{
	private Double avgTaskTime;
	private int taskCount;
	//here's the constructor that constucts an obj using a jobhistory.host data
	//regard a task as one avg unit
	public ServerAvgTaskTime(Double totalDuration, int totalTask){
		super();
		this.avgTaskTime = totalDuration / totalTask;
		this.taskCount = totalTask;
	}

	public Double getAvgTaskTime() {
		return avgTaskTime;
	}

	public void setAvgTaskTime(Double avgTaskTime) {
		this.avgTaskTime = avgTaskTime;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	//the only function update() for updating avgTaskTime
	//2 implementations:
	//1. we see each job as an unit for avgTaskTime calculation
	//2. we see each task in a job as an unit for avgTaskTime calculation
	//TODO:
	//1. update avgTT D
	//2. update taskCount D
	//3. problem still exists: whether it's proper to see a job as an average unit
	//(we can substitute by using a task as an average unit
	//IMPLEMENTATION 1
	//@param newTaskTime
	//avg task time on of a type of job on a certain server that has just been added to job history hosts list
	public void update(Double newTaskTime){
		this.avgTaskTime = ((this.avgTaskTime * taskCount) + newTaskTime) / (taskCount + 1);
		this.taskCount ++;
	}
	//IMPLEMENTATION 2
	//@param totalDuration
	//total duration of executing all tasks on a certain server
	//@param totalTask
	//the total task count a job have assigned on a certain server
	public void update(Double totalDuration, int totalTask){
		this.avgTaskTime = ((this.avgTaskTime * this.taskCount) + totalDuration) / (taskCount + totalTask);
		this.taskCount += totalTask;
	}
}
