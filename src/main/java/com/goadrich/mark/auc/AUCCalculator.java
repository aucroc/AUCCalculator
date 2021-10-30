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
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     Confusion confusion;
/*  30 */     readArgs(paramArrayOfString);
/*     */
/*  32 */

              confusion = ReadList.readFile(fileName, fileType);
/*  38 */     confusion.writePRFile(fileName + ".pr");
/*  39 */     confusion.writeStandardPRFile(fileName + ".spr");
/*  40 */     confusion.writeROCFile(fileName + ".roc");
/*  41 */     confusion.calculateAUCPR(minRecall);
/*  42 */     confusion.calculateAUCROC();
/*     */   }
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
/*     */ }
