package junit;

import static org.junit.Assert.*;
import org.junit.Test;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;
import cobol.Cobol;
import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;

public class TestStringAssembler {
	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("01  hex_dec_data pic x(48) value \"0000000\"");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		
		Cobol c = new Cobol();
		c = (Cobol) out.getTarget();
		//assertFalse
		assertTrue(c.getStringName().equals("hex_dec_data"));
		assertTrue(c.getStringSize().equals(48.0));
		assertTrue(c.getStringValue().equals("0000000"));
		
	}

}