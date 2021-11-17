package org.srlearn.auc;

class ClassSort implements Comparable<ClassSort>
{

  public final double probability;
  public final int classification;

  public ClassSort(double probability, int classification)
  {
    if (probability < 0.0 || probability > 1.0) {
      throw new NumberFormatException("Probabilities must be between 0.0 and 1.0");
    }
    if (classification != 0 && classification != 1) {
      throw new NumberFormatException("Binary classification must be represented with 0 or 1");
    }

    this.probability = probability;
    this.classification = classification;
  }

  public int compareTo(ClassSort other)
  {
    if (this.probability < other.probability) {
      return -1;
    }
    if (this.probability > other.probability) {
      return 1;
    }
    return Integer.compare(other.classification, this.classification);
  }

  public String toString()
  {
    return "(" + this.probability + "," + this.classification + ")";
  }
}
