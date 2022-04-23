package junit;

import static org.junit.Assert.*;
import org.junit.Test;
import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;
import cobol.CobolParser;
import parse.Assembly;

// Test tostring method is working in the TokenAssembly class
public class TestToStringAssembly {
	@Test
	public void test() {
		Parser parser = CobolParser.start();
		Tokenizer token = CobolParser.tokenizer();
		//88 base_8 value 8.
		//88 base_16 value 16.
		token.setString("88 base_16 value 16.");
		 
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		System.out.println(out.toString());		
		assertTrue(out.toString().equals("[]88.0/base_16/value/16.0^"));
	}
}