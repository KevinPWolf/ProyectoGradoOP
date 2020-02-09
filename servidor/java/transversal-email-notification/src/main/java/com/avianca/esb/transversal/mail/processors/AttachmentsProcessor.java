/*    */ package com.avianca.esb.transversal.mail.processors;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.mail.util.ByteArrayDataSource;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.Processor;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentsProcessor
/*    */   implements Processor
/*    */ {
/* 28 */   private static final Logger logger = LoggerFactory.getLogger("MailNotification");
/*    */   
/*    */   public void process(Exchange exchange) throws Exception
/*    */   {
/* 32 */     Map params = (Map)exchange.getIn().getBody(Map.class);
/* 33 */     String patternString = "(attachment\\.)(\\d)(\\.name)";
/* 34 */     Pattern pattern = Pattern.compile(patternString);
/* 35 */     Set set = params.keySet();
/* 36 */     for (Object key : set) {
/* 37 */       String s = String.valueOf(key);
/* 38 */       Matcher matcher = pattern.matcher(s);
/* 39 */       if (matcher.find()) {
/* 40 */         String attachment = matcher.group(2);
/* 41 */         byte[] content = Base64.decodeBase64((byte[])params.get("attachment." + attachment + ".content"));
/* 42 */         String name = (String)params.get("attachment." + attachment + ".name");
/* 43 */         String mimeType = (String)params.get("attachment." + attachment + ".mimeType");
/* 44 */         DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(content, mimeType));
/* 45 */         exchange.getIn().addAttachment(name, dataHandler);
/*    */       }
/*    */     }
/* 48 */     logger.info("Attachaments Found: " + exchange.getIn().getAttachmentNames());
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\processors\AttachmentsProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */