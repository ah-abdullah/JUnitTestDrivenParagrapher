package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import view_controller.Calculator;

@RunWith(Parameterized.class)
public class TestCalc {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private String calcInput;

    private String calcExpected;

    public TestCalc(String input, String expected) {
        this.calcInput = input;
        this.calcExpected = expected;
    }
    
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { "1234567890=", 	"1234567890.0000" }, // digit input test
                 { "3+4=", 			"7.0000" }, 		// ADD test
                 { "15-5=", 		"10.0000" },		// SUBTRACT test
                 { "5-15=", 		"-10.0000" },		// SUBTRACT test
                 { "33*10=",		"330.0000" },		// MULTIPLY test
                 { "42/20=",		"2.1000" }, 		// DIVIDE test fails, bug detected in division, fix division logic in Model.java, re-test which then succeeds 
                 { "3+4*5=", 		"23.0000" },		// precedence test
                 { "(3+4)*5=", 		"35.0000" },		// LEFT_PAR precedence test
                 { "c", 			"0.0000" },			// CLEAR test
                 { "1.2439+4.7891=","6.0330" },			// POINT test
                 { "75n=", 			"-75.0000" },		// NEGATE test
                 { "(-75)n=", 		"75.0000" },		// NEGATE test
                 { "20/40=",		"0.5000" },			// DIVIDE test
                 { "20/0=",			"+ oo" },			// DIVIDE by 0 test
                 { "(-20)/0=",		"- oo" }			// DIVIDE by 0 test fails, bug detected in calculating negative infinity, fix logic in Model.java, re-test which then succeeds
           });
    }
    
	@Test
	public void test() {
		Calculator calc = Calculator.startUp();
		for (int i = 0; i < calcInput.length(); ++i) {
			calc.clickButton(calcInput.substring(i, i+1));
		}
		String calcActual = calc.getValue();
		assertEquals(calcExpected, calcActual);
	}
}
