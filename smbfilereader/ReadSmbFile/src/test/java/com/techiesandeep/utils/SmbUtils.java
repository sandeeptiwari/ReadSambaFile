package com.techiesandeep.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SmbUtils {
	
	/**
	 * @param hostName
	 * @return
	 * @purpose convert DNS to its particular IP address.
	 */
	public static String convertSmbAddress(String hostName){

		InetAddress inetAddress = null;;
		try {
			inetAddress = InetAddress.getByName(hostName);
			return inetAddress.getHostAddress();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

}
