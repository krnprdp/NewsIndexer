/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Pradeep
 *
 */
public class StopWordsRuleTest extends TFRuleBaseTest{

	
	@Test
	public void testRule() {
		try {
			assertArrayEquals(new String[]{"test"}, 
					runTest(TokenFilterType.STOPWORD, "this is a test"));
			assertArrayEquals(new String[]{}, 
					runTest(TokenFilterType.STOPWORD, "do not do this"));
			assertArrayEquals(new String[]{"ace", "spades"}, 
					runTest(TokenFilterType.STOPWORD, "ace of spades"));
			assertArrayEquals(new String[]{"valid", "sentence"}, 
					runTest(TokenFilterType.STOPWORD, "valid sentence"));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}
}
