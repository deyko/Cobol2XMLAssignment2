package cobol;
import java.util.Stack;

import parse.*;
import parse.tokens.*;

public class DisplayAssembler extends Assembler {
	/**
	* This Display Assembler removes the COBOL display syntax and takes the text to display so it is ready to
	* be displayed in XML.
	*/
	public void workOn(Assembly a) {
		Cobol c = new Cobol();	
		Stack assemblyStack = a.getStack();
		Stack reverStack = new Stack();					
		while (assemblyStack.empty() == false) {
			Token token = (Token) assemblyStack.pop();
			reverStack.push(token);
		}	
		String displayString = "";
		
		while (reverStack.empty() == false) {
			Token poppedToken = (Token) reverStack.pop();
			displayString += " " + poppedToken.sval();
		}
		if (displayString.length() > 0) {
			displayString = displayString.substring(1, displayString.length());	
			c.setDisplayText(displayString);
			a.setTarget(c);
		}
	}
}