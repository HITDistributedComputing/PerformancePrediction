package cn.hit.cst.ssl.bean.jsonbean;

public class Stage {
	private int stageId;
	private int attemptId;
	private int numCompleteTask;
	private long executorRunTime;
	private long inputBytes;
	private long inputRecords;
	private long outputBytes;
	private long outputRecords;
	private long shuffleReadBytes;
	private long shuffleReadRecords;
	private long shuffleWriteBytes;
	private long shuffleWriteRecords;
	private long memoryBytesSpilled;
	private long diskBytesSpilled;
	
	public Stage(int stageId,int attemptId,int numCompleteTask,long executorRunTime,
			long inputBytes,long inputRecords,long outputBytes,long outputRecords,
			long shuffleReadBytes,long shuffleReadRecords,long shuffleWriteBytes,
			long shuffleWriteRecords,long memoryBytesSpilled,long diskBytesSpilled){
		super();
		this.stageId = stageId;
		this.attemptId = attemptId;
		this.numCompleteTask = numCompleteTask;
		this.executorRunTime = executorRunTime;
		this.inputBytes = inputBytes;
		this.inputRecords = inputRecords;
		this.outputBytes = outputBytes;
		this.outputRecords = outputRecords;
		this.shuffleReadBytes = shuffleReadBytes;
		this.shuffleReadRecords = shuffleReadRecords;
		this.shuffleWriteBytes = shuffleWriteBytes;
		this.shuffleWriteRecords = shuffleWriteRecords;
		this.memoryBytesSpilled = memoryBytesSpilled;
		this.diskBytesSpilled = diskBytesSpilled;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(int attemptId) {
		this.attemptId = attemptId;
	}
	public int getNumCompleteTask() {
		return numCompleteTask;
	}
	public void setNumCompleteTask(int numCompleteTask) {
		this.numCompleteTask = numCompleteTask;
	}
	public long getExecutorRunTime() {
		return executorRunTime;
	}
	public void setExecutorRunTime(long executorRunTime) {
		this.executorRunTime = executorRunTime;
	}
	public long getInputBytes() {
		return inputBytes;
	}
	public void setInputBytes(long inputBytes) {
		this.inputBytes = inputBytes;
	}
	public long getInputRecords() {
		return inputRecords;
	}
	public void setInputRecords(long inputRecords) {
		this.inputRecords = inputRecords;
	}
	public long getOutputBytes() {
		return outputBytes;
	}
	public void setOutputBytes(long outputBytes) {
		this.outputBytes = outputBytes;
	}
	public long getOutputRecords() {
		return outputRecords;
	}
	public void setOutputRecords(long outputRecords) {
		this.outputRecords = outputRecords;
	}
	public long getShuffleReadBytes() {
		return shuffleReadBytes;
	}
	public void setShuffleReadBytes(long shuffleReadBytes) {
		this.shuffleReadBytes = shuffleReadBytes;
	}
	public long getShuffleReadRecords() {
		return shuffleReadRecords;
	}
	public void setShuffleReadRecords(long shuffleReadRecords) {
		this.shuffleReadRecords = shuffleReadRecords;
	}
	public long getShuffleWriteBytes() {
		return shuffleWriteBytes;
	}
	public void setShuffleWriteBytes(long shuffleWriteBytes) {
		this.shuffleWriteBytes = shuffleWriteBytes;
	}
	public long getShuffleWriteRecords() {
		return shuffleWriteRecords;
	}
	public void setShuffleWriteRecords(long shuffleWriteRecords) {
		this.shuffleWriteRecords = shuffleWriteRecords;
	}
	public long getMemoryBytesSpilled() {
		return memoryBytesSpilled;
	}
	public void setMemoryBytesSpilled(long memoryBytesSpilled) {
		this.memoryBytesSpilled = memoryBytesSpilled;
	}
	public long getDiskBytesSpilled() {
		return diskBytesSpilled;
	}
	public void setDiskBytesSpilled(long diskBytesSpilled) {
		this.diskBytesSpilled = diskBytesSpilled;
	}
	
}
