package com.goadrich.mark.auc;

public class ClassSort implements Comparable
{

  // TODO(hayesall): Get rid of the getter pattern.

  private double probability;
  private int classification;

  public ClassSort(double probability, int classification)
  {
    this.probability = probability;
    this.classification = classification;
  }

  public int getClassification()
  {
    return this.classification;
  }

  public double getProb()
  {
    return this.probability;
  }

  public int compareTo(Object paramObject)
  {

    // TODO(hayesall): Assert Object is of type ClassSort, compare common types.

    // TODO(hayesall): The conditional logic could probably be simplified here.

    double d = ((ClassSort)paramObject).getProb();

    if (this.probability < d) {
      return -1;
    }

    if (this.probability > d) {
      return 1;
    }

    int i = ((ClassSort)paramObject).getClassification();

    if (i == this.classification) {
      return 0;
    }
    if (this.classification > i) {
      return -1;
    }

    return 1;
  }

  public String toString()
  {
    return "(" + this.probability + "," + this.classification + ")";
  }

}
