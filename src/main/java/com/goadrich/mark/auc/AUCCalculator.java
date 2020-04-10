/*     */ package com.goadrich.mark.auc;
/*     */
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class AUCCalculator
/*     */ {
/*     */   private static String fileName;
/*     */   private static String fileType;
/*     */   private static double posCount;
/*     */   private static double negCount;
/*  22 */   private static double minRecall = 0.0D;
/*     */
/*     */
/*     */   public static boolean DEBUG = false;
/*     */
/*     */
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     Confusion confusion;
/*  30 */     readArgs(paramArrayOfString);
/*     */
/*  32 */     if (fileType.equalsIgnoreCase("list")) {
/*  33 */       confusion = ReadList.readFile(fileName, fileType);
/*     */     } else {
/*     */
/*  36 */       confusion = readFile(fileName, fileType, posCount, negCount);
/*     */     }
/*  38 */     confusion.writePRFile(fileName + ".pr");
/*  39 */     confusion.writeStandardPRFile(fileName + ".spr");
/*  40 */     confusion.writeROCFile(fileName + ".roc");
/*  41 */     confusion.calculateAUCPR(minRecall);
/*  42 */     confusion.calculateAUCROC();
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static void readArgs(String[] paramArrayOfString) {
/*  50 */     fileName = "";
/*  51 */     byte b = 2;
/*     */     try {
/*  53 */       fileName = paramArrayOfString[0];
/*  54 */       fileType = paramArrayOfString[1];
/*  55 */       if (!fileType.equalsIgnoreCase("PR") && !fileType.equalsIgnoreCase("ROC") && !fileType.equalsIgnoreCase("list"))
/*     */       {
/*     */
/*  58 */         throw new NoSuchElementException();
/*     */       }
/*  60 */       if (fileType.equalsIgnoreCase("PR") || fileType.equalsIgnoreCase("ROC")) {
/*  61 */         posCount = Double.parseDouble(paramArrayOfString[2]);
/*  62 */         negCount = Double.parseDouble(paramArrayOfString[3]);
/*  63 */         b = 4;
/*  64 */         if (posCount < 1.0D || negCount < 1.0D) {
/*  65 */           throw new NumberFormatException();
/*     */         }
/*     */       }
/*  68 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  69 */       System.err.println("ERROR: Missing Arguments - exiting...");
/*  70 */       System.err.println("Usage:\njava AUCCalculator <fileName> <fileType> <posCount*> <negCount*> <minRecall**>");
/*  71 */       System.err.println("   posCount and negCount required if fileType ROC or PR");
/*  72 */       System.err.println("   minRecall always optional");
/*  73 */       System.exit(-1);
/*  74 */     } catch (NumberFormatException numberFormatException) {
/*  75 */       System.err.println("ERROR: Incorrect Count arguments, must be positive numbers - exiting...");
/*  76 */       System.err.println("Usage:\njava AUCCalculator <fileName> <posCount*> <negCount*> <minRecall**>");
/*  77 */       System.err.println("   posCount and negCount required if fileType ROC or PR");
/*  78 */       System.err.println("   minRecall always optional");
/*  79 */       System.exit(-1);
/*  80 */     } catch (NoSuchElementException noSuchElementException) {
/*  81 */       System.err.println("ERROR: Incorrect fileType, must be ROC, PR, LIST - exiting...");
/*  82 */       System.err.println("Usage:\njava AUCCalculator <fileName> <posCount*> <negCount*> <minRecall**>");
/*  83 */       System.err.println("   posCount and negCount required if fileType ROC or PR");
/*  84 */       System.err.println("   minRecall always optional");
/*  85 */       System.exit(-1);
/*     */     }
/*     */
/*     */     try {
/*  89 */       minRecall = Double.parseDouble(paramArrayOfString[b]);
/*  90 */       if (minRecall < 0.0D || minRecall > 1.0D) {
/*  91 */         throw new NumberFormatException();
/*     */       }
/*  93 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */
/*  95 */     } catch (NumberFormatException numberFormatException) {
/*  96 */       System.err.println("ERROR: Incorrect minRecall argument, must be positive between 0 and 1 - exiting...");
/*  97 */       System.err.println("Usage:\njava AUCCalculator <fileName> <posCount*> <negCount*> <minRecall**>");
/*  98 */       System.err.println("   posCount and negCount required if fileType ROC or PR");
/*  99 */       System.err.println("   minRecall always optional");
/* 100 */       System.exit(-1);
/*     */     }
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
/*     */   public static Confusion readFile(String paramString1, String paramString2, double paramDouble1, double paramDouble2) {
/* 115 */     if (DEBUG) {
/* 116 */       System.out.println("--- Reading in " + paramString2 + " File: " + paramString1 + " ---");
/*     */     }
/*     */
/* 119 */     Confusion confusion = new Confusion(paramDouble1, paramDouble2);
/*     */
/* 121 */     BufferedReader bufferedReader = null;
/*     */     try {
/* 123 */       bufferedReader = new BufferedReader(new FileReader(new File(paramString1)));
/*     */
/* 125 */       if (!paramString2.equals("PR") && !paramString2.equals("ROC") && !paramString2.equals("pr") && !paramString2.equals("roc"))
/*     */       {
/* 127 */         throw new NoSuchElementException();
/*     */       }
/*     */
/* 130 */       while (bufferedReader.ready()) {
/*     */
/* 132 */         String str = bufferedReader.readLine();
/* 133 */         if (DEBUG) {
/* 134 */           System.out.println(str);
/*     */         }
/*     */
/*     */
/* 138 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "\t ,");
/*     */
/*     */
/*     */         try {
/* 142 */           double d1 = Double.parseDouble(stringTokenizer.nextToken());
/* 143 */           double d2 = Double.parseDouble(stringTokenizer.nextToken());
/* 144 */           if (DEBUG) {
/* 145 */             System.out.println(d1 + "\t" + d2);
/*     */           }
/*     */
/* 148 */           if (paramString2.equals("PR")) {
/* 149 */             confusion.addPRPoint(d1, d2);
/*     */           } else {
/* 151 */             confusion.addROCPoint(d1, d2);
/*     */           }
/* 153 */           if (DEBUG) {
/* 154 */             System.out.println("End of Line");
/*     */           }
/*     */         }
/* 157 */         catch (NumberFormatException numberFormatException) {
/*     */
/* 159 */           System.err.println("...skipping bad input line (bad numbers)");
/* 160 */         } catch (NoSuchElementException noSuchElementException) {
/*     */
/* 162 */           System.err.println("...skipping bad input line (missing data)");
/*     */         }
/*     */       }
/* 165 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */
/* 167 */       System.err.println("ERROR: File " + paramString1 + " not found - exiting...");
/* 168 */       System.exit(-1);
/* 169 */     } catch (NoSuchElementException noSuchElementException) {
/*     */
/* 171 */       System.err.println("...incorrect fileType argument, either PR or ROC - exiting");
/* 172 */       System.exit(-1);
/* 173 */     } catch (IOException iOException) {
/*     */
/* 175 */       System.err.println("ERROR: IO Exception in file " + paramString1 + " - exiting...");
/* 176 */       System.exit(-1);
/*     */     }
/*     */
/* 179 */     confusion.sort();
/*     */
/* 181 */     confusion.interpolate();
/*     */
/* 183 */     return confusion;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Confusion readArrays(int[] paramArrayOfint, double[] paramArrayOfdouble) {
/* 193 */     if (paramArrayOfint.length != paramArrayOfdouble.length || paramArrayOfint.length == 0) {
/* 194 */       System.err.println(paramArrayOfint.length + " " + paramArrayOfdouble.length);
/* 195 */       System.err.println("ERROR: incorrect array lengths - exiting");
/* 196 */       System.exit(-1);
/*     */     }
/* 198 */     double d1 = 0.0D;
/* 199 */     double d2 = 0.0D;
/* 200 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/* 201 */       if (paramArrayOfint[b1] == 0) {
/* 202 */         d2++;
/* 203 */       } else if (paramArrayOfint[b1] == 1) {
/* 204 */         d1++;
/*     */       } else {
/* 206 */         System.err.println("ERROR: example not 0 or 1 - exiting");
/* 207 */         System.exit(-1);
/*     */       }
/*     */     }
/*     */
/* 211 */     Confusion confusion = new Confusion(d1, d2);
/*     */
/* 213 */     double d3 = 0.0D;
/* 214 */     double d4 = 0.0D;
/* 215 */     if (paramArrayOfint[0] == 0) {
/* 216 */       d4++;
/* 217 */     } else if (paramArrayOfint[0] == 1) {
/* 218 */       d3++;
/*     */     } else {
/* 220 */       System.err.println("ERROR: example not 0 or 1 - exiting");
/* 221 */       System.exit(-1);
/*     */     }
/*     */
/* 224 */     for (byte b2 = 1; b2 < paramArrayOfdouble.length; b2++) {
/* 225 */       if (paramArrayOfdouble[b2] != paramArrayOfdouble[b2 - 1]) {
/*     */         try {
/* 227 */           confusion.addPoint(d3, d4);
/* 228 */         } catch (NumberFormatException numberFormatException) {
/*     */
/* 230 */           System.err.println("...skipping bad input line (bad numbers)");
/*     */         }
/*     */       }
/* 233 */       if (paramArrayOfint[b2] == 0) {
/* 234 */         d4++;
/* 235 */       } else if (paramArrayOfint[b2] == 1) {
/* 236 */         d3++;
/*     */       } else {
/* 238 */         System.err.println("ERROR: example not 0 or 1 - exiting");
/* 239 */         System.exit(-1);
/*     */       }
/*     */     }
/* 242 */     confusion.addPoint(d3, d4);
/* 243 */     confusion.sort();
/* 244 */     confusion.interpolate();
/* 245 */     return confusion;
/*     */   }
/*     */ }
