/*    */ package com.avianca.esb.transversal.mail.processors;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.Map;
/*    */ import org.apache.camel.Exchange;
/*    */ 
/*    */ public class LoaderTemplatesProcessor
/*    */ {
/*    */   private Map<String, URL> cacheMap;
/*    */   
/*    */   public LoaderTemplatesProcessor(String strTemplates) throws Exception
/*    */   {
/* 13 */     String[] arrTemplates = strTemplates.split(",");
/* 14 */     this.cacheMap = new java.util.HashMap();
/* 15 */     for (String templateName : arrTemplates) {
/* 16 */       this.cacheMap.put(templateName, new URL("profile:" + templateName + ".vm"));
/*    */     }
/*    */   }
/*    */   
/*    */   public void loadTemplate(Exchange ex) {
/* 21 */     String nameTemplate = (String)ex.getIn().getHeader("template", String.class);
/* 22 */     URL urlTemplate = (URL)this.cacheMap.get(nameTemplate);
/* 23 */     ex.getIn().setHeader("CamelVelocityTemplate", urlTemplate);
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\processors\LoaderTemplatesProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */