package com.goadrich.mark.auc;

public class ClassSort implements Comparable<ClassSort>
{

  // TODO(hayesall): Get rid of the getter pattern.

  private final double probability;
  private final int classification;

  public ClassSort(double probability, int classification)
  {
    // TODO(hayesall): There are some invariants that might be worth enforcing.
    //    e.g. 0.0 <= probability <= 1.0
    //    e.g. classification in {0, 1}

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

  public int compareTo(ClassSort other)
  {

    double d = other.probability;

    if (this.probability < d) {
      return -1;
    }

    if (this.probability > d) {
      return 1;
    }

    return Integer.compare(other.classification, this.classification);
  }

  public String toString()
  {
    return "(" + this.probability + "," + this.classification + ")";
  }

}
