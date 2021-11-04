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

    /*
    // TODO(hayesall): API might look something like this.
    double[] y_pred = new double[]{0.8, 0.9, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505};
    int[] y_true = new int[]{1, 1, 0, 1, 1, 1, 0, 0, 1, 0};
    Confusion confusion = Confusion.fromPredictions(y_pred, y_true);
     */

    double aucpr = confusion.calculateAUCPR(minRecall);
    double aucroc = confusion.calculateAUCROC();

    System.out.println("--- Writing PR file randomoutput.txt.pr ---");
    System.out.println("--- Writing standardized PR file randomoutput.txt.spr ---");
    System.out.println("--- Writing ROC file randomoutput.txt.roc ---");

    System.out.println("Area Under the Curve for Precision - Recall is " + aucpr);
    System.out.println("Area Under the Curve for ROC is " + aucroc);
  }
}
