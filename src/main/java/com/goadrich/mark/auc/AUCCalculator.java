package com.goadrich.mark.auc;

public class AUCCalculator
{

  public static void main(String[] paramArrayOfString)
  {

    // Usage: to stay compatible with the old API at the moment, this uses
    //    arguments at position [0] and [2]. Previously, [1] had to have the
    //    string "list" passed as an argument, but a refactor removed this.

    // TODO(hayesall): My unsafe refactor introduced nulls
    String fileName = paramArrayOfString[0];
    double minRecall = Double.parseDouble(paramArrayOfString[2]);

    assert minRecall >= 0.0;
    assert minRecall <= 1.0;

    Confusion confusion = ReadList.readFile(fileName);
    confusion.calculateAUCPR(minRecall);
    confusion.calculateAUCROC();
  }
}
