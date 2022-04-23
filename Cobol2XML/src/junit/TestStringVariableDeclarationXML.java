package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import cobol.Cobol;
import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;
import xmlwriter.XMLPayload;

public class TestStringVariableDeclarationXML {

	@Test
	public void test() {
		Tokenizer token = CobolParser.tokenizer();
		Parser parser = CobolParser.start();
		token.setString("01  hex_dec_data pic x(48) value \"10B11C12D13E14F15\"");
		Assembly in = new TokenAssembly(token);
		Assembly out = parser.bestMatch(in);
		
		Cobol c = new Cobol();
		c = (Cobol) out.getTarget();

		XMLPayload xmlp = new XMLPayload();
		if(c != null) {
			xmlp.addElements(c);
		}

		String actualOutput = xmlp.returnXMLContents();

		String expectedXMLOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><cobol>    <Variable>        <Variable Name=\"hex_dec_data\"/>        <hex_dec_data VariableByteSize=\"48.0\"/>        <Variable Value=\"10B11C12D13E14F15\"/>    </Variable></cobol>";

		//System.out.println(actualOutput);
		//System.out.println(expectedXMLOutput);

		assertTrue(actualOutput.equals(expectedXMLOutput));
	}

}