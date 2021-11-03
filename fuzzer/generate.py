# Copyright © 2021 Alexander L. Hayes
# MIT License


import random


def generate_output_file(size: int) -> None:
    with open("randomoutput.txt", "w") as fh:
        for s in range(size):
            fh.write(str(random.random()) + " " + str(random.randint(0, 1)) + "\n")
