package cn.hit.cst.ssl.bean;

import java.util.ArrayList;

import cn.hit.cst.ssl.bean.jsonbean.Host;
import cn.hit.cst.ssl.utils.SLLNode;
import cn.hit.cst.ssl.utils.SortedLinkedList;

//only use addHost() to add host, which could guarantee the hosts array to be descendingly sorted
public class SparkHistoryJob extends YARNHistoryJob {
	private ArrayList<Host> hosts;
	
	public SparkHistoryJob(String appId, String name, String type, ArrayList<Host> hosts, int elapsedTime, long mbSec,
			int vcoreSec) {
		super(appId, name, type, elapsedTime, mbSec,
				vcoreSec);
		this.hosts = hosts;
		sortHostByDuration();
	}
	//construct a history job directly from a formatted line
	//Format: AppId\tName\tType\tHosts\tElapsed\tMBSec\tVCoreSec
	public SparkHistoryJob(String line){
		super(line);
		String[] lineArray = line.split("\t");
		this.hosts = new ArrayList<Host>();
		for (int i = 6; i < lineArray.length; i+= 4) {
			this.addHost(new Host(lineArray[i], Long.valueOf(lineArray[i + 1]), 
					Integer.valueOf(lineArray[i + 2]), Integer.valueOf(lineArray[i + 3])));
		}
	}
	
	public String getNewSparkHistoryJob(){
		String newSparkHistoryJob = appId + "\t" + name + "\t" + type + "\t" 
				+ elapsedTime + "\t" + mbSec + "\t" + vcoreSec + "\t";
		for (Host host : this.hosts) {
			newSparkHistoryJob += host.getHostTable();
		}
		return newSparkHistoryJob + "\n";
	}
	
	public void sortHostByDuration(){
		SortedLinkedList<Host> sortedLinkedList = new SortedLinkedList<Host>(hosts.get(0));
		for(int i = 1; i < hosts.size(); i++){
			sortedLinkedList.addNode(new SLLNode<Host>(hosts.get(i)));
		}
		this.hosts = sortedLinkedList.iterateListNodeElements(); 
	}

	public ArrayList<Host> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
		sortHostByDuration();
	}
	
	public int getX(){
		return hosts.get(0).getTotalDuration();
	}
	
	public void addHost(Host host){
		this.hosts.add(host);
		sortHostByDuration();
	}
}
