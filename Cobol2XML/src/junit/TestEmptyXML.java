package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;
import cobol.Cobol;
import cobol.CobolParser;
import parse.Assembly;

import xmlwriter.XMLPayload;

public class TestEmptyXML {

	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		
		Cobol c = new Cobol();
		c = (Cobol) out.getTarget();
		XMLPayload xmlpayload = new XMLPayload();
		if(c != null) {
			xmlpayload.addElements(c);
		}
		String actual = xmlpayload.returnXMLContents();
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><cobol/>";

		System.out.println(actual);
		System.out.println(expected);

		assertTrue(actual.equals(expected));
	}

}