package com.im.architecture.businessdelegate;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.*;

import com.im.architecture.utility.CheckUtils;
import com.im.architecture.utility.LocationException;
import com.im.architecture.utility.XMLPackager;
import com.im.architecture.utility.XMLUtils;
import com.im.architecture.utility.XMLValidator;


/* The program will expect 4 parameters :-
 * XML Rule : Location of the rule set.
 * File Path :- Path of the file
 * Good File Path : Location and name of the file having good data 
 * Bad File Path : Location and name of the file having erroneous data
 */

public class FileValidator {
	
	public static void main(String[] args) {
	
		String filePath="C:/Users/kamalik/workspace/FileHandling/Demo/SampleFile.dat";
		String goodFilePath="C:/Users/kamalik/workspace/FileHandling/Demo/goodFile.dat";
		String badFilePath="C:/Users/kamalik/workspace/FileHandling/Demo/badFile.dat";
		String uniqueFilePath="C:/Users/kamalik/workspace/FileHandling/Demo/uniqueFile.dat";
		String xsdPath="C:/Users/kamalik/workspace/FileHandling/Rules_XSD.xsd";
		String xmlPath="C:/Users/kamalik/workspace/FileHandling/Demo/Rules.xml";
		
		try {
			
			LocationException objLocationException = new LocationException();
			objLocationException.fileNotExistsException("Data File", filePath);
			objLocationException.fileNotExistsException("Rules File", xmlPath);
			objLocationException.DirExistsException("Good File",goodFilePath);
			objLocationException.DirExistsException("Bad File",badFilePath);
			
			XMLValidator objXMLValidator = new XMLValidator();
			objXMLValidator.validateXMLSchema(xsdPath, xmlPath);
			
			XMLUtils objXMLUtils= new XMLUtils();
			Document xmlDoc =  objXMLUtils.getDocument(xmlPath);
			
			XMLPackager objXMLPackager = new XMLPackager();
			
			HashMap<String,String> fileInfo = objXMLPackager.fileMetadata(xmlDoc);
				
			List <HashMap> stringRules =  objXMLPackager.stringRulesMetadata(xmlDoc);
			
			List <HashMap> numericRules = objXMLPackager.numericRulesMetadata(xmlDoc);
			
			List <HashMap> dateRules = objXMLPackager.dateRulesMetadata(xmlDoc);
			
			List <String> keyColumns = objXMLPackager.keyColumnsInfo(stringRules,numericRules,dateRules);
			
			DuplicateCheck objDuplicateCheck = new DuplicateCheck(); 
			
			String tempfilePath = objDuplicateCheck.keyColumnDuplicateCheck(keyColumns,fileInfo,filePath,uniqueFilePath,badFilePath);
			
			RecordCheck objRecordCheck = new RecordCheck();
			objRecordCheck.checkColumnLevelRule(fileInfo,stringRules,numericRules,dateRules,tempfilePath,goodFilePath,badFilePath);
			
			CheckUtils.removeTempFile(filePath,tempfilePath);	
			System.out.println("File Validation completed successfully");
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}