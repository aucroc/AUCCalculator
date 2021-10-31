/*     */ package com.goadrich.mark.auc;
/*     */
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class Confusion
/*     */   extends Vector<PNPoint>
/*     */ {
/*     */   private double totPos;
/*     */   private double totNeg;
/*     */
/*     */   public Confusion(double paramDouble1, double paramDouble2) {
/*  23 */     if (paramDouble1 < 1.0D || paramDouble2 < 1.0D) {
/*  24 */       this.totPos = 1.0D;
/*  25 */       this.totNeg = 1.0D;
/*  26 */       System.err.println("ERROR: " + paramDouble1 + "," + paramDouble2 + " - " + "Defaulting Confusion to 1,1");
/*     */     } else {
/*     */
/*  29 */       this.totPos = paramDouble1;
/*  30 */       this.totNeg = paramDouble2;
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
/*     */   public void addPoint(double paramDouble1, double paramDouble2) throws NumberFormatException {
/*  89 */     if (paramDouble1 < 0.0D || paramDouble1 > this.totPos || paramDouble2 < 0.0D || paramDouble2 > this.totNeg) {
/*  90 */       throw new NumberFormatException();
/*     */     }
/*     */
/*  93 */     PNPoint pNPoint = new PNPoint(paramDouble1, paramDouble2);
/*  94 */     if (!contains(pNPoint)) {
/*  95 */       add(pNPoint);
/*     */     }
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public void sort() {
/*     */
/*     */
/* 109 */     if (size() == 0) {
/* 110 */       System.err.println("ERROR: No data to sort....");
/*     */
/*     */       return;
/*     */     }
/*     */
/* 115 */     PNPoint[] arrayOfPNPoint = new PNPoint[size()];
/* 116 */     byte b1 = 0;
/* 117 */     while (size() > 0) {
/* 118 */       arrayOfPNPoint[b1++] = elementAt(0);
/* 119 */       removeElementAt(0);
/*     */     }
/* 121 */     Arrays.sort((Object[])arrayOfPNPoint);
/* 122 */     for (byte b2 = 0; b2 < arrayOfPNPoint.length; b2++) {
/* 123 */       add(arrayOfPNPoint[b2]);
/*     */     }
/*     */
/*     */
/* 127 */     PNPoint pNPoint1 = elementAt(0);
/* 128 */     while (pNPoint1.getPos() < 0.001D && pNPoint1.getPos() > -0.001D) {
/* 129 */       removeElementAt(0);
/* 130 */       pNPoint1 = elementAt(0);
/*     */     }
/*     */
/*     */
/* 134 */     double d = pNPoint1.getNeg() / pNPoint1.getPos();
/*     */
/* 136 */     PNPoint pNPoint2 = new PNPoint(1.0D, d);
/* 137 */     if (!contains(pNPoint2) && pNPoint1.getPos() > 1.0D) {
/* 138 */       insertElementAt(pNPoint2, 0);
/*     */     }
/*     */
/*     */
/* 142 */     pNPoint2 = new PNPoint(this.totPos, this.totNeg);
/* 143 */     if (!contains(pNPoint2)) {
/* 144 */       add(pNPoint2);
/*     */     }
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public void interpolate() {
/*     */
/*     */
/* 159 */     if (size() == 0) {
/* 160 */       System.err.println("ERROR: No data to interpolate....");
/*     */
/*     */       return;
/*     */     }
/*     */
/* 165 */     for (byte b = 0; b < size() - 1; b++) {
/*     */
/*     */
/* 168 */       PNPoint pNPoint1 = elementAt(b);
/* 169 */       PNPoint pNPoint2 = elementAt(b + 1);
/*     */
/*     */
/* 172 */       double d1 = pNPoint2.getPos() - pNPoint1.getPos();
/* 173 */       double d2 = pNPoint2.getNeg() - pNPoint1.getNeg();
/* 174 */       double d3 = d2 / d1;
/* 175 */       double d4 = pNPoint1.getPos();
/* 176 */       double d5 = pNPoint1.getNeg();
/*     */
/*     */
/* 179 */       while (Math.abs(pNPoint1.getPos() - pNPoint2.getPos()) > 1.001D) {
/*     */
/* 181 */         double d = d5 + (pNPoint1.getPos() - d4 + 1.0D) * d3;
/*     */
/* 183 */         PNPoint pNPoint = new PNPoint(pNPoint1.getPos() + 1.0D, d);
/*     */
/*     */
/* 186 */         insertElementAt(pNPoint, ++b);
/* 187 */         pNPoint1 = pNPoint;
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public double calculateAUCPR(double paramDouble) {
/*     */
/*     */
/* 204 */     if (paramDouble < 0.0D || paramDouble > 1.0D) {
/* 205 */       System.err.println("ERROR: invalid minRecall, must be between 0 and 1 - returning 0");
/*     */
/* 207 */       return 0.0D;
/*     */     }
/* 209 */     if (size() == 0) {
/* 210 */       System.err.println("ERROR: No data to calculate....");
/* 211 */       return 0.0D;
/*     */     }
/*     */
/*     */
/* 215 */     double d1 = paramDouble * this.totPos;
/* 216 */     byte b = 0;
/* 217 */     PNPoint pNPoint1 = elementAt(b);
/* 218 */     PNPoint pNPoint2 = null;
/*     */     try {
/* 220 */       while (pNPoint1.getPos() < d1) {
/* 221 */         pNPoint2 = pNPoint1;
/* 222 */         pNPoint1 = elementAt(++b);
/*     */       }
/* 224 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 225 */       System.out.println("ERROR: minRecall out of bounds - exiting...");
/* 226 */       System.exit(-1);
/*     */     }
/*     */
/*     */
/* 230 */     double d2 = (pNPoint1.getPos() - d1) / this.totPos;
/* 231 */     double d3 = pNPoint1.getPos() / (pNPoint1.getPos() + pNPoint1.getNeg());
/* 232 */     double d4 = d2 * d3;
/*     */
/* 234 */     if (pNPoint2 != null) {
/* 235 */       double d5 = pNPoint1.getPos() / this.totPos - pNPoint2.getPos() / this.totPos;
/*     */
/* 237 */       double d6 = pNPoint1.getPos() / (pNPoint1.getPos() + pNPoint1.getNeg()) - pNPoint2.getPos() / (pNPoint2.getPos() + pNPoint2.getNeg());
/*     */
/* 239 */       double d7 = d6 / d5;
/*     */
/* 241 */       double d8 = pNPoint2.getPos() / (pNPoint2.getPos() + pNPoint2.getNeg()) + d7 * (d1 - pNPoint2.getPos()) / this.totPos;
/*     */
/*     */
/* 244 */       double d9 = 0.5D * d2 * (d8 - d3);
/*     */
/* 246 */       d4 += d9;
/*     */     }
/* 248 */     d2 = pNPoint1.getPos() / this.totPos;
/*     */
/*     */
/* 251 */     for (int i = b + 1; i < size(); i++) {
/* 252 */       PNPoint pNPoint = elementAt(i);
/*     */
/* 254 */       double d5 = pNPoint.getPos() / this.totPos;
/* 255 */       double d6 = pNPoint.getPos() / (pNPoint.getPos() + pNPoint.getNeg());
/* 256 */       double d7 = (d5 - d2) * d6;
/* 257 */       double d8 = 0.5D * (d5 - d2) * (d3 - d6);
/*     */
/*     */
/* 260 */       d4 += d7 + d8;
/* 261 */       pNPoint1 = pNPoint;
/* 262 */       d2 = d5;
/* 263 */       d3 = d6;
/*     */     }
/*     */
/* 267 */     return d4;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public double calculateAUCROC() {
/*     */
/*     */
/* 281 */     if (size() == 0) {
/* 282 */       System.err.println("ERROR: No data to calculate....");
/* 283 */       return 0.0D;
/*     */     }
/*     */
/* 286 */     PNPoint pNPoint = elementAt(0);
/* 287 */     double d1 = pNPoint.getPos() / this.totPos;
/* 288 */     double d2 = pNPoint.getNeg() / this.totNeg;
/* 289 */     double d3 = 0.5D * d1 * d2;
/*     */
/*     */
/*     */
/* 293 */     for (byte b = 1; b < size(); b++) {
/* 294 */       PNPoint pNPoint1 = elementAt(b);
/* 295 */       double d4 = pNPoint1.getPos() / this.totPos;
/* 296 */       double d5 = pNPoint1.getNeg() / this.totNeg;
/* 297 */       double d6 = (d4 - d1) * d5;
/* 298 */       double d7 = 0.5D * (d4 - d1) * (d5 - d2);
/* 299 */       d3 += d6 - d7;
/*     */
/* 301 */       pNPoint = pNPoint1;
/* 302 */       d1 = d4;
/* 303 */       d2 = d5;
/*     */     }
/* 305 */     d3 = 1.0D - d3;
/* 307 */     return d3;
/*     */   }
/*     */
/*     */
/*     */   public String toString() {
/* 444 */     String str = "";
/* 445 */     str = str + "TotPos: " + this.totPos + ", TotNeg: " + this.totNeg + "\n";
/* 446 */     for (byte b = 0; b < size(); b++) {
/* 447 */       str = str + elementAt(b) + "\n";
/*     */     }
/* 449 */     return str;
/*     */   }
/*     */ }
