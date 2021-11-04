package com.goadrich.mark.auc;

import java.util.Arrays;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.assertArrayEquals;


public class PNPointTest extends TestCase
{

  public PNPointTest(String testName)
  {
    super(testName);
  }

  public static Test suite()
  {
    return new TestSuite(PNPointTest.class);
  }

  public void testInitializePNPoint()
  {
    PNPoint pnt = new PNPoint(1.0, 1.0);
    assertEquals(pnt.pos, 1.0);
    assertEquals(pnt.neg, 1.0);
  }

  public void testInitializeNegativePosPNPoint()
  {
    PNPoint pnt = new PNPoint(-1.0, 1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  public void testInitializeNegativeNegPNPoint()
  {
    PNPoint pnt = new PNPoint(1.0, -1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  public void testInitializeNegativePosNegPNPoint()
  {
    PNPoint pnt = new PNPoint(-1.0, -1.0);
    assertEquals(pnt.pos, 0.0);
    assertEquals(pnt.neg, 0.0);
  }

  public void testPNPointtoString()
  {
    PNPoint pnt = new PNPoint(1.0, 2.0);
    assertEquals("(1.0,2.0)", pnt.toString());
  }

  public void testCompareEqualPNPoints()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertEquals(pnt1, pnt2);
  }

  public void testCompareUnequalPoints1()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 3.0);
    assertNotSame(pnt1, pnt2);
  }

  public void testCompareUnequalPoints2()
  {
    PNPoint pnt1 = new PNPoint(3.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertNotSame(pnt1, pnt2);
  }

  public void testCompareUnequalPoints3()
  {
    PNPoint pnt1 = new PNPoint(1.0, 3.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertNotSame(pnt1, pnt2);
  }

  public void testCompareUnequalPoints4()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(3.0, 1.0);
    assertNotSame(pnt1, pnt2);
  }

  public void testCompareNullPoint()
  {
    PNPoint pnt1 = new PNPoint(2.0, 2.0);
    PNPoint pnt2 = null;
    assertNotSame(pnt1, pnt2);
  }

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

  public void testCompareToPNPoints0()
  {
    PNPoint pnt1 = new PNPoint(1.0, 1.0);
    PNPoint pnt2 = new PNPoint(1.0, 1.0);
    assertEquals(0, pnt1.compareTo(pnt2));
  }

  public void testCompareToPNPoints1()
  {
    PNPoint pnt1 = new PNPoint(1.0, 2.0);
    PNPoint pnt2 = new PNPoint(2.0, 2.0);
    assertEquals(-1, pnt1.compareTo(pnt2));
  }

  public void testCompareToPNPoints2()
  {
    PNPoint pnt1 = new PNPoint(2.0, 2.0);
    PNPoint pnt2 = new PNPoint(1.0, 2.0);
    assertEquals(1, pnt1.compareTo(pnt2));
  }

  public void testCompareToPNPoints3()
  {
    PNPoint pnt1 = new PNPoint(1.0, 2.0);
    PNPoint pnt2 = null;
    assertEquals(-1, pnt1.compareTo(pnt2));
  }
}
