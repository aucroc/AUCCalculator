# Copyright © 2021 Alexander L. Hayes
# MIT License


import random


def generate_output_file(size: int):

    probas, labels = [], []

    for _ in range(size):
        probas.append(random.random())
        labels.append(random.randint(0, 1))

    with open("randomoutput.txt", "w") as fh:
        for (p, l) in zip(probas, labels):
            fh.write(str(p) + " " + str(l) + "\n")

    return probas, labels
