package junit;
import static org.junit.Assert.*;

import org.junit.Test;
import parse.Parser;
import parse.tokens.TokenAssembly;
import cobol.CobolParser;
import parse.Assembly;

import parse.tokens.Tokenizer;


public class TestLengthAssembly {

	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("88 base_8 value 8");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		
		assertTrue(in.length() == 4);
		assertTrue(out.length() == 4);
	}

}