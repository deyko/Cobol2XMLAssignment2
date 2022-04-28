package junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NumTest.class, ParserTest.class, SymbolTest.class, TestCobol.class, TestDateAssembler.class,
		TestDefDelimiterAssembly.class, TestDisplayAssembler.class, TestEmptyXML.class, TestLengthAssembly.class,
		TestLiteral.class, TestPopTokenAssembly.class, TestRemainderAssembly.class, TestStringAssembler.class,
		TestStringVariableDeclarationXML.class, TestTokenNVal.class, TestToStringAssembly.class, TokenTest.class })
public class AllTests {

}
