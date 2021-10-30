package com.goadrich.mark.auc;

public class PNPoint implements Comparable
{
  private double pos;
  private double neg;

  // TODO(hayesall): Refactor out the getter pattern.

  public PNPoint(double pos, double neg)
  {
    if (pos < 0.0 || neg < 0.0) {
      // TODO(hayesall): This feels like an invariant that should be pulled out
      //    and enforced somewhere else.
      this.pos = 0.0;
      this.neg = 0.0;
      System.err.println("ERROR: " + pos + "," + neg + " - Defaulting PNPoint to 0,0");
    } else {
      this.pos = pos;
      this.neg = neg;
    }
  }

  public double getPos()
  {
    return this.pos;
  }

  public double getNeg()
  {
    return this.neg;
  }

  public String toString()
  {
    return "(" + this.pos + "," + this.neg + ")";
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject instanceof PNPoint) {
      PNPoint pNPoint = (PNPoint)paramObject;

      if (Math.abs(this.pos - pNPoint.pos) > 0.001) {
        return false;
      }
      if (Math.abs(this.neg - pNPoint.neg) > 0.001) {
        return false;
      }
      return true;
    }
    return false;
  }

  public int compareTo(Object paramObject)
  {
    if (paramObject instanceof PNPoint)
    {
      PNPoint pNPoint = (PNPoint)paramObject;

      if (this.pos - pNPoint.pos > 0.0) {
        return 1;
      }
      if (this.pos - pNPoint.pos < 0.0) {
        return -1;
      }
      if (this.neg - pNPoint.neg > 0.0) {
        return 1;
      }
      if (this.neg - pNPoint.neg < 0.0) {
        return -1;
      }
      return 0;
    }
    return -1;
  }
}
