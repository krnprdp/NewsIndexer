package edu.buffalo.cse.irf14;

import edu.buffalo.cse.irf14.analysis.AnalysisSuite;
import edu.buffalo.cse.irf14.document.DocumentSuite;
import edu.buffalo.cse.irf14.index.IndexerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({AnalysisSuite.class, DocumentSuite.class, IndexerSuite.class})
public class AllTests {

}
