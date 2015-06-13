package com.im.architecture.utility;

import java.io.File;

public class LocationException {
	public static boolean checkFileExist(String filePath) {
		File f = new File(filePath);
		if (f.isFile() && f.canRead()){
			return true;
		}
		return false;
	}
	
	public boolean fileNotExistsException(String fileType,String filePath){
		if (checkFileExist(filePath)){
			return true;
		}
		throw new RuntimeException(fileType + " does not exist at the mentioned path");
	}
	
	public boolean fileExistsException(String fileType,String filePath){
		if (checkFileExist(filePath)){
			throw new RuntimeException(fileType + "exists at the mentioned path");
		}
		return true;
	}
	
	public boolean DirExistsException(String fileType,String filePath){
		File f = new File(filePath);
		String absolutePath = f.getAbsolutePath();
		String dirPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		File dir = new File(dirPath);
		if (dir.exists() && dir.isDirectory()){
			return true;
		}
		throw new RuntimeException("Path of " + fileType + " : " + dirPath + " does not exist");
	}
}
