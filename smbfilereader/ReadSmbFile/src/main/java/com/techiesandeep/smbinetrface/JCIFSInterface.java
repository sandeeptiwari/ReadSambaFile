package com.techiesandeep.smbinetrface;

import jcifs.smb.SmbFile;

import com.techiesandeep.utils.ReadSmbException;

public interface JCIFSInterface {

	public void connectViaSamba() throws ReadSmbException;
	public SmbFile getSmb(String fileName) throws ReadSmbException;
	public String fetchSmbFileFromSambaAndWriteFileIntoLocalDirectory(String fileName) throws ReadSmbException;
}
