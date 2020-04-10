# AUC Calculator

This is a recreation of "[AUCCalculator](http://mark.goadrich.com/programs/AUC/),"
based on the original implementation by Jesse Davis and Mark Goadrich, and described in
"[The relationship between Precision-Recall and ROC curves](https://dl.acm.org/doi/abs/10.1145/1143844.1143874)."

## License

This repository does not currently declare a license.

This code is provided as-is for academic purposes only. Please let us know if you find any bugs or have any questions at richm@cs.wisc.edu and jdavis@cs.wisc.edu. If you use this software, we request following paper be cited as a reference.

## Reference

Please refer to the following paper:

ACM Reference Format:

> Jesse Davis and Mark Goadrich. 2006. The relationship between Precision-Recall and ROC curves. In Proceedings of the 23rd international conference on Machine learning (ICML ’06). Association for Computing Machinery, New York, NY, USA, 233–240. DOI:https://doi.org/10.1145/1143844.1143874

BiBTeX:

```bibtex
@inproceedings{10.1145/1143844.1143874,
  author = {Davis, Jesse and Goadrich, Mark},
  title = {The Relationship between Precision-Recall and ROC Curves},
  year = {2006},
  isbn = {1595933832},
  publisher = {Association for Computing Machinery},
  address = {New York, NY, USA},
  url = {https://doi.org/10.1145/1143844.1143874},
  doi = {10.1145/1143844.1143874},
  booktitle = {Proceedings of the 23rd International Conference on Machine Learning},
  pages = {233–240},
  numpages = {8},
  location = {Pittsburgh, Pennsylvania, USA},
  series = {ICML ’06}
}
```

## Overview

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

1. The project can be built as a [Maven Package](https://maven.apache.org/):

  ```bash
  mvn package
  ```

2. Run on the included `testsetlist.txt` file:

  ```bash
  java -jar target/auc-0.2.1-jar-with-dependencies.jar testsetlist.txt list
  ```

3. This produces the following outputs, and we can inspect three created files:
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

7. The outputs match the original `auc.jar`, so we might consider this as
  evidence that we successfully reproduced the source code. However, this
  argument is *necessary* but not *sufficient*, it does not deal with potential
  corner cases.
