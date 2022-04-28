package junit;

import static org.junit.Assert.*;
import org.junit.Test;

import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;

// This test checks whether the default delimiter method of the TokenAssembly class is working.
public class TestDefDelimiterAssembly {

	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("string");
		Assembly in = new TokenAssembly(token);
		String s = "/";
		assertTrue(in.defaultDelimiter() == s);
	}

}