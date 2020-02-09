/*    */ package com.avianca.esb.transversal.mail.processors;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.Processor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestValidatorProcessor
/*    */   implements Processor
/*    */ {
/*    */   private static final String TO = "to";
/*    */   private static final String FROM = "from";
/*    */   private static final String SUBJECT = "subject";
/*    */   private static final String TEMPLATE_NAME = "template";
/*    */   
/*    */   public void process(Exchange exchange)
/*    */     throws Exception
/*    */   {
/* 23 */     if (!(exchange.getIn().getBody() instanceof Map)) {
/* 24 */       throw new Exception("Invalid message type: The message must be of type Map");
/*    */     }
/* 26 */     Map<String, Object> map = (Map)exchange.getIn().getBody();
/*    */     
/* 28 */     if ((isEmpty(map.get("to"))) || 
/* 29 */       (isEmpty(map.get("from"))) || 
/* 30 */       (isEmpty(map.get("subject")))) {
/* 31 */       throw new Exception("The key-value pairs (to, from, subject), are required");
/*    */     }
/*    */   }
/*    */   
/*    */   private static boolean isEmpty(Object str)
/*    */   {
/* 37 */     return (str == null) || ("".equals(str));
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\processors\RequestValidatorProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */