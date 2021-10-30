package com.goadrich.mark.auc;

public class AUCCalculator
{
  private static String fileName;
  private static String fileType;
  private static double minRecall = 0.0;

  public static boolean DEBUG = false;

  public static void main(String[] paramArrayOfString)
  {
    // TODO(hayesall): My unsafe refactor introduced nulls
    fileName = paramArrayOfString[0];
    fileType = paramArrayOfString[1];
    minRecall = Double.parseDouble(paramArrayOfString[2]);

    assert minRecall >= 0.0;
    assert minRecall <= 1.0;

    Confusion confusion = ReadList.readFile(fileName, fileType);
    confusion.calculateAUCPR(minRecall);
    confusion.calculateAUCROC();
  }
}
