# Some history

## First Steps: Decompiling the Jar

The first step was to convert the jar file back to source files, which roughly
followed these steps:

1. Download a copy of [auc.jar](http://mark.goadrich.com/programs/AUC/)
2. Decompile with "Java Decompiler" (http://java-decompiler.github.io/)
3. The decompiler seems to have done a pretty good job,
   but it missed an import in `ReadList.java`:

  ```diff
  + import java.io.File;
  ```

The source files are committed to this repository following some reorganizing.

1. Run on the included `testsetlist.txt` file:

  ```bash
  java -jar target/auc-0.2.1-jar-with-dependencies.jar testsetlist.txt list
  ```

2. This produces the following outputs, and we can inspect three created files:
   `testsetlist.txt.pr`, `testsetlist.txt.roc`, and `testsetlist.txt.spr`.

  ```console
  0.8 1
  0.7 0
  0.6 1
  0.55 1
  0.54 1
  0.53 0
  0.52 0
  0.51 1
  0.505 0
  --- Writing PR file testsetlist.txt.pr ---
  --- Writing standardized PR file testsetlist.txt.spr ---
  --- Writing ROC file testsetlist.txt.roc ---
  Area Under the Curve for Precision - Recall is 0.8243055555555555
  Area Under the Curve for ROC is 0.75
  ```
