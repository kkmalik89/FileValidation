package com.im.architecture.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLPackager {
	public HashMap<String, String> fileMetadata(Document xmlDoc){
		NodeList fileInfoList;
		NodeList fileInfoContent;
		NodeList valContent;
		Element fileInfoElement;
		HashMap<String,String> fileInfo = new HashMap<String,String> ();
		
		fileInfoList = xmlDoc.getElementsByTagName("FILE_DEFINITION_RULES");
		fileInfoElement = (Element) fileInfoList.item(0);
		
		fileInfoContent = fileInfoElement.getElementsByTagName("FILE_TYPE");
		fileInfoElement = (Element) fileInfoContent.item(0);
		valContent = fileInfoElement.getChildNodes();
		fileInfo.put("FILE_TYPE",((Node)valContent.item(0)).getNodeValue().trim());
		
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("DELIMITER");
		fileInfoElement = (Element) fileInfoContent.item(0);
		valContent = fileInfoElement.getChildNodes();
		fileInfo.put("DELIMITER",((Node)valContent.item(0)).getNodeValue().trim());
				
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("FILE_HEADER_AVAILABLE");
		fileInfoElement = (Element) fileInfoContent.item(0);
		valContent = fileInfoElement.getChildNodes();
		fileInfo.put("FILE_HEADER_AVAILABLE",((Node)valContent.item(0)).getNodeValue().trim());
		
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("FILE_HEADER");
		System.out.println(fileInfoContent.getLength());
		fileInfoElement = (Element) fileInfoContent.item(0);
		System.out.println(fileInfoElement.getNodeName());
		valContent = fileInfoElement.getChildNodes();
		fileInfo.put("FILE_HEADER",((Node)valContent.item(0)).getNodeValue().trim());
		
		return fileInfo;
	}

		
	public List<HashMap> stringRulesMetadata(Document xmlDoc){
		NodeList fileInfoList;
		NodeList fileInfoContent;
		NodeList fileColContent;
		NodeList valContent;
		int numCols;
		Element fileInfoElement;
		Element fileColElement;
		String colName;
		String colDataType;
		String allowedValues;
		fileInfoList = xmlDoc.getElementsByTagName("FILE_DEFINITION_RULES");		
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("COLUMN");
		numCols = fileInfoContent.getLength();
		HashMap<String,String> stringRow ;
		List <HashMap> stringRules = new ArrayList<HashMap> ();
		
		for (int i =0; i < numCols;i++ ){
			fileInfoElement = (Element) fileInfoContent.item(i);	
			fileColContent = fileInfoElement.getElementsByTagName("NAME");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colName = "";
			}
			else
			{
				colName = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_DATA_TYPE");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colDataType = "";
			}
			else
			{
				colDataType = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			if(("STRING").equalsIgnoreCase(colDataType)){
				stringRow = new HashMap<String, String>();
				stringRow.put("NAME",colName);
				
				fileColContent = fileInfoElement.getElementsByTagName("MIN_LENGTH");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					stringRow.put("MIN_LENGTH",null);
				}
				else
				{
					stringRow.put("MIN_LENGTH",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("MAX_LENGTH");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					stringRow.put("MAX_LENGTH",null);
				}
				else
				{
					stringRow.put("MAX_LENGTH",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("NULLABLE");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					stringRow.put("NULLABLE",null);
				}
				else
				{
					stringRow.put("NULLABLE",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("ALLOWED_LOV");
				int numAllowed = fileColContent.getLength();
				//System.out.println("Num allowed : " + numAllowed);
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					stringRow.put("ALLOWED_LOV",null);
				}
				else
				{	
					allowedValues=null;
					fileColContent = fileInfoElement.getElementsByTagName("VALUE");
					for(int j=0;j<fileColContent.getLength();j++){
						fileColElement = (Element) fileColContent.item(j);
						valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							allowedValues += ","+ ((Node)valContent.item(0)).getNodeValue().trim();
						}
					}
					System.out.println("allowedValues : " + allowedValues);
					allowedValues = allowedValues.substring(allowedValues.indexOf(",")+1);
					System.out.println("allowedValues : " + allowedValues);
					stringRow.put("ALLOWED_LOV",allowedValues);
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("IS_PK");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					stringRow.put("IS_PK",null);
				}
				else
				{
					stringRow.put("IS_PK",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				stringRules.add(stringRow);				
			}
		}
		return stringRules;
	}

	public List<HashMap> numericRulesMetadata(Document xmlDoc){
		NodeList fileInfoList;
		NodeList fileInfoContent;
		NodeList fileColContent;
		NodeList valContent;
		int numCols;
		Element fileInfoElement;
		Element fileColElement;
		String colName;
		String colDataType;
		String allowedValues;
		fileInfoList = xmlDoc.getElementsByTagName("FILE_DEFINITION_RULES");		
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("COLUMN");
		numCols = fileInfoContent.getLength();
		HashMap<String,String> numericRow = new HashMap<String,String> ();
		List <HashMap> numericRules = new ArrayList<HashMap> ();
		
		for (int i =0; i < numCols;i++ ){
			fileInfoElement = (Element) fileInfoContent.item(i);	
			fileColContent = fileInfoElement.getElementsByTagName("NAME");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colName = "";
			}
			else
			{
				colName = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			////System.out.println(((Node)valContent.item(0)).getNodeValue().trim() + " Name : ");
			
			fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_DATA_TYPE");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colDataType = "";
			}
			else
			{
				colDataType = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			if(("NUMERIC").equalsIgnoreCase(colDataType)){
				numericRow = new HashMap<String, String>();
				numericRow.put("NAME",colName);
				
				fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_PRECISION");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("EXPECTED_PRECISION",null);
				}
				else
				{
					numericRow.put("EXPECTED_PRECISION",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_SCALE");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("EXPECTED_SCALE",null);
				}
				else
				{
					numericRow.put("EXPECTED_SCALE",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("NULLABLE");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("NULLABLE",null);
				}
				else
				{
					numericRow.put("NULLABLE",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("ALLOWED_LOV");
				int numAllowed = fileColContent.getLength();
				//System.out.println("Num allowed : " + numAllowed);
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("ALLOWED_LOV",null);
				}
				else
				{	
					allowedValues=null;
					fileColContent = fileInfoElement.getElementsByTagName("VALUE");
					for(int j=0;j<fileColContent.getLength();j++){
						fileColElement = (Element) fileColContent.item(j);
						valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							allowedValues += ","+ ((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
					}
					System.out.println("allowedValues : " + allowedValues);
					allowedValues = allowedValues.substring(allowedValues.indexOf(",")+1);
					numericRow.put("ALLOWED_LOV",allowedValues);
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("RANGE");
				numAllowed = fileColContent.getLength();
				System.out.println("Num allowed : " + numAllowed);
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("RANGE",null);
				}
				else
				{	
					String rangeValue="";
					fileColContent = fileInfoElement.getElementsByTagName("MIN_VALUE");
					fileColElement = (Element) fileColContent.item(0);
					valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							rangeValue = ((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
					
					fileColContent = fileInfoElement.getElementsByTagName("MAX_VALUE");
					fileColElement = (Element) fileColContent.item(0);
					valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							rangeValue += "," +((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
						else if(rangeValue != ""){
							rangeValue += ",";
						}
					System.out.println("rangeValue : " + rangeValue);
					numericRow.put("RANGE",rangeValue);
				}				
				
				fileColContent = fileInfoElement.getElementsByTagName("IS_PK");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					numericRow.put("IS_PK",null);
				}
				else
				{
					numericRow.put("IS_PK",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				numericRules.add(numericRow);				
			}
		}
		return numericRules;
	}

	public List<HashMap> dateRulesMetadata(Document xmlDoc){
		NodeList fileInfoList;
		NodeList fileInfoContent;
		NodeList fileColContent;
		NodeList valContent;
		int numCols;
		Element fileInfoElement;
		Element fileColElement;
		String colName;
		String colDataType;
		String allowedValues;
		fileInfoList = xmlDoc.getElementsByTagName("FILE_DEFINITION_RULES");		
		fileInfoElement = (Element) fileInfoList.item(0);
		fileInfoContent = fileInfoElement.getElementsByTagName("COLUMN");
		numCols = fileInfoContent.getLength();
		HashMap<String,String> dateRow = new HashMap<String,String> ();
		List <HashMap> dateRules = new ArrayList<HashMap> ();
		
		for (int i =0; i < numCols;i++ ){
			fileInfoElement = (Element) fileInfoContent.item(i);	
			fileColContent = fileInfoElement.getElementsByTagName("NAME");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colName = "";
			}
			else
			{
				colName = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			////System.out.println(((Node)valContent.item(0)).getNodeValue().trim() + " Name : ");
			
			fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_DATA_TYPE");
			fileColElement = (Element) fileColContent.item(0);
			valContent = fileColElement.getChildNodes();
			if(valContent.item(0) == null) {
				colDataType = "";
			}
			else
			{
				colDataType = ((Node)valContent.item(0)).getNodeValue().trim();
			}
			
			if(("DATE").equalsIgnoreCase(colDataType)){
				dateRow = new HashMap<String, String>();
				dateRow.put("NAME",colName);
				
				fileColContent = fileInfoElement.getElementsByTagName("EXPECTED_FORMAT");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					dateRow.put("EXPECTED_FORMAT",null);
				}
				else
				{
					dateRow.put("EXPECTED_FORMAT",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				
				fileColContent = fileInfoElement.getElementsByTagName("NULLABLE");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					dateRow.put("NULLABLE",null);
				}
				else
				{
					dateRow.put("NULLABLE",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("ALLOWED_LOV");
				int numAllowed = fileColContent.getLength();
				//System.out.println("Num allowed : " + numAllowed);
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					dateRow.put("ALLOWED_LOV",null);
				}
				else
				{	
					allowedValues="";
					fileColContent = fileInfoElement.getElementsByTagName("VALUE");
					for(int j=0;j<fileColContent.getLength();j++){
						fileColElement = (Element) fileColContent.item(j);
						valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							allowedValues += ","+ ((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
					}
					//System.out.println("allowedValues : " + allowedValues);
					allowedValues = allowedValues.substring(allowedValues.indexOf(",")+1);
					dateRow.put("ALLOWED_LOV",allowedValues);
				}
				
				fileColContent = fileInfoElement.getElementsByTagName("RANGE");
				numAllowed = fileColContent.getLength();
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					dateRow.put("RANGE",null);
				}
				else
				{	
					String rangeValue="";
					fileColContent = fileInfoElement.getElementsByTagName("MIN_VALUE");
					fileColElement = (Element) fileColContent.item(0);
					valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							rangeValue = ((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
					
					fileColContent = fileInfoElement.getElementsByTagName("MAX_VALUE");
					fileColElement = (Element) fileColContent.item(0);
					valContent = fileColElement.getChildNodes();
						if(valContent.item(0) != null) {
							rangeValue += "," +((Node)valContent.item(0)).getNodeValue().trim();
							//System.out.println(((Node)valContent.item(0)).getNodeValue().trim());
							//System.out.println(fileColElement.getNodeName() + fileColElement.getNodeValue());
						}
						else if(rangeValue != ""){
							rangeValue += ",";
						}
					//System.out.println("rangeValue : " + rangeValue);
					dateRow.put("RANGE",rangeValue);
				}				
				
				fileColContent = fileInfoElement.getElementsByTagName("IS_PK");
				fileColElement = (Element) fileColContent.item(0);
				valContent = fileColElement.getChildNodes();
				if(valContent.item(0) == null) {
					dateRow.put("IS_PK",null);
				}
				else
				{
					dateRow.put("IS_PK",((Node)valContent.item(0)).getNodeValue().trim());
				}
				
				dateRules.add(dateRow);				
			}
		}
		return dateRules;
	}
	public static List<String> keyColumnsInfo(List <HashMap> stringRules, List <HashMap> numericRules, List <HashMap> dateRules) {
		List <String> fileKeyColumns = new ArrayList<String>();
		for(int i=0;i<stringRules.size();i++){
			if(("Y").equalsIgnoreCase((String) stringRules.get(i).get("IS_PK"))){
				fileKeyColumns.add((String) stringRules.get(i).get("NAME"));
			}
		}
		
		for(int i=0;i<numericRules.size();i++){
			if(("Y").equalsIgnoreCase((String) numericRules.get(i).get("IS_PK"))){
				fileKeyColumns.add((String) numericRules.get(i).get("NAME"));
			}
		}
		
		for(int i=0;i<dateRules.size();i++){
			if(("Y").equalsIgnoreCase((String) dateRules.get(i).get("IS_PK"))){
				fileKeyColumns.add((String) dateRules.get(i).get("NAME"));
			}
		}
		return fileKeyColumns;
	}
}
