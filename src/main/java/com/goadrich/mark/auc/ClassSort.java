/*    */ package com.goadrich.mark.auc;
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ClassSort
/*    */   implements Comparable
/*    */ {
/*    */   private double val;
/*    */   private int classification;
/*    */
/*    */   public ClassSort(double paramDouble, int paramInt) {
/* 16 */     this.val = paramDouble;
/* 17 */     this.classification = paramInt;
/*    */   }
/*    */
/*    */   public int getClassification() {
/* 21 */     return this.classification;
/*    */   }
/*    */
/*    */   public double getProb() {
/* 25 */     return this.val;
/*    */   }
/*    */
/*    */   public int compareTo(Object paramObject) {
/* 29 */     double d = ((ClassSort)paramObject).getProb();
/* 30 */     if (this.val < d) {
/* 31 */       return -1;
/*    */     }
/* 33 */     if (this.val > d) {
/* 34 */       return 1;
/*    */     }
/*    */
/* 37 */     int i = ((ClassSort)paramObject).getClassification();
/* 38 */     if (i == this.classification) {
/* 39 */       return 0;
/*    */     }
/* 41 */     if (this.classification > i) {
/* 42 */       return -1;
/*    */     }
/*    */
/* 45 */     return 1;
/*    */   }
/*    */ }
