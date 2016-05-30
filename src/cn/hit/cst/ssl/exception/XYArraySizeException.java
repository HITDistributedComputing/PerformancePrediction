package cn.hit.cst.ssl.exception;
/**
 * 
* @ClassName: XYArraySizeException 
* @Description: the exception for throwing when x y array share different sizes
* @author Yukun Zeng
* @date May 30, 2016 5:55:58 PM 
*
 */
public class XYArraySizeException extends Exception {
	public XYArraySizeException(String msg){
		super(msg + ": X Y ArrayList elements count unequal.");
	}
}
