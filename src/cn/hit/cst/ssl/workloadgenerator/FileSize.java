package cn.hit.cst.ssl;
public class FileSize {
	static public String SizeHandle(long size){
		long fiveHundredMB = 500000000;
		String[] fileName = {"google_100000,", "google_10000,", "google_1000,", "google_100,", "google_10,", "google_1,"};	
		String str = new String("{");
		//make up with 500MB
		long numOfFiveHundredMB = size / fiveHundredMB;		
		for(int i = 0; i < numOfFiveHundredMB; i++){
			str += "wiki_0000000,";
		}		
		//deal with remainder
		size %= fiveHundredMB;
		size /= 1000;
		String sRemainder = String.valueOf(size);
		char[] cRemainder = sRemainder.toCharArray();
		int length = cRemainder.length;
		
		for(int j = 0; j < length; j++){
			for(int k = 0; k < cRemainder[j] - 48; k++){
				str += fileName[6 - length + j];
			}
		}
		str = str.substring(0,str.length()-1);
		str += "}";
		return str;
	}
}
