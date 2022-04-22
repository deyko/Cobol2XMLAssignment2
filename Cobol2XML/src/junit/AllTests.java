package junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NumTest.class, ParserTest.class, SymbolTest.class, TestCobol.class, TestLiteral.class,
		TokenTest.class })
public class AllTests {

}
