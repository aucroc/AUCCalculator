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

## Usage Notes

The only prerequisite is Java, and this should be compatible with Java version 8 or higher.
Check if it's available on your path and install if it isn't:

```bash
java -version
```

### Linux / MacOS

Build the project:

```bash
./gradlew build
```

Use the commandline interface to run a sample file:

```bash
java -jar build/libs/auc-0.3.0.jar history/testsetlist.txt list 0.0
```

Check tests:

```bash
xdg-open build/reports/tests/test/index.html
```

View code coverage results:

```bash
xdg-open build/reports/jacoco/test/html/index.html
```

### Windows (PowerShell environment):

Build the project:

```powershell
.\gradlew.bat build
```

Use the commandline interface to run a sample file:

```powershell
java -jar .\build\libs\auc-0.3.0.jar .\history\testsetlist.txt list 0.0
```

Check tests:

```powershell
Invoke-Item .\build\reports\tests\test\index.html
```

View code coverage results:

```powershell
Invoke-Item .\build\reports\jacoco\test\html\index.html
```
