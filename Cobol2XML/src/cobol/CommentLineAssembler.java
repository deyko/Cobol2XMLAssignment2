package cobol;
import java.util.Stack;

import parse.*;
import parse.tokens.*;

public class CommentLineAssembler extends Assembler {
	/**
	* Pop a string, and set the target DataDivision to this
	* string.
	*
	* @param Assembly the assembly to work on
	*/
	public void workOn(Assembly a) {
		//System.out.println("commentLineAssembler");		
		Cobol c = new Cobol();		
		Stack stackAssembly = a.getStack();		
		String stringComment = "";		
		// si no esta vacio, haz esto:
		while (!(stackAssembly.empty())) {
			Token popToken = (Token) stackAssembly.pop();	
			
			if (popToken.isWord() || popToken.isSymbol()) {
				stringComment += " " + popToken.sval();
			}
			else if (popToken.isNumber()) {
				stringComment += " " + popToken.nval();
			}
		}				
		if (stringComment.length() > 0) {
			String stringArray[] = stringComment.split(" "), unreverComment = ""; 
		    for (int i = stringArray.length - 1; i >=0; i--) { 
		    	unreverComment += stringArray[i] + " ";
		    }
		    
		    // Remove the two extra spaces at the end of the reversed string.
		    unreverComment = unreverComment.substring(0, unreverComment.length() - 2);
			
			c.setCommentLine(unreverComment);
			a.setTarget(c);
		}
	}
}
