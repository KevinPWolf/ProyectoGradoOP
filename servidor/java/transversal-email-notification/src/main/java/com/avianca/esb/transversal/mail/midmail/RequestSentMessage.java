/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.util.ArrayList;
/*    */ import javax.validation.constraints.NotNull;
/*    */ 
/*    */ @javax.xml.bind.annotation.XmlRootElement
/*    */ public class RequestSentMessage implements java.io.Serializable
/*    */ {
/*    */   @NotNull
/*    */   private int idAplication;
/*    */   @NotNull
/*    */   private String messageTemplate;
/*    */   @javax.validation.Valid
/* 16 */   private ArrayList<MailMessage> mail = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getIdAplication()
/*    */   {
/* 23 */     return this.idAplication;
/*    */   }
/*    */   
/*    */   public void setIdAplication(int idAplication) {
/* 27 */     this.idAplication = idAplication;
/*    */   }
/*    */   
/*    */   public String getMessageTemplate() {
/* 31 */     return this.messageTemplate;
/*    */   }
/*    */   
/*    */   public void setMessageTemplate(String messageTemplate) {
/* 35 */     this.messageTemplate = messageTemplate;
/*    */   }
/*    */   
/*    */   public ArrayList<MailMessage> getMail() {
/* 39 */     return this.mail;
/*    */   }
/*    */   
/*    */   public void setMail(ArrayList<MailMessage> mail) {
/* 43 */     this.mail = mail;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/*    */     try
/*    */     {
/* 50 */       return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
/*    */     } catch (JsonProcessingException e) {
/* 52 */       e.printStackTrace();
/*    */     }
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\RequestSentMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */