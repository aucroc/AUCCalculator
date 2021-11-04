package com.goadrich.mark.auc;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConfusionTest extends TestCase {

    public ConfusionTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(ConfusionTest.class);
    }

    public void testConfusionFromPredictions()
    {
        Confusion confusion = Confusion.fromPredictions(
                new double[]{0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505},
                new int[]{1, 1, 0, 1, 1, 1, 0, 0, 1, 0}
        );

        double aucpr = confusion.calculateAUCPR(0.0);
        double aucroc = confusion.calculateAUCROC();

        assertEquals(aucpr, 0.8243055555555555);
        assertEquals(aucroc, 0.75);
    }
}
