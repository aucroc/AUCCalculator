package com.goadrich.mark.auc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassSortTest
{

  @Test
  public void testInitializeClassSort1()
  {
    ClassSort cls = new ClassSort(0.5, 1);
    assertEquals(cls.probability, 0.5);
    assertEquals(cls.classification, 1);
  }

  @Test
  public void testInitializeClassSort0()
  {
    ClassSort cls = new ClassSort(0.75, 0);
    assertEquals(cls.probability, 0.75);
    assertEquals(cls.classification, 0);
  }

  @Test
  public void testClassSorttoString()
  {
    ClassSort cls = new ClassSort(0.5, 1);
    assertEquals(cls.toString(), "(0.5,1)");
  }
}
