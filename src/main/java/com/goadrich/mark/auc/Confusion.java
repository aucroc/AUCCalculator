package com.goadrich.mark.auc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Confusion object for generating AUCROC/AUCPR.
 */
public class Confusion extends Vector<PNPoint>
{
  private final double totPos;
  private final double totNeg;

  public Confusion(double totalPositives, double totalNegatives)
  {
     if (totalPositives < 1.0D || totalNegatives < 1.0D) {
       this.totPos = 1.0D;
       this.totNeg = 1.0D;
       System.err.println("ERROR: " + totalPositives + "," + totalNegatives + " - " + "Defaulting Confusion to 1,1");
    } else {
       this.totPos = totalPositives;
       this.totNeg = totalNegatives;
    }
  }

  /**
   * Create a Confusion instance from <code>y_prob_pred</code> and
   * <code>y_true</code> arrays.
   *
   * @param y_prob_pred Predicted probability of example being true.
   * @param y_true True label for example.
   * @return A Confusion instance
   *
   * <p>
   * <b>Examples</b>
   *
   *<pre>{@code
   *Confusion confusion = Confusion.fromArrays(
   *   new double[] {0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505},
   *   new int[] {1, 1, 0, 1, 1, 1, 0, 0, 1, 0}
   *);
   *
   *double aucpr = confusion.calculateAUCPR(0.0);
   *double aucroc = confusion.calculateAUCROC();
   *}</pre>
   */
  public static Confusion fromPredictions(double[] y_prob_pred, int[] y_true)
  {

    if (y_prob_pred.length != y_true.length) {
      throw new RuntimeException("Arrays must have the same length");
    }

    ClassSort[] arrayOfClassSort = new ClassSort[y_prob_pred.length];
    for (int i = 0; i < y_prob_pred.length; i++) {
      arrayOfClassSort[i] = new ClassSort(y_prob_pred[i], y_true[i]);
    }

    return classSortToConfusion(arrayOfClassSort);
  }

  /**
   * Create a <code>Confusion</code> instance from a delimited file of
   * probabilities and true labels.
   *
   * @param fileName Path to read file from.
   * @return A Confusion instance
   *
   * <p>
   * <b>Examples</b>
   */
  public static Confusion fromFile(String fileName) throws FileNotFoundException {

    LinkedList<ClassSort> linkedList = new LinkedList<>();

    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

      while (bufferedReader.ready()) {
        String line = bufferedReader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line, "\t ,");

        try {
          double probability = Double.parseDouble(tokenizer.nextToken());
          int label = Integer.parseInt(tokenizer.nextToken());
          linkedList.add(new ClassSort(probability, label));
        } catch (NumberFormatException numberFormatException) {
          System.err.println("...skipping bad input line (bad numbers)");
        } catch (NoSuchElementException noSuchElementException) {
          System.err.println("...skipping bad input line (missing data)");
        }
      }
    } catch (FileNotFoundException fileNotFoundException) {
      System.err.println("ERROR: File " + fileName + " not found - exiting...");
      throw new FileNotFoundException("File not found.");
    } catch (IOException iOException) {
      System.err.println("ERROR: IO Exception in file " + fileName + " - exiting...");
      System.exit(-1);
    }

    ClassSort[] arrayOfClassSort = linkedList.toArray(new ClassSort[0]);
    return classSortToConfusion(arrayOfClassSort);
  }

  private void addPoint(PNPoint pnpoint)
  {
    if (!contains(pnpoint)) {
      add(pnpoint);
    }
  }

  private void sort()
  {
    if (size() == 0) {
      System.err.println("ERROR: No data to sort....");
      return;
    }

    PNPoint[] arrayOfPNPoint = new PNPoint[size()];
    int b1 = 0;
    while (size() > 0) {
      arrayOfPNPoint[b1++] = elementAt(0);
      removeElementAt(0);
    }

    Arrays.sort(arrayOfPNPoint);
    this.addAll(Arrays.asList(arrayOfPNPoint));

    PNPoint pNPoint1 = elementAt(0);
    while (pNPoint1.pos < 0.001D && pNPoint1.pos > -0.001D) {
      removeElementAt(0);
      pNPoint1 = elementAt(0);
    }

    double d = pNPoint1.neg / pNPoint1.pos;

    PNPoint pNPoint2 = new PNPoint(1.0D, d);
    if (!contains(pNPoint2) && pNPoint1.pos > 1.0D) {
      insertElementAt(pNPoint2, 0);
    }

    addPoint(new PNPoint(this.totPos, this.totNeg));
  }

  private void interpolate()
  {
    if (size() == 0) {
      System.err.println("ERROR: No data to interpolate....");
      return;
    }

    for (int b = 0; b < size() - 1; b++) {
      PNPoint pNPoint1 = elementAt(b);
      PNPoint pNPoint2 = elementAt(b + 1);

      double d1 = pNPoint2.pos - pNPoint1.pos;
      double d2 = pNPoint2.neg - pNPoint1.neg;
      double d3 = d2 / d1;
      double d4 = pNPoint1.pos;
      double d5 = pNPoint1.neg;

      while (Math.abs(pNPoint1.pos - pNPoint2.pos) > 1.001D) {
        // TODO(hayesall): When is it possible for this to occur? Highly imbalanced data?
        double d = d5 + (pNPoint1.pos - d4 + 1.0D) * d3;
        PNPoint pNPoint = new PNPoint(pNPoint1.pos + 1.0D, d);
        insertElementAt(pNPoint, ++b);
        pNPoint1 = pNPoint;
      }
    }
  }

  /**
   * Calculate the area under the precision-recall curve.
   *
   * @param minRecall Minimum recall required.
   * @return AUCPR value.
   */
  public double calculateAUCPR(double minRecall)
  {
    if (minRecall < 0.0D || minRecall > 1.0D) {
      System.err.println("ERROR: invalid minRecall, must be between 0 and 1 - returning 0");
      return 0.0D;
    }

    if (size() == 0) {
      System.err.println("ERROR: No data to calculate....");
      return 0.0D;
    }

    double d1 = minRecall * this.totPos;
    int b = 0;
    PNPoint pNPoint1 = elementAt(b);
    PNPoint pNPoint2 = null;

    try {
      while (pNPoint1.pos < d1) {
        pNPoint2 = pNPoint1;
        pNPoint1 = elementAt(++b);
      }
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      System.out.println("ERROR: minRecall out of bounds - exiting...");
      System.exit(-1);
    }

    double d2 = (pNPoint1.pos - d1) / this.totPos;
    double d3 = pNPoint1.pos / (pNPoint1.pos + pNPoint1.neg);
    double d4 = d2 * d3;

    if (pNPoint2 != null) {
      // TODO(hayesall): This only seems to occur in a fairly narrow range of minRecall values.
      double d5 = pNPoint1.pos / this.totPos - pNPoint2.pos / this.totPos;
      double d10 = pNPoint2.pos / (pNPoint2.pos + pNPoint2.neg);
      double d6 = d3 - d10;
      double d7 = d6 / d5;
      double d8 = d10 + d7 * (d1 - pNPoint2.pos) / this.totPos;
      double d9 = 0.5D * d2 * (d8 - d3);
      d4 += d9;
    }

    d2 = pNPoint1.pos / this.totPos;
    for (int i = b + 1; i < size(); i++) {
      PNPoint pNPoint = elementAt(i);
      double d5 = pNPoint.pos / this.totPos;
      double d6 = pNPoint.pos / (pNPoint.pos + pNPoint.neg);
      double d7 = (d5 - d2) * d6;
      double d8 = 0.5D * (d5 - d2) * (d3 - d6);
      d4 += d7 + d8;
      d2 = d5;
      d3 = d6;
    }
    return d4;
  }

  /**
   * Calculate the area under the ROC curve.
   * @return AUCROC value.
   */
  public double calculateAUCROC()
  {
    if (size() == 0) {
      System.err.println("ERROR: No data to calculate....");
      return 0.0D;
    }
    PNPoint pNPoint = elementAt(0);
    double d1 = pNPoint.pos / this.totPos;
    double d2 = pNPoint.neg / this.totNeg;
    double d3 = 0.5D * d1 * d2;

    for (int i = 1; i < size(); i++) {
      PNPoint pNPoint1 = elementAt(i);
      double d4 = pNPoint1.pos / this.totPos;
      double d5 = pNPoint1.neg / this.totNeg;
      double d6 = (d4 - d1) * d5;
      double d7 = 0.5D * (d4 - d1) * (d5 - d2);

      d3 += d6 - d7;
      d1 = d4;
      d2 = d5;
    }
    d3 = 1.0D - d3;
    return d3;
  }

  private static Confusion classSortToConfusion(ClassSort[] arrayOfClassSort) {
    Arrays.sort(arrayOfClassSort);

    int b1 = 0;
    int b2 = 0;

    if (arrayOfClassSort[arrayOfClassSort.length - 1].classification == 1) {
      b1++;
    } else {
      b2++;
    }

    ArrayList<PNPoint> arrayList = new ArrayList<>();
    double d = arrayOfClassSort[arrayOfClassSort.length - 1].probability;

    for (int i = arrayOfClassSort.length - 2; i >= 0; i--) {

      double probability = arrayOfClassSort[i].probability;
      int classification = arrayOfClassSort[i].classification;

      System.out.println(probability + " " + classification);

      if (probability != d) {
        arrayList.add(new PNPoint(b1, b2));
      }
      d = probability;

      if (classification == 1) {
        b1++;
      } else {
        b2++;
      }
    }

    arrayList.add(new PNPoint(b1, b2));

    Confusion confusion = new Confusion(b1, b2);
    for (PNPoint pNPoint : arrayList) {
      confusion.addPoint(pNPoint);
    }

    confusion.sort();
    confusion.interpolate();
    return confusion;
  }

  public String toString()
  {
    StringBuilder str = new StringBuilder("TotalPos: " + this.totPos + ", TotalNeg: " + this.totNeg + "\n");
    for (int i = 0; i < size(); i++) {
      str.append(elementAt(i)).append("\n");
    }
    return str.toString();
  }
}
