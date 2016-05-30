package cn.hit.cst.ssl.bean;

import java.util.ArrayList;

import cn.hit.cst.ssl.bean.jsonbean.Host;
import cn.hit.cst.ssl.utils.SLLNode;
import cn.hit.cst.ssl.utils.SortedLinkedList;

//only use addHost() to add host, which could guarantee the hosts array to be descendingly sorted
/**
 * 
* @ClassName: SparkHistoryJob 
* @Description: the class that holds more specific job history data of spark jobs than its super class
* @author Yukun Zeng
* @date May 30, 2016 4:39:29 PM 
*
 */
public class SparkHistoryJob extends YARNHistoryJob {
	/**
	 * descending arraylist 
	 */
	private ArrayList<Host> hosts;
	/**
	 * 
	 * @Description: General Constructor
	 * @param appId
	 * see in super constructor
	 * @param name
	 * see in super constructor
	 * @param type
	 * always spark here, see in super constructor
	 * @param hosts
	 * executors that has once be assigned to run the spark job
	 * @param elapsedTime
	 * see in super constructor
	 * @param mbSec
	 * see in super constructor
	 * @param vcoreSec
	 * see in super constructor
	 */
	public SparkHistoryJob(String appId, String name, String type, ArrayList<Host> hosts, 
			double elapsedTime, double mbSec, double vcoreSec) {
		super(appId, name, type, elapsedTime, mbSec,
				vcoreSec);
		this.hosts = hosts;
		sortHostByDuration();
	}
	/**
	 * 
	 * @Description: construct a history job directly from a formatted line
	 * @param 
	 * line Format: AppId\tName\tType\tHosts\tElapsed\tMBSec\tVCoreSec
	 */
	public SparkHistoryJob(String line){
		super(line);
		String[] lineArray = line.split("\t");
		this.hosts = new ArrayList<Host>();
		for (int i = 6; i < lineArray.length; i+= 4) {
			this.addHost(new Host(lineArray[i], Long.valueOf(lineArray[i + 1]), 
					Integer.valueOf(lineArray[i + 2]), Double.valueOf(lineArray[i + 3])));
		}
	}
	
	//AppId name type input elapsedTime mb-sec vcore-sec
	@Override
	public String getJobHistoryTable(){
		return super.getJobHistoryTable() 
				+ this.getLongestHost().getTotalDuration() + "\t";
	}
	/**
	 * 
	 * @Method: getNewSparkHistoryJob
	 * @Description: coupled with SparkHistoryMerger, which merges all hosts of a job into one line,
	 * and this is to return the merged new line
	 * @return String new, merged line of a spark job
	 */
	public String getNewSparkHistoryJob(){
		String newSparkHistoryJob = appId + "\t" + name + "\t" + type + "\t" 
				+ elapsedTime + "\t" + mbSec + "\t" + vcoreSec + "\t";
		for (Host host : this.hosts) {
			newSparkHistoryJob += host.getHostTable();
		}
		return newSparkHistoryJob + "\n";
	}
	/**
	 * 
	 * @Method: sortHostByDuration
	 * @Description: Sort the host linked list by their duration, in descending order
	 * @return void
	 */
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
	
	public Double getX(){
		return hosts.get(0).getTotalDuration();
	}
	/**
	 * 
	 * @Method: getXbyTaskTime
	 * @Description: TODO
	 * @param avgTaskTime 
	 * compute X (in this model we use longest total duration) through avg task time of the longest host
	 * @return Double
	 * X (longest total duration)
	 */
	public Double getXbyTaskTime(Double avgTaskTime){
		return this.getLongestHost().getTotalTask() * avgTaskTime; 
	}
	/**
	 * 
	 * @Method: addHost
	 * @Description: add host to the ordered linked list, descending order
	 * @param host
	 * target host to add
	 * @return void
	 */
	public void addHost(Host host){
		this.hosts.add(host);
		sortHostByDuration();
	}
	/**
	 * 
	 * @Method: getLongestHost
	 * @Description: directly return the first hosts in our arraylist
	 * @return Host
	 */
	public Host getLongestHost(){
		return this.hosts.get(0);
	}
}
