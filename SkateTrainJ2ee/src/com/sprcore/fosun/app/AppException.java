package com.sprcore.fosun.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author chensm
 *
 */
public class AppException extends RuntimeException {
	private Log logger = LogFactory.getLog(getClass()); 
	public AppException(String message) {
		this.message = message;
	}

	public AppException(Exception e) {
		e.printStackTrace();
		this.e = e;
		this.message = e.getMessage();
		logger.info("e.getMessage()="+e.getMessage());
		
	}

	public String message;
	public Exception e;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	@Override
	public void printStackTrace() {
		
	}
	
	

}

