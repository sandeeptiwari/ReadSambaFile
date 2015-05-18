package com.techiesandeep.utils;

/**
 * @author Sandeep Tiwari
 *
 */
public class ReadSmbException extends Exception{

	public static final int UNABLE_TO_CONNECT_SMB = 100;	
	public static final String UNABLE_TO_CONNECT_SMB_MSG = "Unable to connect samba server.";
	public static final int SERVER_AUTH_EXCEPTION = 101;
	public static final String SERVER_AUTH_EXCEPTION_MSG = "Samba server credentials is invalid.";
	public static final int MALFORMEDURLEXCEPTION = 102;
	public static final String MALFORMEDURLEXCEPTION_MSG = "Malformed url exception.";
	public static final int IOEXCEPTION = 103;
	public static final String IOEXCEPTION_MSG = "Unable to write file into local directory.";

	private String errorMessege;
	private int errorCode;

	public ReadSmbException(int code)
	{
		setException(code);
	}

	public int getErrorCode() {
		return errorCode;
	}

	private void setException(int code) {

		switch(code)
		{
		case UNABLE_TO_CONNECT_SMB:
			setErrorMessege(UNABLE_TO_CONNECT_SMB_MSG);
			errorCode = UNABLE_TO_CONNECT_SMB;
			break;
		case SERVER_AUTH_EXCEPTION:
			setErrorMessege(SERVER_AUTH_EXCEPTION_MSG);
			errorCode = SERVER_AUTH_EXCEPTION;
			break;
		case MALFORMEDURLEXCEPTION:
			setErrorMessege(MALFORMEDURLEXCEPTION_MSG);
			errorCode = MALFORMEDURLEXCEPTION;
			break;
		case IOEXCEPTION:
			setErrorMessege(IOEXCEPTION_MSG);
			errorCode = IOEXCEPTION;
			break;
		}
	}

	public String getErrorMessege() {
		return errorMessege;
	}

	private void setErrorMessege(String errorMessege) {
		this.errorMessege = errorMessege;
	}

}
