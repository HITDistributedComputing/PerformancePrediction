package cn.hit.cst.ssl.bean;
/**
 * 
* @ClassName: ServerAvgTaskTime 
* @Description: the class that holds the avg task time and total history task count of a certain server
* @author Yukun Zeng
* @date May 30, 2016 4:31:53 PM 
*
 */
public class ServerAvgTaskTime{
	private Double avgTaskTime;
	private int taskCount;
	//here's the constructor that constucts an obj using a jobhistory.host data
	//regard a task as one avg unit
	/**
	 * 
	 * @Description: coupled with our program logic, constucts object when the first job of a type has finished
	 * @param totalDuration
	 * the longest total duration of executors in the first spark job
	 * @param totalTask
	 * the total task of the longest running executor in the first spark job
	 */
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
	/**
	 * 
	 * @Method: update
	 * @Description: update average task time of a job type on a certain server, here we see a job as a average unit
	 * @param newTaskTime
	 * the execution average task time in a new job
	 * @return void
	 */
	public void update(Double newTaskTime){
		this.avgTaskTime = ((this.avgTaskTime * taskCount) + newTaskTime) / (taskCount + 1);
		this.taskCount ++;
	}
	//IMPLEMENTATION 2
	//@param totalDuration
	//total duration of executing all tasks on a certain server
	//@param totalTask
	//the total task count a job have assigned on a certain server
	/**
	 * 
	 * @Method: update
	 * @Description: update average task time of a job type on a certain server, here we see a task as a average unit
	 * @param totalDuration
	 * total duration that a server used to processing all assigned task in a job
	 * @param totalTask
	 * total task count that a server was assigned when running a job
	 * @return void
	 */
	public void update(Double totalDuration, int totalTask){
		this.avgTaskTime = ((this.avgTaskTime * this.taskCount) + totalDuration) / (taskCount + totalTask);
		this.taskCount += totalTask;
	}
}
