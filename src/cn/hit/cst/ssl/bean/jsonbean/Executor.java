package cn.hit.cst.ssl.bean.jsonbean;

public class Executor{
	private int id;
	private String host;
	private int taskCount;
	private int duration;
	private long inputBytes;
	private long shuffleRead;
	private long shuffleWrite;
	private long maxMem;
	
	public Executor(int id, String host, int taskCount, int duration, long inputBytes, long shuffleRead,
			long shuffleWrite, long maxMem) {
		super();
		this.id = id;
		this.host = host;
		this.taskCount = taskCount;
		this.duration = duration;
		this.inputBytes = inputBytes;
		this.shuffleRead = shuffleRead;
		this.shuffleWrite = shuffleWrite;
		this.maxMem = maxMem;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getTaskCount() {
		return taskCount;
	}
	
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public long getInputBytes() {
		return inputBytes;
	}
	
	public void setInputBytes(long inputBytes) {
		this.inputBytes = inputBytes;
	}
	
	public long getShuffleRead() {
		return shuffleRead;
	}
	
	public void setShuffleRead(long shuffleRead) {
		this.shuffleRead = shuffleRead;
	}
	
	public long getShuffleWrite() {
		return shuffleWrite;
	}
	
	public void setShuffleWrite(long shuffleWrite) {
		this.shuffleWrite = shuffleWrite;
	}
	
	public long getMaxMem() {
		return maxMem;
	}
	
	public void setMaxMem(long maxMem) {
		this.maxMem = maxMem;
	}
}
