package cobol;
import parse.*;
import parse.tokens.*;

public class VariableAssembler extends Assembler {
	// VariableAssembler class is assigned with removing Cobol syntax from the variable names and converting it into XML to display it.
	public void workOn(Assembly assembly) {
		Cobol c = new Cobol();
		assembly.pop();

		Token token = (Token) assembly.pop();
		Double wholeNumbers = token.nval();
		c.setDecimalAllNumbers(wholeNumbers);
		assembly.pop();
		token = (Token) assembly.pop();
		assembly.pop();
		token = (Token) assembly.pop();
		c.setDecimalName(token.sval().trim());
		assembly.pop();
		assembly.setTarget(c);
	}
}