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

              // TODO(hayesall): My unsafe refactor introduced nulls
              fileName = paramArrayOfString[0];
              fileType = paramArrayOfString[1];
              minRecall = Double.parseDouble(paramArrayOfString[2]);

              assert minRecall >= 0.0D;
              assert minRecall <= 1.0D;

              Confusion confusion = ReadList.readFile(fileName, fileType);
/*  40 */     // confusion.writeROCFile(fileName + ".roc");
/*  41 */     confusion.calculateAUCPR(minRecall);
/*  42 */     confusion.calculateAUCROC();
/*     */   }
/*     */ }
