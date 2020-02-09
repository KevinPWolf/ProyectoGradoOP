/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import org.apache.camel.BeanInject;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.processor.aggregate.AggregationStrategy;
/*    */ 
/*    */ public class CreateResponse implements AggregationStrategy
/*    */ {
/*    */   @BeanInject("sf.mail.props")
/*    */   private Properties properties;
/*    */   
/*    */   public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
/*    */   {
/* 16 */     String successMessage = this.properties.getProperty("transversal.mail.midmail.successMessage");
/* 17 */     String failedMessage = this.properties.getProperty("transversal.mail.midmail.failedMessage");
/* 18 */     if (oldExchange == null) {
/* 19 */       ResponseSentMessage response = new ResponseSentMessage();
/*    */       
/* 21 */       String flagDonwloadFTP = (String)newExchange.getIn().getHeader("flagDonwloadFTP", String.class);
/* 22 */       String msgResult = (String)newExchange.getIn().getHeader("msgResult", String.class);
/*    */       
/* 24 */       if ((flagDonwloadFTP != null) && (flagDonwloadFTP.equals("exito"))) {
/* 25 */         response.setTransactionResult(1);
/* 26 */         response.setMessageResult(successMessage);
/*    */       }
/*    */       
/* 29 */       if ((flagDonwloadFTP != null) && (flagDonwloadFTP.equals("fallido"))) {
/* 30 */         response.setTransactionResult(0);
/* 31 */         response.setMessageResult(failedMessage + ": " + msgResult);
/*    */       }
/*    */       
/* 34 */       if (flagDonwloadFTP == null) {
/* 35 */         response.setTransactionResult(1);
/* 36 */         response.setMessageResult(successMessage);
/*    */       }
/*    */       
/* 39 */       newExchange.getIn().setBody(response);
/*    */     }
/*    */     else {
/* 42 */       ResponseSentMessage response = (ResponseSentMessage)oldExchange.getIn().getBody(ResponseSentMessage.class);
/* 43 */       if (response.getTransactionResult() == 1)
/*    */       {
/* 45 */         String flagDonwloadFTP = (String)newExchange.getIn().getHeader("flagDonwloadFTP", String.class);
/* 46 */         String msgResult = (String)newExchange.getIn().getHeader("msgResult", String.class);
/*    */         
/* 48 */         if ((flagDonwloadFTP != null) && (flagDonwloadFTP.equals("exito"))) {
/* 49 */           response.setTransactionResult(1);
/* 50 */           response.setMessageResult(successMessage);
/*    */         }
/*    */         
/* 53 */         if ((flagDonwloadFTP != null) && (flagDonwloadFTP.equals("fallido"))) {
/* 54 */           response.setTransactionResult(0);
/* 55 */           response.setMessageResult(msgResult);
/*    */         }
/*    */         
/*    */ 
/* 59 */         if (flagDonwloadFTP == null) {
/* 60 */           response.setTransactionResult(1);
/* 61 */           response.setMessageResult(successMessage);
/*    */         }
/*    */       }
/*    */       
/* 65 */       newExchange = oldExchange;
/*    */     }
/* 67 */     return newExchange;
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\CreateResponse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */