package com.techiesandeep.smbimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import com.techiesandeep.smbinetrface.JCIFSInterface;
import com.techiesandeep.utils.ReadSmbException;
import com.techiesandeep.utils.SmbConstant;
import com.techiesandeep.utils.SmbUtils;

/**
 * @author Sandeep Tiwari
 *
 */
public class SambaFileReader implements JCIFSInterface{

	private NtlmPasswordAuthentication auth = null;
	private String smbDirectory;
	private HashMap<String,String> fileMapping = new HashMap<String,String>();
	
	@Override
	public void connectViaSamba() throws ReadSmbException {
		
		StringBuffer sf = new StringBuffer(); 
		String domain = null, userName = null, password = null, ipAddress = null;

		if(SmbConstant.smbDomain != null 
				&& SmbConstant.smbDomain.length() > 0)
		{
			domain = SmbConstant.smbDomain;
		}

		if(SmbConstant.smbUserName != null 
				&& SmbConstant.smbUserName.length() > 0)
		{
			userName = SmbConstant.smbUserName;
		}

		if(SmbConstant.smbPassword != null 
				&& SmbConstant.smbPassword.length() > 0)
		{
			password = SmbConstant.smbPassword;
		}
		
		ipAddress = SmbConstant.smbIp;
		if(ipAddress != null && ipAddress.length() > 0)
		{
			char firstLetter = ipAddress.charAt(0);
			boolean isDigit = Character.isDigit(firstLetter);
			if(!isDigit)
			{
				ipAddress = SmbUtils.convertSmbAddress(ipAddress);
			}
		}

		
		auth = new NtlmPasswordAuthentication(domain, userName, password);

		if(auth == null)
		{
			throw new ReadSmbException(ReadSmbException.SERVER_AUTH_EXCEPTION);
		}

		smbDirectory = sf.append(SmbConstant.PROTOCOL)
				.append(SmbConstant.COLON_SLASH)
				.append(ipAddress).append(SmbConstant.SLASH)
				.append(SmbConstant.smbDirectory)
				.append(SmbConstant.SLASH).toString();
	}

	/* 
	 * Return Smbfle object for inputed file.
	 * */
	@Override
	public SmbFile getSmb(String fileName) throws ReadSmbException {
		SmbFile smbFile = null;
		String filePath = null;
		try {
			if(!fileName.equals(""))
			{
				StringBuffer sf = new StringBuffer("");
				filePath = getFileMappingPath(fileName);
				if(filePath != null )
				{
					sf.append(filePath);
				}
				return smbFile = new SmbFile(sf.toString(), auth);
			}
			else if(fileName.equals(""))
			{
				return smbFile = new SmbFile(smbDirectory, auth);
			}
		}catch (MalformedURLException e) {
			throw new ReadSmbException(ReadSmbException.MALFORMEDURLEXCEPTION);
		}
		return smbFile;
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public String getFileMappingPath(String fileName){
		return fileMapping.get(fileName);
	}
	
	/**
	 * @param fileName
	 * @param filePath
	 */
	public void addFileMapping(String fileName, String filePath){
		fileMapping.put(fileName, filePath);
	}

	

	/* 
	 * @param pass fileName which you want to to read from samba server.
	 * Steps:- 1: get SmbFile object of particular passed by you.
	 *         2: make SmbFileInputStream 
	 *         3: write that stream into your local drive.
	 */
	@Override
	public String fetchSmbFileFromSambaAndWriteFileIntoLocalDirectory(String fileName) throws ReadSmbException {
		
		SmbFile smbFile = getSmb(fileName);

		OutputStream outputStream = null;
		String cFileName = fileName;

		SmbFileInputStream inputStream = null;
		try {
			inputStream = new SmbFileInputStream(smbFile);
			
		} catch (SmbException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		if(inputStream != null)
		{
			return "";
		}
		
		try{
				int read = 0;
				byte[] bytes = new byte[1024];			
				File file = new File(SmbConstant.localDirectoryPath + "\\" + cFileName);
				if(file.exists())
				{
					file.delete();
				}					
				outputStream = new FileOutputStream(file);
				while ((read = inputStream.read(bytes)) != -1) 
				{
					outputStream.write(bytes, 0, read);
				}
		}
		catch (IOException e) {
			 throw new ReadSmbException(ReadSmbException.IOEXCEPTION);
		 }
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		return (SmbConstant.localDirectoryPath + "\\" + cFileName);
	
	}

	
}
