/*     */ package com.goadrich.mark.auc;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
          import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */
/*     */ public class ReadList {
/*     */   public static ClassSort[] convertList(LinkedList<ClassSort> paramLinkedList) {
/*  14 */     ClassSort[] arrayOfClassSort = new ClassSort[paramLinkedList.size()];
/*  15 */     for (byte b = 0; b < arrayOfClassSort.length; b++) {
/*  16 */       arrayOfClassSort[b] = paramLinkedList.removeFirst();
/*     */     }
/*  18 */     Arrays.sort((Object[])arrayOfClassSort);
/*  19 */     return arrayOfClassSort;
/*     */   }
/*     */
/*     */
/*     */   public static final int TP = 0;
/*     */   public static final int FP = 1;
/*     */   public static final int FN = 2;
/*     */   public static final int TN = 3;
/*     */
/*     */   public static Confusion accuracyScoreAllSplits(ClassSort[] paramArrayOfClassSort, int paramInt1, int paramInt2) {
/*  29 */     Arrays.sort((Object[])paramArrayOfClassSort);
/*  30 */     for (int i = paramArrayOfClassSort.length - 1; i >= paramArrayOfClassSort.length - 20; i--);
/*     */
/*     */
/*     */
/*  34 */     Confusion confusion = new Confusion(paramInt1, paramInt2);
/*  35 */     int j = 0;
/*  36 */     double d1 = paramArrayOfClassSort[paramArrayOfClassSort.length - 1].getProb();
/*     */
/*  38 */     int k = paramArrayOfClassSort[paramArrayOfClassSort.length - 1].getClassification();
/*     */
/*  40 */     double d2 = 0.0D;
/*  41 */     double d3 = 0.0D;
/*  42 */     double d4 = 0.0D;
/*     */
/*  44 */     double[] arrayOfDouble = new double[paramArrayOfClassSort.length];
/*  45 */     int[] arrayOfInt = new int[paramArrayOfClassSort.length];
/*  46 */     for (byte b = 0; b < paramArrayOfClassSort.length; b++) {
/*  47 */       arrayOfDouble[b] = paramArrayOfClassSort[b].getProb();
/*  48 */       arrayOfInt[b] = paramArrayOfClassSort[b].getClassification();
/*     */     }
/*     */
/*  51 */     LinkedList linkedList = new LinkedList();
/*     */
/*  53 */     for (int m = paramArrayOfClassSort.length - 2; m >= 0; m--) {
/*  54 */       int n = paramArrayOfClassSort[m].getClassification();
/*  55 */       double d = paramArrayOfClassSort[m].getProb();
/*     */
/*  57 */       if (k == 1 && 0 == n) {
/*  58 */         if (paramArrayOfClassSort[m + 1].getProb() <= d)
/*     */         {
/*     */
/*  61 */           if (paramArrayOfClassSort[m + 1].getProb() <= d)
/*     */           {
/*     */
/*     */
/*  65 */             System.out.println("Bad"); }
/*     */         }
/*  67 */         int[] arrayOfInt1 = fastAccuracy(arrayOfDouble, arrayOfInt, d1);
/*     */
/*  69 */         confusion.addPoint(arrayOfInt1[0], arrayOfInt1[1]);
/*     */       }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*  77 */       j += n;
/*  78 */       d1 = d;
/*  79 */       k = n;
/*     */     }
/*     */
/*  82 */     return confusion;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static int[] fastAccuracy(double[] paramArrayOfdouble, int[] paramArrayOfint, double paramDouble) {
/*  99 */     int[] arrayOfInt = new int[4]; byte b;
/* 100 */     for (b = 0; b < arrayOfInt.length; b++) {
/* 101 */       arrayOfInt[b] = 0;
/*     */     }
/* 103 */     for (b = 0; b < paramArrayOfdouble.length; b++) {
/* 104 */       if (paramArrayOfdouble[b] >= paramDouble) {
/* 105 */         if (paramArrayOfint[b] == 1) {
/* 106 */           arrayOfInt[0] = arrayOfInt[0] + 1;
/*     */         } else {
/*     */
/* 109 */           arrayOfInt[1] = arrayOfInt[1] + 1;
/*     */         }
/*     */
/*     */       }
/* 113 */       else if (paramArrayOfint[b] == 1) {
/* 114 */         arrayOfInt[2] = arrayOfInt[2] + 1;
/*     */       } else {
/*     */
/* 117 */         arrayOfInt[3] = arrayOfInt[3] + 1;
/*     */       }
/*     */     }
/*     */
/* 121 */     return arrayOfInt;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Confusion readFile(String paramString1, String paramString2) {
/* 133 */     byte b1 = 0;
/* 134 */     byte b2 = 0;
/* 135 */     byte b3 = 0;
/* 136 */     LinkedList<ClassSort> linkedList = new LinkedList();
/* 137 */     BufferedReader bufferedReader = null;
/*     */     try {
/* 139 */       bufferedReader = new BufferedReader(new FileReader(new File(paramString1)));
/*     */
/*     */
/* 142 */       while (bufferedReader.ready()) {
/*     */
/* 144 */         String str = bufferedReader.readLine();
/* 145 */         if (AUCCalculator.DEBUG);
/*     */
/*     */
/*     */
/*     */
/* 150 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "\t ,");
/*     */
/*     */
/*     */         try {
/* 154 */           double d1 = Double.parseDouble(stringTokenizer.nextToken());
/* 155 */           int j = Integer.parseInt(stringTokenizer.nextToken());
/* 156 */           linkedList.add(new ClassSort(d1, j));
/*     */
/*     */
/*     */
/* 160 */           if (AUCCalculator.DEBUG);
/*     */
/*     */
/* 163 */           if (AUCCalculator.DEBUG);
/*     */
/*     */
/*     */         }
/* 167 */         catch (NumberFormatException numberFormatException) {
/*     */
/* 169 */           System.err.println("...skipping bad input line (bad numbers)");
/* 170 */         } catch (NoSuchElementException noSuchElementException) {
/*     */
/* 172 */           System.err.println("...skipping bad input line (missing data)");
/*     */         }
/*     */       }
/* 175 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */
/* 177 */       System.err.println("ERROR: File " + paramString1 + " not found - exiting...");
/* 178 */       System.exit(-1);
/* 179 */     } catch (NoSuchElementException noSuchElementException) {
/*     */
/* 181 */       System.err.println("...incorrect fileType argument, either PR or ROC - exiting");
/* 182 */       System.exit(-1);
/* 183 */     } catch (IOException iOException) {
/*     */
/* 185 */       System.err.println("ERROR: IO Exception in file " + paramString1 + " - exiting...");
/* 186 */       System.exit(-1);
/*     */     }
/*     */
/*     */
/* 190 */     ClassSort[] arrayOfClassSort = convertList(linkedList);
/* 191 */     ArrayList<PNPoint> arrayList = new ArrayList();
/* 192 */     double d = arrayOfClassSort[arrayOfClassSort.length - 1].getProb();
/* 193 */     if (arrayOfClassSort[arrayOfClassSort.length - 1].getClassification() == 1) {
/* 194 */       b1++;
/*     */     } else {
/* 196 */       b2++;
/*     */     }
/* 198 */     b3++;
/* 199 */     for (int i = arrayOfClassSort.length - 2; i >= 0; i--) {
/*     */
/* 201 */       double d1 = arrayOfClassSort[i].getProb();
/* 202 */       int j = arrayOfClassSort[i].getClassification();
/* 203 */       System.out.println(d1 + " " + j);
/*     */
/* 205 */       if (d1 != d) {
/* 206 */         arrayList.add(new PNPoint(b1, b2));
/*     */       }
/* 208 */       d = d1;
/*     */
/* 210 */       if (j == 1) {
/* 211 */         b1++;
/*     */       } else {
/* 213 */         b2++;
/*     */       }
/* 215 */       b3++;
/*     */     }
/* 217 */     arrayList.add(new PNPoint(b1, b2));
/*     */
/*     */
/*     */
/* 221 */     Confusion confusion = new Confusion(b1, b2);
/* 222 */     for (PNPoint pNPoint : arrayList) {
/* 223 */       confusion.addPoint(pNPoint.getPos(), pNPoint.getNeg());
/*     */     }
/*     */
/* 226 */     confusion.sort();
/*     */
/* 228 */     confusion.interpolate();
/*     */
/* 230 */     return confusion;
/*     */   }
/*     */ }
