package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import parse.Parser;
import parse.tokens.Literal;
import parse.tokens.Token;

import org.junit.Test;

public class TokenTest {
	@Test
	public void test() {
		//char c = 'a';
		int i = 2;
		Token t = new Token(i);
		assertTrue(t.isNumber());
		assertFalse(t.isWord());
		assertFalse(t.isSymbol());

	}


}
