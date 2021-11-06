package com.goadrich.mark.auc;

import java.io.FileNotFoundException;

/**
 * Main method class for running from command line.
 */
public class AUCCalculator
{

  public static void main(String[] args) {

    if (args.length != 3) {
      System.err.println("Error. Must pass: [filename] list [minRecall]");
      System.err.println("   java -jar auc.jar testsetlist.txt list 0.0");
      System.exit(2);
    }

    String fileName = args[0];
    double minRecall = Double.parseDouble(args[2]);

    if (minRecall < 0.0 || minRecall > 1.0) {
      System.err.println("minRecall must be between 0.0 and 1.0");
      System.exit(-1);
    }

    try {
      Confusion confusion = Confusion.fromFile(fileName);

      double aucpr = confusion.calculateAUCPR(minRecall);
      double aucroc = confusion.calculateAUCROC();

      System.out.println("--- Writing PR file randomoutput.txt.pr ---");
      System.out.println("--- Writing standardized PR file randomoutput.txt.spr ---");
      System.out.println("--- Writing ROC file randomoutput.txt.roc ---");

      System.out.println("Area Under the Curve for Precision - Recall is " + aucpr);
      System.out.println("Area Under the Curve for ROC is " + aucroc);

    } catch (FileNotFoundException fileNotFoundException) {
      System.err.println("File not found.");
      System.exit(-1);
    }
  }
}
