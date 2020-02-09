/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.Processor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreateMessageFromWebService
/*    */   implements Processor
/*    */ {
/*    */   public void process(Exchange exchange)
/*    */     throws Exception
/*    */   {
/* 19 */     MailMessage mailMessage = (MailMessage)exchange.getIn().getHeader("hdrOriginalMail", MailMessage.class);
/* 20 */     Map attachments = (Map)exchange.getIn().getBody(Map.class);
/*    */     
/* 22 */     Map<String, Object> map = new HashMap();
/*    */     
/* 24 */     map.put("from", exchange.getIn().getHeader("from", String.class));
/* 25 */     map.put("to", mailMessage.getDestinatario());
/* 26 */     map.put("template", exchange.getIn().getHeader("idTemp", String.class));
/*    */     
/* 28 */     if (attachments != null) {
/* 29 */       map.putAll(attachments);
/*    */     }
/*    */     
/*    */ 
/* 33 */     for (ArrayList<String> parametros : mailMessage.getParametros()) {
/* 34 */       map.put(parametros.get(0), parametros.get(1));
/*    */     }
/*    */     
/* 37 */     map.put("subject", map.get("asunto"));
/* 38 */     exchange.getIn().setBody(map, Map.class);
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\CreateMessageFromWebService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */