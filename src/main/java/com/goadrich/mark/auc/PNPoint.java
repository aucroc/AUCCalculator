package com.goadrich.mark.auc;

import java.util.Objects;

public class PNPoint implements Comparable<PNPoint>
{
  public final double pos;
  public final double neg;

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

  public String toString()
  {
    return "(" + this.pos + "," + this.neg + ")";
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PNPoint pnPoint = (PNPoint) o;
    return Double.compare(pnPoint.pos, pos) == 0 && Double.compare(pnPoint.neg, neg) == 0;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(pos, neg);
  }

  public int compareTo(PNPoint other)
  {
    if (other != null)
    {

      if (this.pos - other.pos > 0.0) {
        return 1;
      }
      if (this.pos - other.pos < 0.0) {
        return -1;
      }
      return Double.compare(this.neg - other.neg, 0.0);
    }
    return -1;
  }
}
