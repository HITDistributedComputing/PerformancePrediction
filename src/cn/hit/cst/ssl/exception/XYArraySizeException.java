package cn.hit.cst.ssl.exception;

public class XYArraySizeException extends Exception {
	public XYArraySizeException(String msg){
		super(msg + ": X Y ArrayList elements count unequal.");
	}
}
