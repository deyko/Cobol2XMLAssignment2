package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import cobol.Cobol;
import cobol.CobolParser;
import parse.Assembly;
import parse.Parser;
import parse.tokens.TokenAssembly;
import parse.tokens.Tokenizer;

// This test checks whether COBOL display commands can be assembled correctly
// by checking whether the constant Cobol class variables have been set correctly.
public class TestDisplayAssembler {
	@Test
	public void test() {
		Tokenizer t = CobolParser.tokenizer();
		Parser p = CobolParser.start();
		t.setString("display \"Base: \" current_base \" value: \" entry_char");
		Assembly in = new TokenAssembly(t);
		Assembly out = p.bestMatch(in);
		
		Cobol c = new Cobol();
		c = (Cobol) out.getTarget();
		System.out.println(c.getDisplayText());
		assertTrue(c.getDisplayText().equals("\"Base: \" current_base \" value: \" entry_char"));
	}
}
