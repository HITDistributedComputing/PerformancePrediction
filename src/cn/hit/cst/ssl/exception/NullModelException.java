package cn.hit.cst.ssl.exception;
/**
 * 
* @ClassName: NullModelException 
* @Description: the exception for throwing when the prediction model hasn't been initialized
* @author Yukun Zeng
* @date May 30, 2016 5:55:03 PM 
*
 */
public class NullModelException extends Exception {
	public NullModelException(String msg){
		super(msg + ": " + "The model for prediction hasn't been initialized.");
	}
}
