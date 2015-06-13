package com.im.architecture.utility;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckUtils {
	public boolean checkStringLength(String dataValue, String MIN_LENGTH, String MAX_LENGTH){
		int minLength;
		int maxLength;
		int flag;
		int stringLength = dataValue.length();
		if (dataValue.isEmpty()){
			return true;
		}
		if (MIN_LENGTH == null && MAX_LENGTH == null){
			flag = 0;
			return true;
		}
		else if (MIN_LENGTH == null) {
			flag = 1;
			maxLength = Integer.parseInt(MAX_LENGTH);
			if(stringLength <= maxLength){
				return true;
			}
			else
				return false;
		} 
		else if (MAX_LENGTH == null) {
			flag = 2;
			minLength = Integer.parseInt(MIN_LENGTH);
			if(stringLength >= minLength){
				return true;
			}
			else
				return false;
		}
		else {
			flag = 3;
			minLength = Integer.parseInt(MIN_LENGTH);
			maxLength = Integer.parseInt(MAX_LENGTH);
			if(stringLength >= minLength && stringLength <= maxLength){
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean checkNull(String dataValue,String expectedNull){
		if (("Y").equalsIgnoreCase(expectedNull)){
			return true;
		}
		else if (dataValue.isEmpty()){
			return false;
		}
		return true;		
	}
			
	public boolean checkLov(String dataValue, String allowedValues){
		if(allowedValues == null){
			return true;
		}
		String[] allowedValuesArray = allowedValues.split(",",-1);
		for (int i=0; i < allowedValuesArray.length; i++){
			if(dataValue.equalsIgnoreCase(allowedValuesArray[i])){
				return true;
			}
		}
		return false;
	}
	
	public boolean numberPrecisionCheck(String dataValue,String precision, String scale){
		int expPrecision;
		int expScale;
		//System.out.println( "dataValue  " + dataValue +  " precision " + precision +  " scale " + scale ); 
		if(dataValue.isEmpty() || (precision == null && scale == null)){
			return true;
		}
		else if (precision == null ) {
			expScale = Integer.parseInt(scale);
			if (expScale == dataValue.substring(dataValue.indexOf(".")+1).length()){
				return true;
			}
		}
		else if (scale == null){
			expPrecision = Integer.parseInt(precision);
			if (dataValue.contains(".")==true){
				if (expPrecision == (dataValue.length()-1)){
					return true;
				}
			}
			else {
				if (expPrecision == (dataValue.length())){
					return true;
			}
			}
		}
		else {
			expPrecision = Integer.parseInt(precision);
			expScale = Integer.parseInt(scale);
			if (dataValue.contains(".")==true){
				if (expScale == dataValue.substring(dataValue.indexOf(".")+1).length() && expPrecision == (dataValue.length()-1)){
					return true;
				}
			}
			else {
				if (expScale == dataValue.substring(dataValue.indexOf(".")+1).length() && expPrecision == (dataValue.length())){
					return true;
				}
			}
		}
		return false;	
	}	
	
	public boolean dateFormatCheck(String dataValue, String dateFormat){
		//System.out.println("Inside date check");
		if(dataValue.isEmpty()){
			return true;
		}
		if(dateFormat == null){
			return true;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dataValue);
		} 
		catch (ParseException e) {
			return false;
		}
		return true;
	}

	public boolean isNumeric(String dataValue)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(dataValue);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	public static boolean removeTempFile(String filePath,String tempfilePath){
		if (filePath.equalsIgnoreCase(tempfilePath)){
			return true;
		}
		else {
			boolean deleteStatus = false;
			File f = new File(tempfilePath);
			deleteStatus = f.delete();
			return deleteStatus;
		}
	}
	
	public boolean numberRangeCheck(String dataValue,String rangeValue){
		if (rangeValue.length() == 0){
			return true;
		}
		else {
			String[] range = rangeValue.split(",",-1);
		/*	System.out.println("range[0] : " + range[0]);
			System.out.println("range[1] : " + range[1]);
			System.out.println(range[0].length());
			*/
			if(range[0].length() > 0){
				if (Double.parseDouble(dataValue) < Double.parseDouble(range[0])){
					return false;
				}
			}
			if (range[1].length() > 0){
				if (Double.parseDouble(dataValue) > Double.parseDouble(range[1])){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean dateRangeCheck(String dataValue,String rangeValue, String expectedFormat){
		if (rangeValue.length() == 0 || dataValue.length() == 0){
			return true;
		}
		else {
			String[] range = rangeValue.split(",",-1);
			//System.out.println("range[0] : " + range[0]);
			//System.out.println("range[1] : " + range[1]);
			//System.out.println(range[0].length());
			if(range[0].length() > 0){
				if (CheckUtils.dateFromString(dataValue,expectedFormat).compareTo(CheckUtils.dateFromString(range[0],expectedFormat)) < 0){
					return false;
				}
			}	
			if (range[1].length() > 0){
				if (CheckUtils.dateFromString(dataValue,expectedFormat).compareTo(CheckUtils.dateFromString(range[1],expectedFormat)) > 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public static Date dateFromString(String dataValue, String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		//System.out.println(" Data Value : " + dataValue);
		//System.out.println(" Date Format : " + dateFormat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dataValue);
			return date;
		} 
		catch (ParseException e) {
			throw new RuntimeException("Unable to convert string to date");
		}
	}
}
