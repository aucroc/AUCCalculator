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
    public void testConfusionFromPredictions5()
    {

        // This test intentionally requires interpolation.

        Confusion confusion = Confusion.fromPredictions(
                new double[]{0.0, 1.0, 0.33115424372672403, 0.0, 0.0, 0.5691536821990291, 1.0, 1.0, 1.0, 0.11699333504730192, 0.2089028813491356, 1.0, 1.0, 1.0, 1.0, 0.09224075477745197, 0.2427913326784128, 0.29792593787305666, 0.8143560028148764, 0.32689913699395173},
                new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        );

        double aucpr = confusion.calculateAUCPR(0.0);
        double aucroc = confusion.calculateAUCROC();

        assertEquals(aucpr, 0.47877105028614314);
        assertEquals(aucroc, 0.42999999999999994);
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
