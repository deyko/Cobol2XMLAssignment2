package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import cobol.Cobol;

public class TestCobol {

	@Test
	public void test() {	
		Cobol c = new Cobol();
		c.setYearDateWritten(1900);
		assertEquals(c.getYearDateWritten(),1900);
	}


}
