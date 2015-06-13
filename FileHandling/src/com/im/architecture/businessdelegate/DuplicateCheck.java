package com.im.architecture.businessdelegate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DuplicateCheck {
	public final String keyColumnDuplicateCheck(List <String> keyColumns,HashMap<String,String> fileInfo,String filePath,String uniqueFilePath, String badFilePath){
		//System.out.println("keyColumns.size() : " + keyColumns.size());
		try {
			if(keyColumns.size() == 0){
				BufferedWriter bw_badFile = new BufferedWriter(new FileWriter(badFilePath));
				String delimiter = fileInfo.get("DELIMITER");
				String headerLine=fileInfo.get("FILE_HEADER");
				String badFileHeader = "Error Message"+delimiter+headerLine;
				bw_badFile.write(badFileHeader);
				bw_badFile.newLine();
				bw_badFile.close();
				return filePath;
			}
			else {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				BufferedWriter bw_uniqueFile = new BufferedWriter(new FileWriter(uniqueFilePath));
				BufferedWriter bw_badFile = new BufferedWriter(new FileWriter(badFilePath));
				String delimiter = fileInfo.get("DELIMITER");
				String splitChar = "\\"+delimiter+"(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
				String headerLine=fileInfo.get("FILE_HEADER");
				if(("Y").equalsIgnoreCase(fileInfo.get("FILE_HEADER_AVAILABLE"))){
					br.readLine();
					bw_uniqueFile.write(headerLine);
					bw_uniqueFile.newLine();
				}
				
				String badFileHeader = "Error Message"+delimiter+headerLine;
				bw_badFile.write(badFileHeader);
				bw_badFile.newLine();
				
				String[] colList = headerLine.split(splitChar,-1);
				String keyColumnCheck[] = new String[colList.length];
				for (int i=0; i < colList.length ; i++){
					keyColumnCheck[i] = "N";
					for (String str : keyColumns){
						if (str.equalsIgnoreCase(colList[i])) {
							keyColumnCheck[i] = "Y";
						}
					}
				}
				
				String currentLine;
				String[] dataList = new String[colList.length];
				String keyDataList="";
				HashMap<String,Integer> keyHashMap= new HashMap<String,Integer> ();
				
				while ((currentLine = br.readLine()) != null){
					dataList = currentLine.split(splitChar,-1);
					for(int i=0;i<dataList.length;i++){
						if("Y".equalsIgnoreCase(keyColumnCheck[i])){
							keyDataList += ","+dataList[i];
						}
					}
					keyDataList = keyDataList.substring(keyDataList.indexOf(",")+1);
					if (keyHashMap.containsKey(keyDataList)){
						keyHashMap.put(keyDataList, keyHashMap.get(keyDataList)+1);
					}
					else {
						keyHashMap.put(keyDataList, 1);
					}
				}
				
				//Remove Unique values
				keyHashMap.values().removeAll(Collections.singleton(1));
				
				br = new BufferedReader(new FileReader(filePath));
				if(("Y").equalsIgnoreCase(fileInfo.get("FILE_HEADER_AVAILABLE"))){
					br.readLine();
				}
				
				while ((currentLine = br.readLine()) != null){
					dataList = currentLine.split(splitChar,-1);
					for(int i=0;i<dataList.length;i++){
						if("Y".equalsIgnoreCase(keyColumnCheck[i])){
							keyDataList += ","+dataList[i];
						}
					}
					keyDataList = keyDataList.substring(keyDataList.indexOf(",")+1);
					if (keyHashMap.containsKey(keyDataList)){
						currentLine = "Duplicate Row" + delimiter + currentLine;
						bw_badFile.write(currentLine);
						bw_badFile.newLine();
					}
					else {
						bw_uniqueFile.write(currentLine);
						bw_uniqueFile.newLine();
					}
				}
				br.close();
				bw_uniqueFile.close();
				bw_badFile.close();
				for(String key : keyHashMap.keySet()){
					//System.out.println("Iterate : " + keyHashMap.get(key));
					if(keyHashMap.get(key) == 1){
					}
					//System.out.println("After removal" + keyHashMap.get(key));
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Error Occured while validating file. There is something wrong with data file.");
		}
		return uniqueFilePath;
	}
	
}
