package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import parse.Parser;
import parse.tokens.Literal;

public class TestLiteral {

	@Test
	public void testLiteral() {
		String chain = "Hola";
		Literal l = new Literal(chain);
		ArrayList<Parser> list = new ArrayList<Parser>(); 
		assertEquals(chain,l.unvisitedString(list));

	}
}
