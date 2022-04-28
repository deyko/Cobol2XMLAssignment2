package junit;

import static org.junit.Assert.*;
import parse.tokens.*;
import org.junit.Test;
import java.util.ArrayList;

public class TestTokenNVal {

	@Test
	public void test() {
		Token token = new Token(6);		
		assertTrue(token.nval() == 6);
	}

}
