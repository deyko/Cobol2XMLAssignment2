/*
 * @(#)XMLPayload.java	 0.1.0
 *
 * Copyright (c) 2019 Julian M. Bass
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */package xmlwriter;


import cobol.*;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.logging.Logger;
import org.w3c.dom.Attr;

public class XMLPayload {
	Document doc;
	Element rootElement;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public XMLPayload() {
		try {
		DocumentBuilderFactory dbFactory =
		         DocumentBuilderFactory.newInstance();
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // XML parsers should not be vulnerable to XXE attacks
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // XML parsers should not be vulnerable to XXE attacks
		DocumentBuilder dBuilder = 
		            dbFactory.newDocumentBuilder();
		doc = dBuilder.newDocument();
		
		 // root element
        rootElement = doc.createElement("cobol");
        doc.appendChild(rootElement);
		
		 } catch (Exception e) {
	         e.printStackTrace();
	     }
		
	}
	
	public void addElements(Cobol c) {
		/*
		* add string variable element
		*/
		String stringVariableName = c.getStringName();
		if (stringVariableName != null) {
			this.addStringVariableValueElement( c.getStringName(), c.getStringSize(), c.getStringValue());
			//System.out.println("Got Section");
			// Add contents of procedure division
		} else {
			//System.out.println("String Name null");
		}
		/*
		* add ConstantName element
		*/
		String constantName = c.getConstantName();
		if (constantName != null) {
			this.addConstantValueElement( constantName, 
			c.getConstantValue(), c.getLineNumber() );
			//System.out.println("Got Section");
			// Add contents of procedure division
		} else {
			//System.out.println("Comment Line null");
		} 
		
		/*
		* add commentLine element
		*/
		String commentLine = c.getCommentLine();
		if (commentLine != null) {
			this.addCommentLineElement( commentLine );
			//System.out.println("Got Section");
			// Add contents of procedure division
		} else {
			//System.out.println("Comment Line null");
		}

		
		/*
		 *  add sectionName element
		 */		
		String sectionName = c.getSectionName();
		if (sectionName != null) {
			this.addSectionElement( sectionName );
			
			// Add contents of procedure division
		} else {
			// Section Name null
		}
		
		/*
		 *  add divisionName element
		 */		
		String divisionName = c.getDivisionName();
		if (divisionName != null) {
			this.addDivisionElement( divisionName );
			// Got Division
			// Add contents of procedure division
		} else {
			// Division Name null
		}
		
		/*
		 *  add ProgramID element
		 */		
		String programIDName = c.getProgram_ID();
		if (programIDName != null) {
			this.addProgram_IDElement( programIDName );
			// Got ProgramID
			// Add contents of procedure division
		} else {
			// ProgramID null
		}
		
		/*
		 *  add DateWritten element
		 */	
		// DayDateWritten
		int dayDateWritten = c.getDayDateWritten();
		if(dayDateWritten != 0) {
			this.addDayDateWrittenElement( dayDateWritten );
		}
		
		//MonthDateWritten
		String monthDateWritten = c.getMonthDateWritten();
		if (monthDateWritten != null) {
			this.addMonthDateWrittenElement( monthDateWritten );
			// Got month
			// Add contents of procedure division
		} else {
			// month null
		}

		// YearDateWritten
		int yearDateWritten = c.getYearDateWritten();
		if(yearDateWritten != 0) {
			this.addYearDateWrittenElement( yearDateWritten );
		}
		
		/*
		* add display element
		*/
		String display = c.getDisplayText();
		if (display != null) {
		this.addDisplayElement(display);
		} else {
		}
	
		/*
		* add string variable element
		*/
		String StringVariableName = c.getDecimalName();
		if (StringVariableName != null) {
			this.addDecimalVariableValueElement( c.getDecimalName(), c.getDecimalAllNumbers());
		} else {
		}
		

	}
	
	void addDisplayElement(String displayString) {
		// Display command element
		if(displayString != null) {
			Element cobolname = doc.createElement("Display");
			cobolname.appendChild(doc.createTextNode(displayString));
			rootElement.appendChild(cobolname);
		}
	}
	
	void addDecimalVariableValueElement(String decimalVariableName, double decimalVariableWholeNumbers) {
		// Decimal Variable Declaration
		if(decimalVariableName != null) {
			Element cobolname = doc.createElement("Variable");
			
			// insert name of the variable into XML file
			Element nameID = doc.createElement("Variable");
			Attr attrType1 = doc.createAttribute("Name" );
			attrType1.setValue( decimalVariableName );
			nameID.setAttributeNode(attrType1);
			cobolname.appendChild(nameID);
			
			// insert how many whole numbers the variable can store into XML file
			Element sizeID = doc.createElement(decimalVariableName);
			Attr attrType2 = doc.createAttribute("MaxWholeNumbers" );
			attrType2.setValue( Double.toString(decimalVariableWholeNumbers) );
			sizeID.setAttributeNode(attrType2);
			cobolname.appendChild(sizeID);
			
			rootElement.appendChild(cobolname);
		}
	}
	void addStringVariableValueElement(String stringVariableName, double stringVariableSize, String stringVariableValue) {
		// String Variable Declaration
		if(stringVariableName != null) {
			Element cobolname = doc.createElement("Variable");
			
			// Insert name of the String Variable into XML file
			Element nameID = doc.createElement("Variable");
			Attr attrType1 = doc.createAttribute("Name" );
			attrType1.setValue(stringVariableName);
			nameID.setAttributeNode(attrType1);
			cobolname.appendChild(nameID);
			
			// Insert how many bytes the String Variable can store into the XML file
			Element sizeID = doc.createElement(stringVariableName);
			Attr attrType2 = doc.createAttribute("VariableByteSize" );
			attrType2.setValue(Double.toString(stringVariableSize));
			sizeID.setAttributeNode(attrType2);
			cobolname.appendChild(sizeID);
			
			// Insert the value of the String Variable into XML file
			Element valueID = doc.createElement("Variable");
			Attr attrType3 = doc.createAttribute("Value" );
			attrType3.setValue(stringVariableValue);
			valueID.setAttributeNode(attrType3);
			cobolname.appendChild(valueID);
			
			rootElement.appendChild(cobolname);
		}
	}

	 void addConstantValueElement(String constantName,  double constantValue, int lineNumber) {  
		 //  Program ID element
		 if(constantName != null) {  
			 Element cobolname = doc.createElement("Constant");    
			 
			 // insert name of constant into XML file   
			 Element constID = doc.createElement("Constant");   
			 Attr attrType2 = doc.createAttribute("Name" );   
			 attrType2.setValue( constantName );   
			 constID.setAttributeNode(attrType2);   
			 cobolname.appendChild(constID);       
			 
			 // insert line number of constant into XML file   
			 Element lineID = doc.createElement(constantName);   
			 Attr attrType = doc.createAttribute("Line_Number" );   
			 attrType.setValue( Integer.toString(lineNumber) );   
			 lineID.setAttributeNode(attrType);   
			 cobolname.appendChild(lineID);      
			 
			 // insert value of constant into XML file   
			 Element constantID = doc.createElement(constantName);   
			 Attr attrType1 = doc.createAttribute("Value" );   
			 attrType1.setValue( Double.toString(constantValue) );   
			 constantID.setAttributeNode(attrType1);   
			 cobolname.appendChild(constantID);       
			 rootElement.appendChild(cobolname); 
		 }
	 }

 	void addProgram_IDElement(String stringElement) {
		//  Program ID element
		
		if(stringElement != null) {
			Element cobolname = doc.createElement("Program-ID");
			cobolname.appendChild(doc.createTextNode(stringElement));
			rootElement.appendChild(cobolname);
		}
	}
 	
	void addCommentLineElement(String stringElement) {
		//  Comment Line element
		
		if(stringElement != null) {
			Element cobolname = doc.createElement("comment");
			cobolname.appendChild(doc.createTextNode(stringElement));
			rootElement.appendChild(cobolname);
		}
	}
 	
 	void addSectionElement(String stringElement) {
		//  Section element
		
		if(stringElement != null) {
			Element cobolname = doc.createElement("section");
			cobolname.appendChild(doc.createTextNode(stringElement));
			rootElement.appendChild(cobolname);
		}
	}
 	
 	void addDivisionElement(String stringElement) {
		//  Division element
		if(stringElement != null) {
			Element cobolname = doc.createElement("division");
			cobolname.appendChild(doc.createTextNode(stringElement));
			rootElement.appendChild(cobolname);
		}
	}
	
	void addDayDateWrittenElement(int intElement) {
		//  DayDateWritten element
		
		if(intElement != 0) {
			Element cobolname = doc.createElement("day-date-written");
			String s = "" + intElement;
			cobolname.appendChild(doc.createTextNode(s));
			rootElement.appendChild(cobolname);
		}
	}
 	
	void addMonthDateWrittenElement(String stringElement) {
		//  MonthWritten element
		
		if(stringElement != null) {
			Element cobolname = doc.createElement("month-date-written");
			cobolname.appendChild(doc.createTextNode(stringElement));
			rootElement.appendChild(cobolname);
		}
	}

	void addYearDateWrittenElement(int intElement) {
		//  YearDateWritten element
		
		if(intElement != 0) {
			Element cobolname = doc.createElement("year-date-written");
			String s = "" + intElement;
			cobolname.appendChild(doc.createTextNode(s));
			rootElement.appendChild(cobolname);
		}
	}
	
	
	public void writeFile(String filename) {
		try {
		// write the content into xml file
		// insert debug printf "WriteFile Filename: " + filename
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // XML parsers should not be vulnerable to XXE attacks
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // XML parsers should not be vulnerable to XXE attacks
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
        
        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
        
        //A character stream that collects its output in a string buffer, 
        //which can then be used to construct a string.
        StringWriter writer = new StringWriter();
        
        //transform document to string 
        transformer.transform(source, new StreamResult(writer));
        
        String xmlString = writer.getBuffer().toString();
        LOGGER.info(xmlString);
		 } catch (Exception e) {
	         e.printStackTrace();
	     }
	}

	public String returnXMLContents() {
		try {
        TransformerFactory transformerFactory =
        TransformerFactory.newInstance();
        Transformer transformer =
        transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        StreamResult result =
                new StreamResult(new File("test.xml"));
        transformer.transform(source, result);
        
        String output = "", input = "";
        BufferedReader in = new BufferedReader(new FileReader("test.xml"));
        while((input = in.readLine()) != null) {
        	output += input;
        }    
        return output;
		 } catch (Exception e) {
	         e.printStackTrace();
	     }
		return "erroooorrr";
	}
	

	

}
