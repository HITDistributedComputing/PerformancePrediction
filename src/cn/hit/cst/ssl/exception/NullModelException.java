package cn.hit.cst.ssl.exception;

public class NullModelException extends Exception {
	public NullModelException(String msg){
		super(msg + ": " + "The model for prediction hasn't been initialized.");
	}
}
