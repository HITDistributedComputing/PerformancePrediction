package cn.hit.cst.ssl.bean.jsonbean;

import cn.hit.cst.ssl.utils.Sortable;

public class Host extends Sortable{
	private String hostPort;
	private long inputSize;
	private int totalTask;
	private int totalDuration;
	
	public Host(String hostPort, long inputSize, int totalTask, int totalDuration) {
		super();
		this.hostPort = hostPort;
		this.inputSize = inputSize;
		this.totalTask = totalTask;
		this.totalDuration = totalDuration;
	}
	
	public String getHostTable(){
		return this.hostPort + "\t" + this.inputSize + "\t" + this.totalTask + "\t"
				+ this.totalDuration + "\t";
	}

	public String getHostPort() {
		return hostPort;
	}

	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	public long getInputSize() {
		return inputSize;
	}

	public void setInputSize(long inputSize) {
		this.inputSize = inputSize;
	}

	public int getTotalTask() {
		return totalTask;
	}

	public void setTotalTask(int totalTask) {
		this.totalTask = totalTask;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	@Override
	public int getMyKey() {
		// TODO Auto-generated method stub
		return this.getTotalDuration();
	}
}
