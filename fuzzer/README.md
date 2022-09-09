# Fuzzing the AUCCalculator

We have the original `auc.jar`, a version created by the decompiler, and now
we have multiple versions produced during refactoring.

How do we know that all of these are equivalent?
We can *[fuzz test](https://en.wikipedia.org/wiki/Fuzzing)*.

## Quickstart

Compile and install the Rust implementation:

```bash
pip install maturin setuptools-rust
cd auc-calculator/lib-auccalc-py
maturin develop --release
pip install -e .
cd ../..
```

Compile the Java implementation:

```bash
./gradlew build
```

Run the fuzzer:

```bash
python -m fuzzer
```

## Overview

The `AUCCalculator` should be deterministic: given the same inputs we expect
it to produce the same outputs. So we might reveal errors by randomly generating
inputs, passing the inputs into both versions, and observing the results.

As a first pass, we'll random generate a floating point number an integer in
`{0, 1}`:

```python
def generate_output_file(size: int) -> None:
    with open("randomoutput.txt", "w") as fh:
        for s in range(size):
            fh.write(str(random.random()) + " " + str(random.randint(0, 1)) + "\n")
```

So our strategy will be to (1) generate inputs, (2) pass the inputs into
`auc.jar` and the development copy, and (3) hopefully find inputs that
where the results do not match:

```python
while True:
    generate_output_file(i)

    out0 = run_jar_file("original-auc.jar", 1.0)
    out1 = run_jar_file("target/auc-0.2.1-jar-with-dependencies.jar", 1.0)

    if out0.stdout != out1.stdout:
        sys.exit(0)
```

Almost immediately, this revealed bounds errors in the decompiled version.
In many cases, the decompiler produced `byte` when `int` was
probably correct, which explains why many of the errors occurred when the
input files were ~128 or ~256 lines long (i.e. an overflow occurred).

```console
[fail005.txt] (127 lines)

(trimmed, failed)
0.014305310851898678 0
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -128 out of bounds for length 160
	at java.base/java.util.Vector.elementData(Vector.java:762)
	at java.base/java.util.Vector.elementAt(Vector.java:500)
	at com.goadrich.mark.auc.Confusion.calculateAUCROC(Confusion.java:163)
	at com.goadrich.mark.auc.AUCCalculator.main(AUCCalculator.java:22)

(fixed problem with byte -> int, line 78 in Confusion.java)
```

```console
[fail006.txt] (128 lines)

Exception in thread "main" java.util.NoSuchElementException
	at java.base/java.util.LinkedList.removeFirst(LinkedList.java:274)
	at com.goadrich.mark.auc.ReadList.convertList(ReadList.java:18)
	at com.goadrich.mark.auc.ReadList.readFile(ReadList.java:64)
        at com.goadrich.mark.auc.AUCCalculator.main(AUCCalculator.java:20)

(fixed with byte -> int, line 17-18 in ReadList.java)
```

```console
[fail007.txt] (127 lines)

Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -128 out of bounds for length 160
	at java.base/java.util.Vector.elementData(Vector.java:762)
	at java.base/java.util.Vector.elementAt(Vector.java:500)
	at com.goadrich.mark.auc.Confusion.calculateAUCROC(Confusion.java:163)
	at com.goadrich.mark.auc.AUCCalculator.main(AUCCalculator.java:22)

(fixed with byte -> int, line 162-163 in Confusion.java)
```

```console
[fail008.txt] (129 lines)

Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -128 out of bounds for length 129
	at com.goadrich.mark.auc.Confusion.sort(Confusion.java:45)
	at com.goadrich.mark.auc.ReadList.readFile(ReadList.java:98)
	at com.goadrich.mark.auc.AUCCalculator.main(AUCCalculator.java:20)

(fixed with byte -> int, line 43 in Confusion.java)
```

```console
[fail009.txt] (232 lines)

ERROR: 100.0,-128.0 - Defaulting PNPoint to 0,0
0.02458387472293344 1
ERROR: 100.0,-127.0 - Defaulting PNPoint to 0,0
0.01689671336582621 0
ERROR: 101.0,-127.0 - Defaulting PNPoint to 0,0
0.012911421686382707 0
ERROR: 101.0,-126.0 - Defaulting PNPoint to 0,0
ERROR: 101.0,-125.0 - Defaulting PNPoint to 0,0
ERROR: 101.0,-125.0 - Defaulting Confusion to 1,1
Exception in thread "main" java.lang.NumberFormatException
	at com.goadrich.mark.auc.Confusion.addPoint(Confusion.java:26)
	at com.goadrich.mark.auc.ReadList.readFile(ReadList.java:95)
	at com.goadrich.mark.auc.AUCCalculator.main(AUCCalculator.java:20)

(fixed with byte -> int, line 69-70 in ReadList.java)
```

```console
[changed from minRecall=0.0 to minRecall=0.5]
[fail010.txt] (246 lines)

ERROR: minRecall out of bounds - exiting...

(fixed with byte -> int, line 110 Confusion.java)
```
