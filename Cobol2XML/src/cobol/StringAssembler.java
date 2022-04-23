package cobol;
import java.util.ArrayList;
import parse.tokens.*;
import parse.*;

//StringAssembler functionality: COBOL comment syntax is removed and gets the variable size, name and value to display it later in XML source file.
public class StringAssembler extends Assembler {

	public void workOn(Assembly a) {
		Cobol c = new Cobol();
		
		// Value of string variable
		Token token = (Token) a.pop();
		// Remove '&quot;' characters
		String quotedString = token.sval();
		quotedString = quotedString.replace("\"", "");		
		c.setStringValue(quotedString);			
		a.pop();
		a.pop();		
		token = (Token) a.pop();
		c.setStringSize(token.nval());
		a.pop();
		a.pop();
		a.pop();
		token = (Token) a.pop();
		c.setStringName(token.sval());
		a.pop();
		a.setTarget(c);
	}
}