/*     */ package com.goadrich.mark.auc;
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
/*     */
/*     */ public class PNPoint
/*     */   implements Comparable
/*     */ {
/*     */   private double pos;
/*     */   private double neg;
/*     */
/*     */   public PNPoint(double paramDouble1, double paramDouble2) {
/*  24 */     if (paramDouble1 < 0.0D || paramDouble2 < 0.0D) {
/*  25 */       this.pos = 0.0D;
/*  26 */       this.neg = 0.0D;
/*  27 */       System.err.println("ERROR: " + paramDouble1 + "," + paramDouble2 + " - Defaulting " + "PNPoint to 0,0");
/*     */     } else {
/*     */
/*  30 */       this.pos = paramDouble1;
/*  31 */       this.neg = paramDouble2;
/*     */     }
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public double getPos() {
/*  41 */     return this.pos;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public double getNeg() {
/*  50 */     return this.neg;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public int compareTo(Object paramObject) {
/*  59 */     if (paramObject instanceof PNPoint) {
/*  60 */       PNPoint pNPoint = (PNPoint)paramObject;
/*  61 */       if (this.pos - pNPoint.pos > 0.0D)
/*  62 */         return 1;
/*  63 */       if (this.pos - pNPoint.pos < 0.0D) {
/*  64 */         return -1;
/*     */       }
/*  66 */       if (this.neg - pNPoint.neg > 0.0D)
/*  67 */         return 1;
/*  68 */       if (this.neg - pNPoint.neg < 0.0D) {
/*  69 */         return -1;
/*     */       }
/*  71 */       return 0;
/*     */     }
/*     */
/*     */
/*  75 */     return -1;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public boolean equals(Object paramObject) {
/*  85 */     if (paramObject instanceof PNPoint) {
/*  86 */       PNPoint pNPoint = (PNPoint)paramObject;
/*  87 */       if (Math.abs(this.pos - pNPoint.pos) > 0.001D)
/*  88 */         return false;
/*  89 */       if (Math.abs(this.neg - pNPoint.neg) > 0.001D) {
/*  90 */         return false;
/*     */       }
/*  92 */       return true;
/*     */     }
/*  94 */     return false;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public String toString() {
/* 103 */     String str = "";
/* 104 */     str = str + "(" + this.pos + "," + this.neg + ")";
/* 105 */     return str;
/*     */   }
/*     */ }
