/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.activation.MimetypesFileTypeMap;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.processor.aggregate.AggregationStrategy;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JoinAttachments
/*    */   implements AggregationStrategy
/*    */ {
/* 19 */   private static final Logger logger = LoggerFactory.getLogger("MailNotification");
/*    */   
/* 21 */   private MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
/*    */   
/*    */ 
/*    */ 
/*    */   public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
/*    */   {
/* 27 */     if (oldExchange == null) {
/* 28 */       Map<String, Object> body = new HashMap();
/* 29 */       int index = 1;
/* 30 */       if (((String)newExchange.getIn().getHeader("flagDonwloadFTP", String.class)).equals("exito")) {
/* 31 */         String filename = (String)newExchange.getIn().getHeader("CamelFileNameOnly", String.class);
/* 32 */         body.put("attachment." + index + ".name", filename);
/* 33 */         body.put("attachment." + index + ".mimeType", findMimeType(filename));
/* 34 */         body.put("attachment." + index + ".content", newExchange.getIn().getBody(byte[].class));
/*    */       }
/* 36 */       body.put("totalAttachments", Integer.valueOf(index));
/* 37 */       newExchange.getIn().setBody(body);
/*    */     } else {
/* 39 */       if (oldExchange.getIn().getHeader("flagDonwloadFTP").equals("exito")) {
/* 40 */         Map<String, Object> body = (Map)oldExchange.getIn().getBody(Map.class);
/* 41 */         if (((String)newExchange.getIn().getHeader("flagDonwloadFTP", String.class)).equals("exito")) {
/* 42 */           int index = ((Integer)body.get("totalAttachments")).intValue();
/* 43 */           index++;
/* 44 */           String filename = (String)newExchange.getIn().getHeader("CamelFileNameOnly", String.class);
/* 45 */           body.put("attachment." + index + ".name", filename);
/* 46 */           body.put("attachment." + index + ".mimeType", findMimeType(filename));
/* 47 */           body.put("attachment." + index + ".content", newExchange.getIn().getBody(byte[].class));
/* 48 */           body.put("totalAttachments", Integer.valueOf(index));
/*    */         }
/* 50 */         newExchange.getIn().setBody(body);
/*    */       }
/*    */       
/* 53 */       String oldMsgResult = (String)oldExchange.getIn().getHeader("msgResult", String.class);
/* 54 */       String newMsgResult = (String)newExchange.getIn().getHeader("msgResult", String.class);
/*    */       
/* 56 */       String oldFlagDonwloadFTP = (String)oldExchange.getIn().getHeader("flagDonwloadFTP", String.class);
/* 57 */       String newFlagDonwloadFTP = (String)newExchange.getIn().getHeader("flagDonwloadFTP", String.class);
/* 58 */       if ((oldFlagDonwloadFTP.equals("fallido")) || (newFlagDonwloadFTP.equals("fallido"))) {
/* 59 */         newExchange.getIn().setHeader("flagDonwloadFTP", "fallido");
/*    */       }
/* 61 */       newExchange.getIn().setHeader("msgResult", oldMsgResult + "." + newMsgResult);
/*    */       
/* 63 */       long oldFileSize = ((Long)oldExchange.getIn().getHeader("CamelFileLength", Long.TYPE)).longValue();
/* 64 */       long newFileSize = ((Long)newExchange.getIn().getHeader("CamelFileLength", Long.TYPE)).longValue();
/* 65 */       newExchange.getIn().setHeader("CamelFileLength", Long.valueOf(oldFileSize + newFileSize));
/*    */     }
/* 67 */     return newExchange;
/*    */   }
/*    */   
/*    */ 
/*    */   private String findMimeType(String filename)
/*    */   {
/* 73 */     if ((filename.contains("xls")) || (filename.contains("xlsx"))) {
/* 74 */       return "application/vnd.ms-excel";
/*    */     }
/*    */     
/* 77 */     if (filename.contains("zip")) {
/* 78 */       return "application/zip";
/*    */     }
/*    */     
/* 81 */     if (filename.contains("pdf")) {
/* 82 */       return "application/pdf";
/*    */     }
/*    */     
/* 85 */     return this.mimetypesFileTypeMap.getContentType(filename);
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\JoinAttachments.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */