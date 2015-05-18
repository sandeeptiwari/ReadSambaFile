package com.techiesandeep.main;

import com.techiesandeep.smbimpl.SambaFileReader;
import com.techiesandeep.smbinetrface.JCIFSInterface;
import com.techiesandeep.utils.ReadSmbException;

/**
 * @author Sandeep Tiwari
 *
 */
public class ReadSmbMain {
	
	
	
	public static void main(String ...args)
	{
		JCIFSInterface jcifc = new SambaFileReader();
		/* Connect Samba server */
		try {
			 jcifc.connectViaSamba();
			
		} catch (ReadSmbException e) {
			e.printStackTrace();
		}
		try {
			String localPath = jcifc.fetchSmbFileFromSambaAndWriteFileIntoLocalDirectory("mySambafile.csv");
			System.out.println(localPath);
		} catch (ReadSmbException e) {
			e.printStackTrace();
		}
	}
	

}
