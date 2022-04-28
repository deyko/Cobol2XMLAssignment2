package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import cobol.Cobol;
import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;

public class TestDateAssembler {
	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("date-written.  12-dec-2002 - mb.");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);	
		Cobol c = new Cobol();
		c = (Cobol) out.getTarget();
				
		assertTrue(c.getDayDateWritten() == 12);
		assertTrue(c.getMonthDateWritten().equals("dec"));
		assertTrue(c.getYearDateWritten() == 2002);
	}

}