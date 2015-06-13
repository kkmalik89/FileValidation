package com.im.architecture.businessdelegate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import com.im.architecture.utility.CheckUtils;

public class RecordCheck {
	public final void checkColumnLevelRule(HashMap<String,String> fileInfo, List <HashMap> stringRules, List <HashMap> numericRules, List <HashMap> dateRules, String filePath,String goodFilePath,String badFilePath){
		try{
			//System.out.println("FIle Reader");
			int bad_flag=0;
			boolean validLengthFlag;
			boolean validNullableFlag;
			boolean validLovFlag;
			boolean validPkFlag;
			boolean validPrecisonFlag;
			boolean validDateFormatFlag;
			boolean validNumberFlag;
			boolean validRangeFlag;
			int i,j;
			String doubleQuote="\"";
			String headerLine;
			String currentLine;
			String badFileHeader;
			String colName;
			String errorMsg = "";
			String delimiter = fileInfo.get("DELIMITER");
			String splitChar = "\\"+delimiter+"(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			BufferedWriter bw_badFile = new BufferedWriter(new FileWriter(badFilePath,true));
			BufferedWriter bw_goodFile = new BufferedWriter(new FileWriter(goodFilePath));
			headerLine = fileInfo.get("FILE_HEADER");
			if(("Y").equalsIgnoreCase(fileInfo.get("FILE_HEADER_AVAILABLE"))){
				currentLine = br.readLine();
				//System.out.println("currentLine " + currentLine);
			}		
			bw_goodFile.write(headerLine);
			bw_goodFile.newLine();
			
			String[] colList = headerLine.split(splitChar,-1);
			String[] dataList = new String[colList.length]; 
			String[] stringRuleCheck = new String[colList.length];
			String[] numericRuleCheck = new String[colList.length];
			String[] dateRuleCheck = new String[colList.length];
			
			CheckUtils  objCheckUtils = new CheckUtils();
			
			//System.out.println("COl List length : " + colList.length);
			
			for(i=0;i<stringRuleCheck.length;i++){
				stringRuleCheck[i] =  "N";
				for(j=0;j<stringRules.size();j++){
					colName = (String) stringRules.get(j).get("NAME");
					if(colList[i].equalsIgnoreCase(colName)) {
						stringRuleCheck[i] =  "Y";
						continue;
					}
				}
			}
			
			for(i=0;i<numericRuleCheck.length;i++){
				numericRuleCheck[i] =  "N";
				for(j=0;j<numericRules.size();j++){
					colName = (String) numericRules.get(j).get("NAME");
					if(colList[i].equalsIgnoreCase(colName)) {
						numericRuleCheck[i] =  "Y";
						continue;
					}
				}
			}
			
			for(i=0;i<dateRuleCheck.length;i++){
				dateRuleCheck[i] =  "N";
				for(j=0;j<dateRules.size();j++){
					colName = (String) dateRules.get(j).get("NAME");
					if(colList[i].equalsIgnoreCase(colName)) {
						dateRuleCheck[i] =  "Y";
						continue;
					}
				}
			}
			
			//String Rule apply
			while ((currentLine = br.readLine()) != null){
				i=0;
				j=0;
				bad_flag=0;
				validLengthFlag = true;
				validNullableFlag = true;
				validLovFlag = true;
				validPkFlag = true;
				validPrecisonFlag = true;
				validDateFormatFlag = true;
				validNumberFlag = true;
				validRangeFlag = true;
				dataList = currentLine.split(splitChar,-1);
				//System.out.println(delimiter);
				//System.out.println("data list length : " + dataList.length);
				
				for(i=0;i<dataList.length;i++){
					//System.out.println(" ColList Element : " + i + " : "+colList[i]);
					//System.out.println(" DataList Element : " + i + " : "+dataList[i]);
					if(doubleQuote.equalsIgnoreCase(String.valueOf(dataList[i].charAt(0))) && doubleQuote.equalsIgnoreCase(String.valueOf(dataList[i].charAt(dataList[i].length()-1)))){
						dataList[i]=dataList[i].substring(1,dataList[i].length()-1);
					}
				}
				
				System.out.println(doubleQuote.equalsIgnoreCase("\""));
				
				for(i=0;i<dataList.length;i++){
					if (bad_flag==1){
						break;
					}
					if(stringRuleCheck[i].equals("Y")){
						for(j=0;j<stringRules.size();j++){
							if (colList[i].equalsIgnoreCase((String) stringRules.get(j).get("NAME"))){
									validLengthFlag = objCheckUtils.checkStringLength(dataList[i],(String) stringRules.get(j).get("MIN_LENGTH"),(String) stringRules.get(j).get("MAX_LENGTH"));
									validNullableFlag = objCheckUtils.checkNull(dataList[i],(String) stringRules.get(j).get("NULLABLE"));
									validLovFlag = objCheckUtils.checkLov(dataList[i],(String) stringRules.get(j).get("ALLOWED_LOV"));
									//System.out.println("dataList[" +i +"] " + dataList[i] + "validLengthFlag: " + validLengthFlag + "validNullableFlag: " + validNullableFlag + "validLovFlag :" + validLovFlag);
									//System.out.println(" Inside if block ...col list " + colList[i] + "  data list : " + dataList[i]);
									if(validLengthFlag == true && validNullableFlag == true && validLovFlag == true){
										continue;
									}
									else if(validLengthFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Out of expected min and max bounds";
										break;
									}
									else if(validNullableFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Null value";
										break;
									}
									else if(validLovFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Unexpected value";
										break;
									}
						}
						
					}
				}
					else if(numericRuleCheck[i].equals("Y") && bad_flag != 1){
						for(j=0;j<numericRules.size();j++){
							if (colList[i].equalsIgnoreCase((String) numericRules.get(j).get("NAME"))){
									validNumberFlag = objCheckUtils.isNumeric(dataList[i]);
									if (validNumberFlag == true){
										validPrecisonFlag = objCheckUtils.numberPrecisionCheck(dataList[i],(String) numericRules.get(j).get("EXPECTED_PRECISION"),(String) numericRules.get(j).get("EXPECTED_SCALE"));
										validNullableFlag = objCheckUtils.checkNull(dataList[i],(String) numericRules.get(j).get("NULLABLE"));
										validLovFlag = objCheckUtils.checkLov(dataList[i],(String) numericRules.get(j).get("ALLOWED_LOV"));
										validRangeFlag = objCheckUtils.numberRangeCheck(dataList[i],(String) numericRules.get(j).get("RANGE"));
									}
									//System.out.println("dataList[" +i +"] " + dataList[i] + "validLengthFlag: " + validLengthFlag + "validNullableFlag: " + validNullableFlag + "validLovFlag :" + validLovFlag);
									//System.out.println(" Inside if block ...col list " + colList[i] + "  data list : " + dataList[i]);
									if(validNumberFlag == true && validPrecisonFlag == true && validNullableFlag == true && validLovFlag == true && validRangeFlag == true){
										continue;
									}
									else if(validNumberFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Not a number";
										break;
									}
									else if(validPrecisonFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Number not in specified precision/scale";
										break;
									}
									else if(validNullableFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Null value";
										break;
									}
									else if(validLovFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Unexpected value";
										break;
									}
									else if(validRangeFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Out of Range";
										break;
									}
						}
					}
					}
					else if(dateRuleCheck[i].equals("Y") && bad_flag != 1){
						for(j=0;j<dateRules.size();j++){
							if (colList[i].equalsIgnoreCase((String) dateRules.get(j).get("NAME"))){
									validDateFormatFlag	= objCheckUtils.dateFormatCheck(dataList[i],(String) dateRules.get(j).get("EXPECTED_FORMAT"));
									if (validDateFormatFlag == true){
										validNullableFlag = objCheckUtils.checkNull(dataList[i],(String) dateRules.get(j).get("NULLABLE"));
										validLovFlag = objCheckUtils.checkLov(dataList[i],(String) dateRules.get(j).get("ALLOWED_LOV"));
										validRangeFlag = objCheckUtils.dateRangeCheck(dataList[i],(String) dateRules.get(j).get("RANGE"),(String) dateRules.get(j).get("EXPECTED_FORMAT"));
									}
									//System.out.println("dataList[" +i +"] " + dataList[i] + "validLengthFlag: " + validLengthFlag + "validNullableFlag: " + validNullableFlag + "validLovFlag :" + validLovFlag);
									//System.out.println(" Inside if block ...col list " + colList[i] + "  data list : " + dataList[i]);
									if(validDateFormatFlag == true && validNullableFlag == true && validLovFlag == true && validRangeFlag == true){
										continue;
									}
									else if(validDateFormatFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Invalid date";
										break;
									}
									else if(validNullableFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Null value";
										break;
									}
									else if(validLovFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Unexpected value";
										break;
									}
									else if(validRangeFlag != true){
										bad_flag=1;
										errorMsg=colList[i] + " validation failed: Out of Range";
										break;
									}
						}
					}
					} 
			 }
			if(bad_flag == 1){
				currentLine=errorMsg+delimiter+currentLine;
				bw_badFile.write(currentLine);
				bw_badFile.newLine();
			}
			else {
				bw_goodFile.write(currentLine);
				bw_goodFile.newLine();
			}				
			}
			br.close();
			bw_goodFile.close();
			bw_badFile.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Error Occured while validating file. There is something wrong with data file");
			//System.out.println(ex.getMessage());	
		}
	}
}
