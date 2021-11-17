# Copyright © 2021 Alexander L. Hayes
# MIT License

from .generate import generate_output_file
import subprocess
import sys


def run_jar_file(jar_name: str, min_recall: float):
    command = "java -jar " + jar_name + " randomoutput.txt list " + str(min_recall)
    return subprocess.run(command.split(), capture_output=True, check=True)


def fuzz():
    i = 0
    while True:

        generate_output_file(i + 5)

        out0 = run_jar_file("history/original-auc.jar", 0.0)
        out1 = run_jar_file("build/libs/auc-0.3.0.jar", 0.0)

        if out0.stdout != out1.stdout:
            print(f"stdout differed at iteration, {i}")
            sys.exit(0)

        if out0.stderr != out1.stderr:
            print(f"stderr differed at iteration, {i}")
            sys.exit(0)

        print(f"{i} fuzzing iterations", end="\r")
        i += 1


if __name__ == "__main__":
    fuzz()
