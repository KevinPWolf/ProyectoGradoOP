/*    */ package com.avianca.esb.transversal.mail.midmail;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ import org.apache.camel.BeanInject;
/*    */ import org.apache.camel.CamelContext;
/*    */ import org.apache.camel.ConsumerTemplate;
/*    */ import org.apache.camel.Exchange;
/*    */ import org.apache.camel.Message;
/*    */ import org.apache.camel.Processor;
/*    */ import org.apache.camel.component.file.remote.RemoteFile;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DownloadFromFtpProcess
/*    */   implements Processor
/*    */ {
/* 18 */   static final Logger logger = Logger.getLogger("MailNotification");
/*    */   
/*    */ 
/*    */   @BeanInject("sf.mail.props")
/*    */   private Properties properties;
/*    */   
/*    */ 
/*    */   public void process(Exchange exchange)
/*    */     throws Exception
/*    */   {
/* 28 */     String flagBorrarFile = (String)exchange.getIn().getHeader("deleteMaster", String.class);
/* 29 */     String fileName = (String)exchange.getIn().getHeader("fileName", String.class);
/* 30 */     String path = (String)exchange.getIn().getHeader("path", String.class);
/* 31 */     String deleteFlag = "false";
/* 32 */     if ("S".equals(flagBorrarFile)) {
/* 33 */       deleteFlag = "true";
/*    */     }
/*    */     
/* 36 */     String host = this.properties.getProperty("transversal.mail.midmail.sftp.host");
/* 37 */     String username = this.properties.getProperty("transversal.mail.midmail.sftp.username");
/* 38 */     String password = this.properties.getProperty("transversal.mail.midmail.sftp.password");
/* 39 */     int timeout = Integer.parseInt(this.properties.getProperty("transversal.mail.midmail.sftp.timeout"));
/* 40 */     String populatedURI = "sftp:" + host + "/" + path + "?streamDownload=false&stepwise=false&password=" + password + "&username=" + username + "&fastExistsCheck=true&delete=" + deleteFlag + "&fileName=" + fileName;
/*    */     
/*    */ 
/* 43 */     logger.info("::: FTP URI : " + populatedURI);
/* 44 */     ConsumerTemplate consumerTemplate = exchange.getContext().createConsumerTemplate();
/*    */     try
/*    */     {
/* 47 */       RemoteFile ftpFile = (RemoteFile)consumerTemplate.receiveBody(populatedURI, timeout);
/* 48 */       if (ftpFile == null) {
/* 49 */         throw new TimeoutException("Timeout limit exceeded: " + timeout + ". File Not Found or Can't connect to FTP");
/*    */       }
/*    */       
/* 52 */       ftpFile.bindToExchange(exchange);
/* 53 */       consumerTemplate.stop();
/* 54 */       exchange.getIn().setHeader("flagDonwloadFTP", "exito");
/* 55 */       exchange.getIn().setHeader("msgResult", fileName + " El archivo se ha descargado exitosamente");
/*    */     } catch (TimeoutException var20) {
/* 57 */       exchange.getIn().setHeader("flagDonwloadFTP", "fallido");
/* 58 */       exchange.getIn().setHeader("msgResult", fileName + " Timeout excedido hacia el FTP. Detalles: " + var20.getMessage());
/* 59 */       exchange.getIn().setHeader("CamelFileLength", Integer.valueOf(0));
/*    */     } catch (Exception var21) {
/* 61 */       exchange.getIn().setHeader("flagDonwloadFTP", "fallido");
/* 62 */       exchange.getIn().setHeader("msgResult", fileName + " Error al descargar el archivo del ftp. Detalles: " + var21.getMessage());
/* 63 */       exchange.getIn().setHeader("CamelFileLength", Integer.valueOf(0));
/*    */     } finally {
/* 65 */       consumerTemplate.stop();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\DownloadFromFtpProcess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */