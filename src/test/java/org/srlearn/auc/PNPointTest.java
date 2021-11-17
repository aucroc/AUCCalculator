package org.srlearn.auc;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNPointTest
{

  @Test
  public void testInitializePNPoint()
  {
    PNPoint pnt = new PNPoint(1.0, 1.0);
    assertEquals(pnt.pos, 1.0);
    assertEquals(pnt.neg, 1.0);
  }

  @Test
  public void testInitializeNegativePosPNPoint()
  {
    PNPoint pnt = new PNPoint(-1.0, 1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  @Test
  public void testInitializeNegativeNegPNPoint()
  {
    PNPoint pnt = new PNPoint(1.0, -1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  @Test
  public void testInitializeNegativePosNegPNPoint()
  {
    PNPoint pnt = new PNPoint(-1.0, -1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  @Test
  public void testPNPointtoString()
  {
    PNPoint pnt = new PNPoint(1.0, 2.0);
    assertEquals("(1.0,2.0)", pnt.toString());
  }

  @Test
  public void testCompareEqualPNPoints()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertEquals(pnt1, pnt2);
  }

  @Test
  public void testCompareUnequalPoints1()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 3.0);
    assertNotEquals(pnt1, pnt2);
  }

  @Test
  public void testCompareUnequalPoints2()
  {
    PNPoint pnt1 = new PNPoint(3.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertNotEquals(pnt1, pnt2);
  }

  @Test
  public void testCompareUnequalPoints3()
  {
    PNPoint pnt1 = new PNPoint(1.0, 3.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertNotEquals(pnt1, pnt2);
  }

  @Test
  public void testCompareUnequalPoints4()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(3.0, 1.0);
    assertNotEquals(pnt1, pnt2);
  }

  @Test
  public void testCompareNullPoint()
  {
    PNPoint pnt1 = new PNPoint(2.0, 2.0);
    assertNotEquals(pnt1, null);
  }

  @Test
  public void testSortPNPoints()
  {

    PNPoint[] actual = {
      new PNPoint(2.0, 1.0),
      new PNPoint(1.0, 1.0),
      new PNPoint(3.0, 1.0),
    };

    PNPoint[] expected = {
      new PNPoint(1.0, 1.0),
      new PNPoint(2.0, 1.0),
      new PNPoint(3.0, 1.0),
    };

    Arrays.sort(actual);

    assertArrayEquals(actual, expected);
  }

  @Test
  public void testSortPNPoints2()
  {
    PNPoint[] actual = {
      new PNPoint(1.0, 2.0),
      new PNPoint(1.0, 1.0),
      new PNPoint(1.0, 3.0),
    };

    PNPoint[] expected = {
      new PNPoint(1.0, 1.0),
      new PNPoint(1.0, 2.0),
      new PNPoint(1.0, 3.0),
    };

    Arrays.sort(actual);
    assertArrayEquals(actual, expected);
  }

  @Test
  public void testCompareToPNPoints0()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertEquals(0, pnt1.compareTo(pnt2));
  }

  @Test
  public void testCompareToPNPoints1()
  {
    PNPoint pnt1 = new PNPoint(1.0, 2.0);
    PNPoint pnt2 = new PNPoint(2.0, 2.0);
    assertEquals(-1, pnt1.compareTo(pnt2));
  }

  @Test
  public void testCompareToPNPoints2()
  {
    PNPoint pnt1 = new PNPoint(2.0, 2.0);
    PNPoint pnt2 = new PNPoint(1.0, 2.0);
    assertEquals(1, pnt1.compareTo(pnt2));
  }

  @Test
  public void testCompareToPNPoints3()
  {
    PNPoint pnt1 = new PNPoint(1.0, 2.0);
    assertEquals(-1, pnt1.compareTo(null));
  }
}
