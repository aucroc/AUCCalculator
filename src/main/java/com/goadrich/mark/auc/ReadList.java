package com.goadrich.mark.auc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadList
{
    public static ClassSort[] convertList(LinkedList<ClassSort> paramLinkedList)
    {
        // TODO(hayesall): converts a LinkedList<ClassSort> to ArrayList<ClassSort>, then sorts.

        ClassSort[] arrayOfClassSort = new ClassSort[paramLinkedList.size()];

        for (byte b = 0; b < arrayOfClassSort.length; b++) {
            arrayOfClassSort[b] = paramLinkedList.removeFirst();
        }
        Arrays.sort(arrayOfClassSort);
        return arrayOfClassSort;
    }

    public static Confusion readFile(String paramString1) {

        // TODO(hayesall): This feels bad, should probably be a method of Confusion, e.g.: Confusion.fromFile()
        // TODO(hayesall): Split into two functions: read file / interpret results

        LinkedList<ClassSort> linkedList = new LinkedList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(paramString1));

            while (bufferedReader.ready()) {
                String str = bufferedReader.readLine();
                StringTokenizer stringTokenizer = new StringTokenizer(str, "\t ,");

                try {
                    double d1 = Double.parseDouble(stringTokenizer.nextToken());
                    int j = Integer.parseInt(stringTokenizer.nextToken());
                    linkedList.add(new ClassSort(d1, j));

                } catch (NumberFormatException numberFormatException) {
                    System.err.println("...skipping bad input line (bad numbers)");
                } catch (NoSuchElementException noSuchElementException) {
                    System.err.println("...skipping bad input line (missing data)");
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("ERROR: File " + paramString1 + " not found - exiting...");
            System.exit(-1);
        } catch (NoSuchElementException noSuchElementException) {

            // TODO(hayesall): I'm 80% sure I've eliminated the noSuchElementException, revisit later.

            System.err.println("...incorrect fileType argument, either PR or ROC - exiting");
            System.exit(-1);
        } catch (IOException iOException) {
            System.err.println("ERROR: IO Exception in file " + paramString1 + " - exiting...");
            System.exit(-1);
        }

        ClassSort[] arrayOfClassSort = convertList(linkedList);
        ArrayList<PNPoint> arrayList = new ArrayList<>();

        double d = arrayOfClassSort[arrayOfClassSort.length - 1].getProb();

        byte b1 = 0;
        byte b2 = 0;

        if (arrayOfClassSort[arrayOfClassSort.length - 1].getClassification() == 1) {
            b1++;
        } else {
            b2++;
        }

        for (int i = arrayOfClassSort.length - 2; i >= 0; i--) {
            double d1 = arrayOfClassSort[i].getProb();
            int j = arrayOfClassSort[i].getClassification();
            System.out.println(d1 + " " + j);
            if (d1 != d) {
                arrayList.add(new PNPoint(b1, b2));
            }
            d = d1;
            if (j == 1) {
                b1++;
            } else {
                b2++;
            }
        }
        arrayList.add(new PNPoint(b1, b2));
        Confusion confusion = new Confusion(b1, b2);
        for (PNPoint pNPoint : arrayList) {
            confusion.addPoint(pNPoint.getPos(), pNPoint.getNeg());
        }

        confusion.sort();
        confusion.interpolate();
        return confusion;
    }
}
