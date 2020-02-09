/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import com.fasterxml.jackson.annotation.JsonAutoDetect;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ @XmlRootElement
/*    */ @JsonAutoDetect
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class ResponseSentMessage
/*    */ {
/*    */   @XmlElement
/*    */   private int idTransaction;
/*    */   private int transactionResult;
/* 17 */   private String messageResult = "";
/*    */   
/*    */ 
/*    */ 
/*    */   public int idTransaction()
/*    */   {
/* 23 */     return this.idTransaction;
/*    */   }
/*    */   
/*    */   public void setIdTransaction(int idTransaction) {
/* 27 */     this.idTransaction = idTransaction;
/*    */   }
/*    */   
/*    */   public int getTransactionResult() {
/* 31 */     return this.transactionResult;
/*    */   }
/*    */   
/*    */   public void setTransactionResult(int transactionResult) {
/* 35 */     this.transactionResult = transactionResult;
/*    */   }
/*    */   
/*    */   public String getMessageResult() {
/* 39 */     return this.messageResult;
/*    */   }
/*    */   
/*    */   public void setMessageResult(String messageResult) {
/* 43 */     this.messageResult = messageResult;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 49 */     return "ResponseSentMessage{idTransaction=" + this.idTransaction + ", transactionResult=" + this.transactionResult + ", messageResult='" + this.messageResult + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\ResponseSentMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */