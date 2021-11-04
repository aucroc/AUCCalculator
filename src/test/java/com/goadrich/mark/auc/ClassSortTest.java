package com.goadrich.mark.auc;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ClassSortTest extends TestCase
{
  public ClassSortTest(String testName)
  {
    super(testName);
  }

  public static Test suite()
  {
    return new TestSuite(ClassSortTest.class);
  }

  public void testInitializeClassSort1()
  {
    ClassSort cls = new ClassSort(0.5, 1);
    assertEquals(cls.probability, 0.5);
    assertEquals(cls.classification, 1);
  }

  public void testInitializeClassSort0()
  {
    ClassSort cls = new ClassSort(0.75, 0);
    assertEquals(cls.probability, 0.75);
    assertEquals(cls.classification, 0);
  }

  public void testClassSorttoString()
  {
    ClassSort cls = new ClassSort(0.5, 1);
    assertEquals(cls.toString(), "(0.5,1)");
  }
}
