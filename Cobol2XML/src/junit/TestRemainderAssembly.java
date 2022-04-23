package junit;

import static org.junit.Assert.*;
import org.junit.Test;
import parse.Parser;
import parse.tokens.TokenAssembly;
import cobol.CobolParser;
import parse.Assembly;
import parse.tokens.Tokenizer;

public class TestRemainderAssembly {
	@Test
	public void test() {
		Parser parser = CobolParser.start();
		Tokenizer token = CobolParser.tokenizer();
		token.setString("program-id. JB-base example");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		
		assertTrue(out.remainder("/").equals("example"));
	}
}