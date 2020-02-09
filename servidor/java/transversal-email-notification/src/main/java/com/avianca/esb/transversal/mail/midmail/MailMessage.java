/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.validation.constraints.NotNull;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import org.hibernate.validator.constraints.NotEmpty;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class MailMessage
/*    */ {
/*    */   private String idMessage;
/*    */   private List<ArrayList<String>> parametros;
/*    */   @NotNull
/*    */   @NotEmpty
/*    */   private String destinatario;
/*    */   private String nombre;
/*    */   private String apellido;
/*    */   private List<String> adjuntos;
/*    */   private String flagAdjuntos;
/*    */   
/*    */   public String getIdMessage()
/*    */   {
/* 30 */     return this.idMessage;
/*    */   }
/*    */   
/*    */   public void setIdMessage(String idMessage) {
/* 34 */     this.idMessage = idMessage;
/*    */   }
/*    */   
/*    */   public List<ArrayList<String>> getParametros() {
/* 38 */     return this.parametros;
/*    */   }
/*    */   
/*    */   public void setParametros(List<ArrayList<String>> parametros) {
/* 42 */     this.parametros = parametros;
/*    */   }
/*    */   
/*    */   public String getDestinatario() {
/* 46 */     return this.destinatario;
/*    */   }
/*    */   
/*    */   public void setDestinatario(String destinatario) {
/* 50 */     this.destinatario = destinatario;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 54 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 58 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public String getApellido() {
/* 62 */     return this.apellido;
/*    */   }
/*    */   
/*    */   public void setApellido(String apellido) {
/* 66 */     this.apellido = apellido;
/*    */   }
/*    */   
/*    */   public List<String> getAdjuntos() {
/* 70 */     return this.adjuntos;
/*    */   }
/*    */   
/*    */   public void setAdjuntos(List<String> adjuntos) {
/* 74 */     this.adjuntos = adjuntos;
/*    */   }
/*    */   
/*    */   public String getFlagAdjuntos() {
/* 78 */     return this.flagAdjuntos;
/*    */   }
/*    */   
/*    */   public void setFlagAdjuntos(String flagAdjuntos) {
/* 82 */     this.flagAdjuntos = flagAdjuntos;
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\MailMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */