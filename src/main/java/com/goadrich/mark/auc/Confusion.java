package com.goadrich.mark.auc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Confusion extends Vector<PNPoint>
{
  private final double totPos;
  private final double totNeg;

  public Confusion(double paramDouble1, double paramDouble2)
  {
     if (paramDouble1 < 1.0D || paramDouble2 < 1.0D) {
       this.totPos = 1.0D;
       this.totNeg = 1.0D;
       System.err.println("ERROR: " + paramDouble1 + "," + paramDouble2 + " - " + "Defaulting Confusion to 1,1");
    } else {
       this.totPos = paramDouble1;
       this.totNeg = paramDouble2;
    }
  }

  /**
   * Return a Confusion instance from `y_prob_pred` and `y_true` arrays.
   *
   * @param y_prob_pred Predicted probability of example being true.
   * @param y_true True label for example.
   * @return A Confusion instance
   *
   * Examples
   * --------
   *
   * ```java
   * Confusion confusion = Confusion.fromArrays(
   *    new double[] {0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505},
   *    new int[] {1, 1, 0, 1, 1, 1, 0, 0, 1, 0}
   * };
   *
   * double aucpr = confusion.calculateAUCPR(0.0);
   * double aucroc = confusion.calculateAUCROC();
   * ```
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
    Arrays.sort(arrayOfClassSort);

    // TODO(hayesall): This is copied from the `ReadList.readFile` method.
    ArrayList<PNPoint> arrayList = new ArrayList<>();

    double d = arrayOfClassSort[arrayOfClassSort.length - 1].probability;

    int b1 = 0;
    int b2 = 0;

    if (arrayOfClassSort[arrayOfClassSort.length - 1].classification == 1) {
      b1++;
    } else {
      b2++;
    }

    for (int i = arrayOfClassSort.length - 2; i >= 0; i--) {
      double d1 = arrayOfClassSort[i].probability;
      int j = arrayOfClassSort[i].classification;
      System.out.println(d1 + " " + j);
      if (d1 != d) {
        arrayList.add(new PNPoint(b1, b2));
      }
      d = d1;
      if (j == 1) {
        b1++;
      } else {
        b2++;
      }
    }
    arrayList.add(new PNPoint(b1, b2));
    Confusion confusion = new Confusion(b1, b2);
    for (PNPoint pNPoint : arrayList) {
      confusion.addPoint(pNPoint.pos, pNPoint.neg);
    }

    confusion.sort();
    confusion.interpolate();
    return confusion;
  }

  public void addPoint(double paramDouble1, double paramDouble2) throws NumberFormatException
  {
     if (paramDouble1 < 0.0D || paramDouble1 > this.totPos || paramDouble2 < 0.0D || paramDouble2 > this.totNeg) {
       throw new NumberFormatException();
     }

     PNPoint pNPoint = new PNPoint(paramDouble1, paramDouble2);
     if (!contains(pNPoint)) {
       add(pNPoint);
    }
  }

  public void sort()
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

    pNPoint2 = new PNPoint(this.totPos, this.totNeg);
    if (!contains(pNPoint2)) {
      add(pNPoint2);
    }
  }

  public void interpolate()
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
        double d = d5 + (pNPoint1.pos - d4 + 1.0D) * d3;
        PNPoint pNPoint = new PNPoint(pNPoint1.pos + 1.0D, d);
        insertElementAt(pNPoint, ++b);
        pNPoint1 = pNPoint;
      }
    }
  }

  public double calculateAUCPR(double paramDouble)
  {
    if (paramDouble < 0.0D || paramDouble > 1.0D) {
      System.err.println("ERROR: invalid minRecall, must be between 0 and 1 - returning 0");
      return 0.0D;
    }

    if (size() == 0) {
      System.err.println("ERROR: No data to calculate....");
      return 0.0D;
    }

    double d1 = paramDouble * this.totPos;
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

  public String toString()
  {
    StringBuilder str = new StringBuilder("TotalPos: " + this.totPos + ", TotalNeg: " + this.totNeg + "\n");
    for (int i = 0; i < size(); i++) {
      str.append(elementAt(i)).append("\n");
    }
    return str.toString();
  }
}
