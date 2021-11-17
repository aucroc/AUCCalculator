package org.srlearn.auc;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

public class ConfusionTest {

    @Test
    public void testConfusionFromPredictions1()
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

    @Test
    public void testConfusionFromPredictions2()
    {
        Confusion confusion = Confusion.fromPredictions(
                new double[]{0.75, 0.85, 0.6, 0.4, 0.0001},
                new int[]{0, 0, 0, 0, 1}
        );

        double aucpr = confusion.calculateAUCPR(0.0);
        double aucroc = confusion.calculateAUCROC();

        assertEquals(aucpr, 0.2);
        assertEquals(aucroc, 0.5);
    }

    @Test
    public void testConfusionFromPredictions3()
    {
        Confusion confusion = Confusion.fromPredictions(
                new double[]{0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505},
                new int[]{1, 1, 0, 1, 1, 1, 0, 0, 1, 0}
        );

        double aucpr = confusion.calculateAUCPR(0.5);
        double aucroc = confusion.calculateAUCROC();

        assertEquals(aucpr, 0.3729166666666667);
        assertEquals(aucroc, 0.75);
    }

    @Test
    public void testConfusionFromPredictions4()
    {
        Confusion confusion = Confusion.fromPredictions(
                new double[]{0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505},
                new int[]{1, 1, 0, 1, 1, 1, 0, 0, 1, 0}
        );

        double aucpr = confusion.calculateAUCPR(1.0);
        double aucroc = confusion.calculateAUCROC();

        assertEquals(aucpr, 0.0);
        assertEquals(aucroc, 0.75);
    }

    @Test
    public void testConfusionFromFile() throws FileNotFoundException {
        Confusion confusion = Confusion.fromFile("history/testsetlist.txt");
        double aucpr = confusion.calculateAUCPR(0.0);
        double aucroc = confusion.calculateAUCROC();
        assertEquals(aucpr, 0.8243055555555555);
        assertEquals(aucroc, 0.75);
    }

    @Test
    public void testMissingFile()
    {
        FileNotFoundException thrown = assertThrows(FileNotFoundException.class, () -> Confusion.fromFile("fileDoesNotExist.xyz"));
        assertTrue(thrown.getMessage().contains("File not found"));
    }
}
