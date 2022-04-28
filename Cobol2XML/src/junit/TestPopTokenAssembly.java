package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;
import parse.tokens.Token;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;

// This test checks whether the pop method of the TokenAssembly class is working.
public class TestPopTokenAssembly{

	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("");
		Assembly in = new TokenAssembly(token);		
		in.push(new Token("wordToken"));		
		Token poppedToken = (Token) in.pop();		
		assertTrue(poppedToken.sval().equals("wordToken"));
	}

}